package es.neifi.rohlikcasestudy.domain.product;

import es.neifi.rohlikcasestudy.domain.product.exception.InvalidPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricePerUnitShould {
    @Test
    void notBeLessThanZero() {
        
        assertThrows(InvalidPriceException.class, () -> {
            new PricePerUnit(-1);
        });
    }

    @Test
    void createdWithValidValue() {

        assertDoesNotThrow(()-> new PricePerUnit(149.99));

    }
}