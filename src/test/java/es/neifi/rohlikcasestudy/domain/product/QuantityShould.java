package es.neifi.rohlikcasestudy.domain.product;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityShould {
    @Test
    void throwExceptionWhenQuantityIsNegative() {

        assertThrows(InvalidProductQuantityException.class, () -> new Quantity(-1));
    }

    @Test
    void createQuantityWithValidNumber() {
        assertDoesNotThrow(() -> new Quantity(3));
    }
}