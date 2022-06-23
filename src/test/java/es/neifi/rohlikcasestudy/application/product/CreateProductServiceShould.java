package es.neifi.rohlikcasestudy.application.product;

import es.neifi.rohlikcasestudy.application.product.exception.ProductAlreadyExistException;
import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.product.event.ProductCreated;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceShould {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EventBus eventBus;

    @InjectMocks
    private CreateProductService createProductService;

    @Captor
    ArgumentCaptor<DomainEvent<?>> productCreatedArg;

    @Test
    void createProductCorrectly() {

        String productIdStr = "2849bfb4-d726-4457-a14a-39b33037ca3d";
        ProductId productId = new ProductId(productIdStr);
        String productName = "Milk";
        int pricePerUnit = 1;
        int stock = 5;
        GenericProductRequest milk = new GenericProductRequest(
                productIdStr,
                productName,
                pricePerUnit,
                stock
        );

        when(productRepository.findProductById(productId)).thenReturn(Optional.empty());

        createProductService.execute(milk);

        verify(productRepository).save(new Product(
                productId,
                new ProductName(productName),
                new PricePerUnit(pricePerUnit),
                new Quantity(stock)
        ));

        verify(eventBus).publish(productCreatedArg.capture());
        ProductCreated event = (ProductCreated) productCreatedArg.getValue();
        assertEquals(productId,event.aggregateId());
    }

    @Test
    void throwExceptionWhenProductAlreadyExist() {
        String productIdStr = "2849bfb4-d726-4457-a14a-39b33037ca3d";
        ProductId productId = new ProductId(productIdStr);
        String productName = "Milk";
        int pricePerUnit = 1;
        int stock = 5;
        GenericProductRequest milk = new GenericProductRequest(
                productIdStr,
                productName,
                pricePerUnit,
                stock
        );

        Product product = new Product(
                productId,
                new ProductName(productName),
                new PricePerUnit(pricePerUnit),
                new Quantity(stock)
        );

        when(productRepository.findProductById(productId)).thenReturn(Optional.of(
                product
        ));

        assertThrows(ProductAlreadyExistException.class, () -> {
            createProductService.execute(milk);
        });
    }
}