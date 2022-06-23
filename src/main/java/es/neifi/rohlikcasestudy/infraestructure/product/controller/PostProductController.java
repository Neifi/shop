package es.neifi.rohlikcasestudy.infraestructure.product.controller;

import es.neifi.rohlikcasestudy.application.product.DeleteProductService;
import es.neifi.rohlikcasestudy.application.product.GenericProductRequest;
import es.neifi.rohlikcasestudy.application.product.UpdateProductService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
public class PostProductController {


    private UpdateProductService updateProductService;

    public PostProductController(UpdateProductService updateProductService, DeleteProductService deleteProductService) {
        this.updateProductService = updateProductService;
    }

    @PostMapping("/product/{id}")
    @ResponseStatus(ACCEPTED)
    public void updateProduct(@PathVariable("productId") String productId, @RequestBody GenericProductHttpRequest body) {
        updateProductService.execute(new GenericProductRequest(productId,
                body.productName(),
                body.pricePerUnit(),
                body.stock()));
    }

}
