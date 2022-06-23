package es.neifi.rohlikcasestudy.infraestructure.product.controller;

public record GenericProductHttpRequest(String productName, double pricePerUnit, int stock) {
}