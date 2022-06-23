package es.neifi.rohlikcasestudy.infraestructure.order.repository;


import org.springframework.data.repository.PagingAndSortingRepository;


public interface JpaOrderRegistryRepository extends PagingAndSortingRepository<OrderRegistryEntity,String> {

}
