package es.neifi.rohlikcasestudy.infraestructure.order.controller;

import es.neifi.rohlikcasestudy.application.order.CreateOrderRequest;
import es.neifi.rohlikcasestudy.application.order.CreateOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutOrderController {

    private CreateOrderService createOrderService;

    public PutOrderController(CreateOrderService createOrderService) {
        this.createOrderService = createOrderService;
    }

    @PutMapping("/order/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@PathVariable("orderId") String orderId, @RequestBody CreateOrderHttpRequest body) {
        createOrderService.execute(new CreateOrderRequest(orderId, body.productQuantity()));
    }
}
