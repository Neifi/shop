package es.neifi.rohlikcasestudy.application.product;


import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.event.ProductDeleted;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;

public class DeleteProductService {
    private final ProductRepository productRepository;
    private final EventBus eventBus;

    public DeleteProductService(ProductRepository productRepository, EventBus eventBus) {
        this.productRepository = productRepository;
        this.eventBus = eventBus;
    }

    public void execute(String productId) {
        productRepository.findProductById(new ProductId(productId)).ifPresentOrElse(product -> {
            productRepository.delete(product.productId());
            eventBus.publish(new ProductDeleted(product));
        }, () -> {
            throw new ProductNotFoundException(productId);
        });
    }
}
