package es.neifi.rohlikcasestudy.infraestructure.order;

import es.neifi.rohlikcasestudy.application.order.CancelOrderService;
import es.neifi.rohlikcasestudy.application.order.CreateOrderService;
import es.neifi.rohlikcasestudy.application.order.PayOrderService;
import es.neifi.rohlikcasestudy.infraestructure.order.controller.CancelOrderHttpRequest;
import es.neifi.rohlikcasestudy.infraestructure.order.controller.PayOrderHttpRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostOrderControllerShould {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PayOrderService payOrderService;

    @MockBean
    private CancelOrderService cancelOrderService;

    @Test
    void return202statusWhenOrderIsPaid() {
        String orderId = "5b93aebb-dafa-4d04-9428-7ea48d05bd4a";
        PayOrderHttpRequest body =
                new PayOrderHttpRequest(orderId);
        HttpEntity<PayOrderHttpRequest> bodyEntity = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate
                .exchange("/order/" + orderId + "/pay", HttpMethod.POST, bodyEntity, String.class);

        Mockito.doNothing().when(payOrderService).execute(any());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

    }

    @Test
    void return202statusWhenOrderIsCancelled() {
        String orderId = "5b93aebb-dafa-4d04-9428-7ea48d05bd4a";
        CancelOrderHttpRequest body =
                new CancelOrderHttpRequest("USER");
        HttpEntity<CancelOrderHttpRequest> bodyEntity = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate
                .exchange("/order/" + orderId + "/cancel", HttpMethod.POST, bodyEntity, String.class);

        Mockito.doNothing().when(cancelOrderService).execute(any());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}