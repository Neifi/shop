package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import org.springframework.data.repository.CrudRepository;

public interface JpaOrderProductRepository extends CrudRepository<OrderProductsEntity,String> {
}
