package es.neifi.rohlikcasestudy.application.order;

import java.util.Map;

public record CreateOrderRequest(String orderId,Map<String,Integer> productQuantity) {
}
