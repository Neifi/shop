package es.neifi.rohlikcasestudy.domain.product;

public record Quantity(int quantity){
    public Quantity {
        if(quantity < 0 ){
            throw new InvalidProductQuantityException(quantity);
        }
    }
}
