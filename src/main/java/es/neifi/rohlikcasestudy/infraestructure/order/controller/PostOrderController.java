package es.neifi.rohlikcasestudy.infraestructure.order.controller;

import es.neifi.rohlikcasestudy.application.order.CancelOrderRequest;
import es.neifi.rohlikcasestudy.application.order.CancelOrderService;
import es.neifi.rohlikcasestudy.application.order.PayOrderRequest;
import es.neifi.rohlikcasestudy.application.order.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostOrderController {

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private CancelOrderService cancelOrderService;

    @PostMapping("/order/{id}/pay")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void payOrder(@PathVariable("id") String orderId) {
        payOrderService.execute(new PayOrderRequest(orderId));
    }

    @PostMapping("/order/{id}/cancel")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelOrder(@PathVariable("id") String orderId, CancelOrderHttpRequest body) {
        cancelOrderService.execute(new CancelOrderRequest(orderId, body.cancellationReason()));
    }
}
