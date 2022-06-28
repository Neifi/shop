package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.ID;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OrderRegistryCreated extends DomainEvent<OrderRegistryCreated>{
    private final String TYPE = "seller.order.registry.created";
    private OrderRegistry orderRegistry;

    public OrderRegistryCreated(OrderRegistry orderRegistry) {
        super(orderRegistry.orderId());
        this.orderRegistry = orderRegistry;
    }

    public OrderRegistryCreated(ID aggregateId) {
        super(aggregateId);
    }

    @Override
    protected String eventType() {
        return TYPE;
    }

    public OrderRegistry oderRegistry() {
        return orderRegistry;
    }

    @Override
    public Map<String, String> toPrimitives() {
        return Map.of("orderId", this.orderRegistry.orderId().id().toString(),
                "expirationDate", this.orderRegistry.expirationDate().toString());
    }

    @Override
    public  OrderRegistryCreated fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }

    public OrderRegistry getOrderRegistry() {
        return orderRegistry;
    }
}
