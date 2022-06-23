package es.neifi.rohlikcasestudy.infraestructure.product.repository;

import org.springframework.data.repository.CrudRepository;

public interface JpaProductRepository extends CrudRepository<ProductEntity, String> {
}
