package es.neifi.rohlikcasestudy.domain.product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findProductById(ProductId productId);

    void save(Product product);

    void delete(ProductId productId);

    void update(Product product);
}
