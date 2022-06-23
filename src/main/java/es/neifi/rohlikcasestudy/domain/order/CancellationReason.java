package es.neifi.rohlikcasestudy.domain.order;

public enum CancellationReason {
    NO_STOCK("noStock"),
    USER("user"),
    PAYMENT_TIMEOUT("paymentTimeout");

    private final String reason;

    CancellationReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return reason;
    }


}
