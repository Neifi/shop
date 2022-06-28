package es.neifi.rohlikcasestudy.domain.order.event;

import es.neifi.rohlikcasestudy.domain.order.Order;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PaidOrder extends DomainEvent<PaidOrder> {
    private final String TYPE = "pub.seller.order.paid";

    public PaidOrder(Order order) {
        super(order.orderId());
    }

    @Override
    public String eventType() {
        return TYPE;
    }

    @Override
    protected Map<String, String> toPrimitives() {
        return null;
    }

    @Override
    protected PaidOrder fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }
}
