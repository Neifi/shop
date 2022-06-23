package es.neifi.rohlikcasestudy.domain.product;

import es.neifi.rohlikcasestudy.domain.product.exception.InvalidProductNameException;

public record ProductName(String name) {
    public ProductName {
        if(name.isEmpty() || name.contains("someBadWord") || name.length() < 3){
            throw new InvalidProductNameException(name);
        }
    }
}
