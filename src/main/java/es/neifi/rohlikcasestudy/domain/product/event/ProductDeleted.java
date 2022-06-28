package es.neifi.rohlikcasestudy.domain.product.event;

import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProductDeleted extends DomainEvent<ProductDeleted> {

    private final String TYPE = "seller.product.deleted";

    public ProductDeleted(Product product) {
        super(product.productId());
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
    protected ProductDeleted fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }
}
