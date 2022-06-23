package es.neifi.rohlikcasestudy.domain.order.exception;

import java.time.OffsetDateTime;

public class InvalidOrderExpirationDateException extends RuntimeException {
    public InvalidOrderExpirationDateException(OffsetDateTime date) {
        super("The date cannot be in the past: "+date.toString());
    }
}
