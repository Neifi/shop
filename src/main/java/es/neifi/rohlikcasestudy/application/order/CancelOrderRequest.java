package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.domain.order.CancellationReason;
import es.neifi.rohlikcasestudy.domain.order.OrderId;

public record CancelOrderRequest(String orderId, String cancellationReason){

}
