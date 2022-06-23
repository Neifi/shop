package es.neifi.rohlikcasestudy.domain.product;

import es.neifi.rohlikcasestudy.domain.product.exception.InvalidProductNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductNameShould {

    @Test
    void beCreatedWithValidValue() {
        assertDoesNotThrow(() -> new ProductName("Orange"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "someBadWord", "so"})
    void notBeCreatedWithInvalidWords(String name) {
        assertThrows(InvalidProductNameException.class, () -> new ProductName(name));
    }
}