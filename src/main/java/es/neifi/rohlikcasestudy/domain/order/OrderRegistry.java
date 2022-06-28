package es.neifi.rohlikcasestudy.domain.order;

import java.time.OffsetDateTime;

public record OrderRegistry(OrderId orderId, OffsetDateTime expirationDate){

}
