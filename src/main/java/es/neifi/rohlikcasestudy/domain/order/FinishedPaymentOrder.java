package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;

public class FinishedPaymentOrder extends Order {
    private final Map<ProductId, Quantity> orderProducts;

    public FinishedPaymentOrder(PendingPaymentOrder order) {
        super(OrderStatus.FINISHED, order.orderId(), order.createdAt());
        this.orderProducts = order.orderQuantity();
    }

    public FinishedPaymentOrder(OrderId orderId, Map<ProductId, Quantity> orderProducts, OffsetDateTime createdAt) {
        super(OrderStatus.FINISHED, orderId, createdAt);
        this.orderProducts = orderProducts;
    }

    public static FinishedPaymentOrder toPaidOrder(PendingPaymentOrder order) {
        return new FinishedPaymentOrder(order);
    }

    @Override
    public Map<ProductId, Quantity> orderQuantity() {
        return Collections.unmodifiableMap(this.orderProducts);
    }
}
