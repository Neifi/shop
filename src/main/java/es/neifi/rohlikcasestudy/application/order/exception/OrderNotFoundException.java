package es.neifi.rohlikcasestudy.application.order.exception;

import es.neifi.rohlikcasestudy.domain.order.OrderId;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId orderId) {
        super("The given order does not exist: "+orderId.id());
    }
}
