package es.neifi.rohlikcasestudy.infraestructure.configuration;

import es.neifi.rohlikcasestudy.application.order.PaymentGateway;
import es.neifi.rohlikcasestudy.infraestructure.payment.FakePaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfiguration {

    @Bean
    public PaymentGateway paymentGateway() {
        return new FakePaymentGateway();
    }
}
