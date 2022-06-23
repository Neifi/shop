package es.neifi.rohlikcasestudy.infraestructure.payment;

import es.neifi.rohlikcasestudy.application.order.PaymentGateway;

public class FakePaymentGateway implements PaymentGateway {
    @Override
    public void charge(Double amount) throws RuntimeException {

    }
}
