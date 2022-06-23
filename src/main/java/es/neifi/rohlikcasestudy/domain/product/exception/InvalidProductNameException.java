package es.neifi.rohlikcasestudy.domain.product.exception;

public class InvalidProductNameException extends IllegalArgumentException{
    public InvalidProductNameException(String name) {
        super("Invalid name: "+name);
    }
}
