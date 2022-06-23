package es.neifi.rohlikcasestudy.domain.shared;

import es.neifi.rohlikcasestudy.domain.shared.exception.InvalidIDException;

import java.util.Objects;
import java.util.UUID;

public class ID {
    private UUID id;

    public ID(String id) {
        try {
            this.id = UUID.fromString(id);

        }catch (IllegalArgumentException e){
            throw new InvalidIDException(id);
        }
    }

    public UUID id(){
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ID)) return false;
        ID id1 = (ID) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
