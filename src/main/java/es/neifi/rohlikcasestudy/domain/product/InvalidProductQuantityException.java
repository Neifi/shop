package es.neifi.rohlikcasestudy.domain.product;

public class InvalidProductQuantityException extends IllegalArgumentException {
    public InvalidProductQuantityException(double quantity) {
        super("Invalid stock: "+ quantity);
    }
}
