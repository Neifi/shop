package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.application.order.exception.OrderNotFoundException;
import es.neifi.rohlikcasestudy.domain.order.CancellationReason;
import es.neifi.rohlikcasestudy.domain.order.CancelledOrder;
import es.neifi.rohlikcasestudy.domain.order.Order;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

public class CancelOrderService {

    private final OrderRepository orderRepository;
    private final OrderRegistryRepository orderRegistryRepository;

    public CancelOrderService(OrderRepository orderRepository,
                              OrderRegistryRepository orderRegistryRepository) {
        this.orderRepository = orderRepository;
        this.orderRegistryRepository = orderRegistryRepository;
    }

    @EventListener
    public void handle(OrderCancelled event) {
        execute(new CancelOrderRequest(event.aggregateId().id().toString(), event.reason().name()));
    }

    public void execute(CancelOrderRequest request) {
        OrderId orderId = new OrderId(request.orderId());
        this.orderRepository.getOrder(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        this.orderRepository.deleteOrder(orderId);
        this.orderRegistryRepository.deleteRegistry(orderId);
    }
}
