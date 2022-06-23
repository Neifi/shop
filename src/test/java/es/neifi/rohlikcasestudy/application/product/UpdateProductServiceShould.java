package es.neifi.rohlikcasestudy.application.product;

import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
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
class UpdateProductServiceShould {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private EventBus eventBus;

    @InjectMocks
    private UpdateProductService updateProductService;

    @Captor
    private ArgumentCaptor<DomainEvent<?>> productUpdatedArg;

    @Test
    void updateProductWhenProductExists() {

        String productIdStr = "2849bfb4-d726-4457-a14a-39b33037ca3d";
        ProductId productId = new ProductId(productIdStr);
        String productName = "Milk";
        int pricePerUnit = 1;
        int stock = 5;
        GenericProductRequest request = new GenericProductRequest(
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

        updateProductService.execute(request);

        verify(productRepository).update(product);
        verify(eventBus).publish(productUpdatedArg.capture());
        ProductUpdated productUpdated = (ProductUpdated) productUpdatedArg.getValue();
        assertEquals(productId, productUpdated.aggregateId());
    }

    @Test
    void throwExceptionWhenProductDoesNotExist() {

        String productIdStr = "2849bfb4-d726-4457-a14a-39b33037ca3d";
        ProductId productId = new ProductId(productIdStr);
        String productName = "Milk";
        int pricePerUnit = 1;
        int stock = 5;
        GenericProductRequest request = new GenericProductRequest(
                productIdStr,
                productName,
                pricePerUnit,
                stock
        );
        when(productRepository.findProductById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            updateProductService.execute(request);
        });
    }
}