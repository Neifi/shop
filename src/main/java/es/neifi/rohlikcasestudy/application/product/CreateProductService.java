package es.neifi.rohlikcasestudy.application.product;

import es.neifi.rohlikcasestudy.application.product.exception.ProductAlreadyExistException;
import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.product.event.ProductCreated;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;

public class CreateProductService {
    private final ProductRepository productRepository;
    private final EventBus eventBus;

    public CreateProductService(ProductRepository productRepository, EventBus eventBus) {
        this.productRepository = productRepository;
        this.eventBus = eventBus;
    }

    public void execute(GenericProductRequest request) {
        ProductId productId = new ProductId(request.productId());
        Product product = new Product(
                productId,
                new ProductName(request.productName()),
                new PricePerUnit(request.pricePerUnit()),
                new Quantity(request.stock())
        );
        productRepository.findProductById(productId).ifPresent((p)-> {
            throw new ProductAlreadyExistException(p.productId().id().toString());
        });

        productRepository.save(product);
        eventBus.publish(new ProductCreated(product));
    }
}
