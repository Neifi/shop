package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.application.order.exception.OrderNotFoundException;
import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.order.FinishedPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.OrderStatus;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.StartedOrder;
import es.neifi.rohlikcasestudy.domain.order.event.PaidOrder;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayOrderServiceShould {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRegistryRepository orderRegistryRepository;
    @Mock
    private PaymentGateway paymentGateway;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private EventBus eventBus;

    @InjectMocks
    private PayOrderService payOrderService;

    @Captor
    private ArgumentCaptor<DomainEvent<?>> orderPaidEventArg;

    @Captor
    private ArgumentCaptor<FinishedPaymentOrder> finishedPaymentOrderArg;

    @Test
    void executePaymentForGivenOrder() {
        String orderPaidType = "pub.seller.order.paid";
        String orderIdStr = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        PayOrderRequest request = new PayOrderRequest(orderIdStr);
        OrderId orderId = new OrderId(orderIdStr);
        StartedOrder startedOrder = new StartedOrder(orderId);
        ProductId productId = new ProductId("ec30fc59-0790-4fe8-95e0-c740e24d24f0");
        startedOrder.addProduct(productId, new Quantity(5));
        PendingPaymentOrder pendingPaymentOrder = PendingPaymentOrder.toPending(startedOrder);
        Product milk = new Product(
                productId,
                new ProductName("Milk"),
                new PricePerUnit(2.0),
                new Quantity(5)//TODO Quantity stock integer, maybe other VO
        );
        Map<ProductId, Quantity> expectedProducts = startedOrder.orderQuantity();

        when(orderRepository.getOrder(orderId))
                .thenReturn(Optional.of(pendingPaymentOrder));
        when(productRepository.findProductById(productId)).thenReturn(Optional.of(milk));

        payOrderService.execute(request);

        verify(paymentGateway, times(1)).charge(10d);
        verify(orderRepository).saveOrder(finishedPaymentOrderArg.capture());
        FinishedPaymentOrder finishedPaymentOrder = finishedPaymentOrderArg.getValue();
        assertEquals(orderId, finishedPaymentOrder.orderId());
        assertEquals(OrderStatus.FINISHED, finishedPaymentOrder.status());
        assertEquals(expectedProducts, finishedPaymentOrder.orderQuantity());
        verify(orderRegistryRepository).deleteRegistry(orderId);
        verify(eventBus).publish(orderPaidEventArg.capture());
        DomainEvent<?> paidEvent = orderPaidEventArg.getValue();
        assertEquals(orderId, paidEvent.aggregateId());
        PaidOrder domainEvent = (PaidOrder) paidEvent;
        assertEquals(orderPaidType, domainEvent.eventType());
    }

    @Test
    void throwExceptionWhenProductIsNotFound() {
        String orderIdStr = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        OrderId orderId = new OrderId(orderIdStr);
        StartedOrder startedOrder = new StartedOrder(orderId);
        ProductId productId = new ProductId("ec30fc59-0790-4fe8-95e0-c740e24d24f0");
        startedOrder.addProduct(productId, new Quantity(5));
        PendingPaymentOrder pendingPaymentOrder = PendingPaymentOrder.toPending(startedOrder);

        when(orderRepository.getOrder(any())).thenReturn(Optional.of(pendingPaymentOrder));
        when(productRepository.findProductById(productId))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            payOrderService.execute(
                    new PayOrderRequest(orderIdStr)
            );
        });
    }

    @Test
    void throwExceptionWhenOrderIsNotFound() {
        String orderIdStr = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        OrderId orderId = new OrderId(orderIdStr);

        when(orderRepository.getOrder(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            payOrderService.execute(new PayOrderRequest(orderIdStr));
        });
    }
}