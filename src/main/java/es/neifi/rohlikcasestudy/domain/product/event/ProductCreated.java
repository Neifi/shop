package es.neifi.rohlikcasestudy.domain.product.event;

import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProductCreated extends DomainEvent<ProductCreated> {

    private final String TYPE = "seller.product.created";

    public ProductCreated(Product product) {
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
    protected ProductCreated fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }
}
