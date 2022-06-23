package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.order.exception.EmptyProductsException;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;

public class PendingPaymentOrder extends Order {

    private final Map<ProductId, Quantity> orderProducts;

    public PendingPaymentOrder(StartedOrder order) {
        super(OrderStatus.PENDING, order.orderId(), order.createdAt());
        this.orderProducts = order.orderQuantity();
    }

    public PendingPaymentOrder(OrderId orderId, Map<ProductId, Quantity> orderProducts, OffsetDateTime createdAt) {
        super(OrderStatus.PENDING, orderId, createdAt);
        this.orderProducts = orderProducts;
    }

    public static PendingPaymentOrder toPending(StartedOrder startedOrder) {
        if (startedOrder.orderQuantity().isEmpty()) {
            throw new EmptyProductsException();
        }
        return new PendingPaymentOrder(startedOrder);
    }

    @Override
    public Map<ProductId, Quantity> orderQuantity() {
        return Collections.unmodifiableMap(this.orderProducts);
    }

    public OffsetDateTime getCreatedAt() {
        return this.createdAt();
    }
}
