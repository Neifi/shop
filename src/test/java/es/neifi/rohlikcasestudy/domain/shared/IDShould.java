package es.neifi.rohlikcasestudy.domain.shared;

import es.neifi.rohlikcasestudy.domain.shared.exception.InvalidIDException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDShould {

    @Test
    void create_with_valid_uuid() {
        String uuid = "96c0c380-3ab7-48da-b814-b96802dfc03a";
        ID id = new ID(uuid);

        Assertions.assertEquals(id , new ID(uuid));
    }

    @Test
    void not_create_with_invalid_uuid() {
        String uuid = "96c0c380-3ab7";

       Assertions.assertThrows(InvalidIDException.class, () -> new ID(uuid));
    }

}