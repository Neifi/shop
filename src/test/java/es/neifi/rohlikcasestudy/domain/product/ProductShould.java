package es.neifi.rohlikcasestudy.domain.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductShould {

    @Test
    void create_correctly() {
        ProductId productId = new ProductId("96c0c380-3ab7-48da-b814-b96802dfc03a");
        assertDoesNotThrow(() -> new Product(
                productId,
                new ProductName("Orange"),
                new PricePerUnit(1),
                new Quantity(1)
        ));

        //TODO check domain event
    }
}