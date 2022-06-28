package es.neifi.rohlikcasestudy.infraestructure.order;

import es.neifi.rohlikcasestudy.application.product.CreateProductService;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import es.neifi.rohlikcasestudy.infraestructure.order.controller.CreateOrderHttpRequest;
import org.junit.jupiter.api.Test;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PutProductControllerShould {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CreateProductService createProductService;

    @MockBean
    private EventBus eventBus;

    @Test
    void return201StatusCodeWhenOrderIsCreated() {

        String productId = "0de106dd-f900-4dad-8aad-c85a6f8c47d0";
        CreateOrderHttpRequest request = new CreateOrderHttpRequest(
                Map.of("7a1bb114-3d26-4d8a-a283-4d14bb7191a4", 1));
        HttpEntity<CreateOrderHttpRequest> bodyEntity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange("/product/"+productId, HttpMethod.PUT, bodyEntity, String.class);

        Mockito.doNothing().when(createProductService).execute(any());

        assertEquals( HttpStatus.CREATED,response.getStatusCode());
    }

}