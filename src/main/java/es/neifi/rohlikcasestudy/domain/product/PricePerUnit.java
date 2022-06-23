package es.neifi.rohlikcasestudy.domain.product;

import es.neifi.rohlikcasestudy.domain.product.exception.InvalidPriceException;

public record PricePerUnit(double price) {

    public PricePerUnit {
        if (price < 0) {
            throw new InvalidPriceException(price);
        }
    }
}
