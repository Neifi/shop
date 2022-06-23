package es.neifi.rohlikcasestudy.domain.order.exception;

public class EmptyProductsException extends RuntimeException{
    public EmptyProductsException() {
        super("The order must have products");
    }
}
