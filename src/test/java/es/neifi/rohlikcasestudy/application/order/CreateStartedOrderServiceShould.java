package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.StartedOrder;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCreated;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.order.OrderStatus;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateStartedOrderServiceShould {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRegistryRepository orderRegistryRepository;
    @Mock
    private EventBus eventBus = Mockito.mock(EventBus.class);

    @InjectMocks
    private CreateOrderService createOrderService;

    @Captor
    private ArgumentCaptor<DomainEvent<?>> orderCreatedArgumentCaptor;

    @Captor
    private ArgumentCaptor<PendingPaymentOrder> pendingOrderArgumentCaptor;

    @Captor
    private ArgumentCaptor<OrderRegistry> orderRegistryArg;

    private final int ORDER_AUTO_CANCELLATION_TIME = 30;

    @Test
    void createOrderWhenStockIsAvailableAndSaveToOrderRegistry() {
        String orderCreatedEventType = "pub.seller.order.created";
        ProductId orangeProductId = new ProductId("96c0c380-3ab7-48da-b814-b96802dfc03a");
        Quantity quantity = new Quantity(1);

        Product orange = new Product(
                orangeProductId,
                new ProductName("Orange"),
                new PricePerUnit(0.99),
                quantity
        );
        ProductId milkProductId = new ProductId("16c0c380-3ab7-48da-b814-b96802dfc03b");
        Product milk = new Product(
                milkProductId,
                new ProductName("Milk"),
                new PricePerUnit(1.99),
                new Quantity(0)
        );

        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        StartedOrder expectedStartedOrder = new StartedOrder(orderId);
        expectedStartedOrder.addProduct(orangeProductId, quantity);


        when(productRepository.findProductById(orangeProductId)).thenReturn(Optional.of(orange));
        when(productRepository.findProductById(milkProductId)).thenReturn(Optional.of(milk));

        createOrderService.execute(new CreateOrderRequest(
                orderId.id().toString(),
                Map.of(orangeProductId.id().toString(),
                        quantity.quantity(), milkProductId.id().toString(), new Quantity(0).quantity())
        ));

        verify(productRepository).findProductById(orangeProductId);
        verify(orderRepository, times(1)).saveOrder(pendingOrderArgumentCaptor.capture());
        verify(orderRegistryRepository, times(1)).save(orderRegistryArg.capture());

        verify(eventBus, times(1)).publish(orderCreatedArgumentCaptor.capture());

        assertEquals(orderId, orderRegistryArg.getValue().orderId());
        assertTrue(orderRegistryArg.getValue().expirationDate()
                .isAfter(pendingOrderArgumentCaptor.getValue().getCreatedAt()));
        assertEquals(pendingOrderArgumentCaptor.getValue().getCreatedAt()
                .plusMinutes(ORDER_AUTO_CANCELLATION_TIME), orderRegistryArg.getValue().expirationDate());

        assertEquals(orderId, pendingOrderArgumentCaptor.getValue().orderId());
        assertEquals(Map.of(orangeProductId, quantity), pendingOrderArgumentCaptor.getValue().orderQuantity());
        assertEquals(OrderStatus.PENDING, pendingOrderArgumentCaptor.getValue().status());

        assertEquals(orderId.id(), orderCreatedArgumentCaptor.getValue().aggregateId().id());
        OrderCreated domainEvent = (OrderCreated) orderCreatedArgumentCaptor.getValue();
        assertEquals(orderCreatedEventType, domainEvent.type());

    }

    @Captor
    private ArgumentCaptor<DomainEvent<?>> orderCancelledArg;

    @Test
    void dontCreateOrderWhenStockIsNotAvailable() {
        String orderCancelledEvent = "pub.seller.order.cancelled";
        ProductId productId = new ProductId("96c0c380-3ab7-48da-b814-b96802dfc03a");
        Quantity quantity = new Quantity(0);
        Product product = new Product(
                productId,
                new ProductName("Orange"),
                new PricePerUnit(0.99),
                quantity
        );

        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        StartedOrder expectedStartedOrder = new StartedOrder(orderId);
        expectedStartedOrder.addProduct(productId, quantity);

        when(productRepository.findProductById(productId)).thenReturn(Optional.of(product));

        createOrderService.execute(new CreateOrderRequest(
                orderId.id().toString(),
                Map.of(productId.id().toString(),
                        quantity.quantity())
        ));

        verify(productRepository).findProductById(productId);
        verify(eventBus, times(1)).publish(orderCancelledArg.capture());
        assertEquals(orderId.id(), orderCancelledArg.getValue().aggregateId().id());
        OrderCancelled domainEvent = (OrderCancelled) orderCancelledArg.getValue();
        assertEquals(orderCancelledEvent, domainEvent.type());

    }

    @Test
    void throwExceptionWhenProductIsNotFound() {
        ProductId productId = new ProductId("96c0c380-3ab7-48da-b814-b96802dfc03a");
        Quantity quantity = new Quantity(4);

        when(productRepository.findProductById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> createOrderService
                .execute(new CreateOrderRequest(
                        UUID.randomUUID().toString(),
                        Map.of(productId.id().toString(),
                                quantity.quantity())
                )));

    }
}