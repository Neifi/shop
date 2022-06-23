package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.application.order.exception.OrderNotFoundException;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.StartedOrder;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CancelOrderServiceShould {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRegistryRepository orderRegistryRepository;
    @Mock
    private EventBus eventBus;

    @InjectMocks
    private CancelOrderService cancelOrderService;

    @Captor
    private ArgumentCaptor<DomainEvent<?>> orderCancelledArg;

    @Test
    void cancelOrderWhenOrderExist() {
        String id = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        CancelOrderRequest request = new CancelOrderRequest(
                id,
                "USER"
        );
        OrderId orderId = new OrderId(id);
        StartedOrder startedOrder = new StartedOrder(orderId);
        startedOrder.addProduct(new ProductId("ec30fc59-0790-4fe8-95e0-c740e24d24f0"), new Quantity(1));
        String orderCancelledType = "pub.seller.order.cancelled";
        Mockito.when(orderRepository.getOrder(orderId))
                .thenReturn(Optional.of(startedOrder));

        cancelOrderService.execute(request);

        Mockito.verify(eventBus).publish(orderCancelledArg.capture());
        OrderCancelled orderCancelled = (OrderCancelled) orderCancelledArg.getValue();
        assertEquals(orderCancelledType, orderCancelled.type());
        assertEquals(orderId, orderCancelled.aggregateId());

    }

    @Test
    void throwExceptionWhenOrderIsNotFound() {
        String id = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        CancelOrderRequest request = new CancelOrderRequest(
                id,
                "USER"
        );
        OrderId orderId = new OrderId(id);

        Mockito.when(orderRepository.getOrder(orderId))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> cancelOrderService.execute(request));

    }

}