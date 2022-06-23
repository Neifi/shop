package es.neifi.rohlikcasestudy.domain.order;

public enum OrderStatus {
    STARTED("started"),PENDING("pending"),FINISHED("finished"),CANCELLED("cancelled");
    private final String status;
    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
