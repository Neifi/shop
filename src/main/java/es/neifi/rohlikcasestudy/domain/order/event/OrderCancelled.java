package es.neifi.rohlikcasestudy.domain.order.event;

import es.neifi.rohlikcasestudy.domain.order.CancelledOrder;
import es.neifi.rohlikcasestudy.domain.order.CancellationReason;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.ID;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderCancelled extends DomainEvent<OrderCancelled> {
    private final String TYPE = "seller.order.cancelled";
    private final CancellationReason reason;
    public OrderCancelled(CancelledOrder cancelledOrder) {
        super(cancelledOrder.orderId());
        this.reason = cancelledOrder.reason();
    }

    public OrderCancelled(ID aggregateId, CancellationReason reason) {
        super(aggregateId);
        this.reason = reason;
    }

    public String type() {
        return TYPE;
    }

    @Override
    protected String eventType() {
        return TYPE;
    }

    @Override
    protected Map<String, String> toPrimitives() {
        return null;
    }

    @Override
    protected OrderCancelled fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }

    public CancellationReason reason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCancelled)) return false;
        if (!super.equals(o)) return false;
        OrderCancelled that = (OrderCancelled) o;
        return Objects.equals(TYPE, that.TYPE) && reason == that.reason;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), TYPE, reason);
    }
}
