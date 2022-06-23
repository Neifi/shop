package es.neifi.rohlikcasestudy.infraestructure.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayOrderHttpRequest(@JsonProperty("orderId") String orderId) {
}
