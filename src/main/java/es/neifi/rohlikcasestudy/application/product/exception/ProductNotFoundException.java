package es.neifi.rohlikcasestudy.application.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String id) {
        super("Product not found with given id:"+ id);
    }
}
