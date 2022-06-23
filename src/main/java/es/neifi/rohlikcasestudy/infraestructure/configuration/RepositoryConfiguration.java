package es.neifi.rohlikcasestudy.infraestructure.configuration;

import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.infraestructure.order.repository.H2OrderRegistryRepository;
import es.neifi.rohlikcasestudy.infraestructure.order.repository.H2OrderRepository;
import es.neifi.rohlikcasestudy.infraestructure.product.repository.H2ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public OrderRepository orderRepository() {
        return new H2OrderRepository();
    }

    @Bean
    public ProductRepository productRepository() {
        return new H2ProductRepository();
    }

    @Bean
    public OrderRegistryRepository orderRegistryRepository(){
        return new H2OrderRegistryRepository();
    }
}
