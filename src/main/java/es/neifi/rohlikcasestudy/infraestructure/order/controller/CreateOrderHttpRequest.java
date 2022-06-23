package es.neifi.rohlikcasestudy.infraestructure.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record CreateOrderHttpRequest( @JsonProperty("orderDetails") Map<String,Integer> productQuantity) {
}
