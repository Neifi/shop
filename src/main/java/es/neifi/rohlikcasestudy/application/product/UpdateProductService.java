package es.neifi.rohlikcasestudy.application.product;


import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;

public class UpdateProductService {
    private final ProductRepository productRepository;
    private final EventBus eventBus;

    public UpdateProductService(ProductRepository productRepository, EventBus eventBus) {
        this.productRepository = productRepository;
        this.eventBus = eventBus;
    }

    public void execute(GenericProductRequest request) {
        productRepository.findProductById(new ProductId(request.productId())).ifPresentOrElse(
                product -> {
                    productRepository.update(new Product(
                            new ProductId(request.productId()),
                            new ProductName(request.productName()),
                            new PricePerUnit(request.pricePerUnit()),
                            new Quantity(request.stock())
                    ));

                    eventBus.publish(new ProductUpdated(product));
                },
                () -> {
                    throw new ProductNotFoundException(request.productId());
                }
        );
    }
}
