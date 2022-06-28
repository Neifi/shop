package es.neifi.rohlikcasestudy.application.product;

import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProductUpdated extends DomainEvent<ProductUpdated> {
    private final String TYPE = "seller.product.updated";

    public ProductUpdated(Product product) {
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
    protected ProductUpdated fromPrimitives(String aggregateId, HashMap<String, Serializable> body, String eventId, String occurredOn) {
        return null;
    }
}
