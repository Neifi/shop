package es.neifi.rohlikcasestudy.infraestructure.configuration;

import es.neifi.rohlikcasestudy.application.order.CancelOrderService;
import es.neifi.rohlikcasestudy.application.order.CreateOrderService;
import es.neifi.rohlikcasestudy.application.order.PayOrderService;
import es.neifi.rohlikcasestudy.application.order.PaymentGateway;
import es.neifi.rohlikcasestudy.application.product.CreateProductService;
import es.neifi.rohlikcasestudy.application.product.DeleteProductService;
import es.neifi.rohlikcasestudy.application.product.UpdateProductService;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public CreateOrderService createOrderService(
            ProductRepository productRepository,
            OrderRepository orderRepository,
            OrderRegistryRepository orderRegistryRepository,
            EventBus eventBus
    ) {
        return new CreateOrderService(
                productRepository,
                orderRepository,
                orderRegistryRepository,
                eventBus
        );
    }

    @Bean
    public CancelOrderService CancelOrderService(
            OrderRepository orderRepository,
            OrderRegistryRepository orderRegistryRepository,
            EventBus eventBus
    ) {
        return new CancelOrderService(
                orderRepository,
                orderRegistryRepository
        );
    }

    @Bean
    public PayOrderService payOrderService(OrderRepository orderRepository,
                                           ProductRepository productRepository,
                                           OrderRegistryRepository orderRegistryRepository,
                                           EventBus eventBus,
                                           PaymentGateway paymentGateway) {
        return new PayOrderService(
                orderRepository,
                productRepository,
                orderRegistryRepository,
                eventBus,
                paymentGateway
        );
    }

    @Bean
    public CreateProductService createProductService(ProductRepository productRepository, EventBus eventBus) {
        return new CreateProductService(
                productRepository,
                eventBus
        );
    }

    @Bean
    public UpdateProductService updateProductService(ProductRepository productRepository, EventBus eventBus) {
        return new UpdateProductService(
                productRepository,
                eventBus
        );
    }

    @Bean
    public DeleteProductService deleteProductService(ProductRepository productRepository, EventBus eventBus) {
        return new DeleteProductService(
                productRepository,
                eventBus
        );
    }
}
