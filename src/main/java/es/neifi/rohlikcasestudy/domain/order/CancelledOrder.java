package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;

import java.util.Map;
import java.util.Objects;

public class CancelledOrder extends Order {
    private CancellationReason reason;
    private Map<ProductId, Quantity> orderQuantity;
    private CancelledOrder(Order order, CancellationReason reason) {
        super(OrderStatus.CANCELLED, order.orderId(), order.createdAt());
        this.reason = reason;
        this.orderQuantity = order.orderQuantity();
    }

    public static CancelledOrder toCancelled(Order order, CancellationReason cancellationReason) {
        return new CancelledOrder(order, cancellationReason);
    }

    @Override
    public Map<ProductId, Quantity> orderQuantity() {
        return this.orderQuantity;
    }

    public CancellationReason reason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CancelledOrder)) return false;
        CancelledOrder that = (CancelledOrder) o;
        return reason == that.reason && Objects.equals(orderQuantity, that.orderQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, orderQuantity);
    }
}
