package es.neifi.rohlikcasestudy.infraestructure.product.repository;

import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public class H2ProductRepository implements ProductRepository {

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Override
    public Optional<Product> findProductById(ProductId productId) {
        return jpaProductRepository.findById(productId.id().toString()).map(this::toProduct);

    }

    @Override
    public void save(Product product) {
        this.jpaProductRepository.save(toProductEntity(product));
    }

    @Override
    public void delete(ProductId productId) {
        this.jpaProductRepository.deleteById(productId.id().toString());
    }

    @Override
    public void update(Product product) {
        this.jpaProductRepository.save(toProductEntity(product));
    }


    private Product toProduct(ProductEntity productEntity) {
        return new Product(
                new ProductId(productEntity.productId()),
                new ProductName(productEntity.productName()),
                new PricePerUnit(productEntity.pricePerUnit()),
                new Quantity(productEntity.stock()));
    }

    private ProductEntity toProductEntity(Product product) {
        return new ProductEntity(
                product.productId().id().toString(),
                product.productName().name(),
                product.pricePerUnit().price(),
                product.stock().quantity()
        );
    }
}
