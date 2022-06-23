package es.neifi.rohlikcasestudy.application.order;

public interface PaymentGateway {
    void charge(Double amount) throws RuntimeException;
}
