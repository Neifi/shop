package es.neifi.rohlikcasestudy.infraestructure.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelOrderHttpRequest( @JsonProperty("reason") String cancellationReason){

}