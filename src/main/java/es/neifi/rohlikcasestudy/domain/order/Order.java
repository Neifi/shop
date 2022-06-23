package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.shared.AggregateRoot;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Objects;

public abstract class Order extends AggregateRoot {
    private final OrderId orderId;
    private final OrderStatus status;
    private final OffsetDateTime createdAt;

    protected Order(OrderStatus status, OrderId orderId, OffsetDateTime createdAt) {
        this.status = status;
        this.orderId = orderId;
        this.createdAt = createdAt;
    }

    public OrderStatus status() {
        return status;
    }

    public OrderId orderId() {
        return orderId;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public abstract Map<ProductId, Quantity> orderQuantity();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && status == order.status && Objects.equals(createdAt, order.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, createdAt);
    }
}
