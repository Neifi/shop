package es.neifi.rohlikcasestudy.application.product.exception;

public class ProductAlreadyExistException extends RuntimeException{
    public ProductAlreadyExistException(String id) {
        super("Product with given id already exist: "+id);
    }
}
