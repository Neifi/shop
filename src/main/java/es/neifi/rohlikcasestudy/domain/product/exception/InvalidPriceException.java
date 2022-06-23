package es.neifi.rohlikcasestudy.domain.product.exception;

public class InvalidPriceException extends IllegalArgumentException{
    public InvalidPriceException(double price) {
        super("Invalid price: " + price);
    }
}
