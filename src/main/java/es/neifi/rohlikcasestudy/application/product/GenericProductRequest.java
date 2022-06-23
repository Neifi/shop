package es.neifi.rohlikcasestudy.application.product;

public record GenericProductRequest(String productId, String productName, double pricePerUnit, int stock) {
}
