package es.neifi.rohlikcasestudy.domain.order.event;

import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.ID;

import java.io.Serializable;
import java.util.HashMap;

public class OrderCreated extends DomainEvent<OrderCreated> {
    private final String TYPE = "pub.seller.order.created";

    private ID aggregateId;

    public OrderCreated(PendingPaymentOrder order) {
        super(order.orderId());
    }

    public ID id(){
        return aggregateId;
    }

    @Override
    protected String eventType() {
        return TYPE;
    }

    @Override
    protected HashMap<String, Serializable> toPrimitives() {
        return null;
    }

    @Override
    protected OrderCreated fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }

    public String type() {
        return TYPE;
    }
}
