package es.neifi.rohlikcasestudy.application.product;

import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.product.event.ProductDeleted;
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
class DeleteProductServiceShould {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private EventBus eventBus;
    @InjectMocks
    private DeleteProductService deleteProductService;
    @Captor
    private ArgumentCaptor<DomainEvent<?>> productDeletedArg;

    @Test
    void deleteProductWhenProductExists() {
        ProductId productId = new ProductId("6246e0cc-582e-4390-b870-5f3a990b536d");
        Product product = new Product(
                productId,
                new ProductName("Milk"),
                new PricePerUnit(1),
                new Quantity(3)
        );
        when(productRepository.findProductById(productId)).thenReturn(Optional.of(product));

        deleteProductService.execute("6246e0cc-582e-4390-b870-5f3a990b536d");

        verify(productRepository).delete(productId);
        verify(eventBus).publish(productDeletedArg.capture());
        ProductDeleted productDeleted = (ProductDeleted) productDeletedArg.getValue();
        assertEquals(productId, productDeleted.aggregateId());
    }

    @Test
    void throwExceptionWhenProductDoesNotExist() {
        ProductId productId = new ProductId("6246e0cc-582e-4390-b870-5f3a990b536d");
        when(productRepository.findProductById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> deleteProductService.execute("6246e0cc-582e-4390-b870-5f3a990b536d"));
    }
}