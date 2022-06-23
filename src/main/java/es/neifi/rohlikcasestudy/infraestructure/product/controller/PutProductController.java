package es.neifi.rohlikcasestudy.infraestructure.product.controller;

import es.neifi.rohlikcasestudy.application.product.CreateProductService;
import es.neifi.rohlikcasestudy.application.product.GenericProductRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class PutProductController {

    private final CreateProductService createProductService;

    public PutProductController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @PutMapping("/product/{id}")
    @ResponseStatus(CREATED)
    public void updateProduct(@PathVariable("id") String productId, @RequestBody GenericProductHttpRequest body) {
        createProductService.execute(new GenericProductRequest(productId,
                body.productName(),
                body.pricePerUnit(),
                body.stock()));
    }
}
