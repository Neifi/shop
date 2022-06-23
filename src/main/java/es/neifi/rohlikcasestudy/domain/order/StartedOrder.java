package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.order.exception.EmptyProductsException;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StartedOrder extends Order {
    private final Map<ProductId, Quantity> orderProducts = new HashMap<>();

    public StartedOrder(OrderId orderId) {
        super(OrderStatus.STARTED, orderId, OffsetDateTime.now());
    }

    public void addProduct(ProductId productId, Quantity quantity) {
        if (this.orderProducts.containsKey(productId)) {
            int newQuantity = this.orderProducts.get(productId).quantity() +
                                 quantity.quantity();
            this.orderProducts.put(productId, new Quantity(newQuantity));
            return;
        }
        this.orderProducts.put(productId, quantity);
    }

    @Override
    public Map<ProductId, Quantity> orderQuantity() {
        return Collections.unmodifiableMap(this.orderProducts);
    }


}
