package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.ID;

import java.io.Serializable;
import java.util.HashMap;

public class OrderRegistryCreated extends DomainEvent<OrderRegistryCreated> {
    private final String TYPE = "seller.order.registry.created";
    private OrderRegistry orderRegistry;
    public OrderRegistryCreated(OrderRegistry orderRegistry) {
        super(orderRegistry.orderId());
        this.orderRegistry = orderRegistry;
    }

    @Override
    protected String eventType() {
        return TYPE;
    }

    public OrderRegistry oderRegistry() {
        return orderRegistry;
    }

    @Override
    protected HashMap<String, Serializable> toPrimitives() {
        return null;
    }

    @Override
    protected OrderRegistryCreated fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }
}
