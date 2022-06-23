package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "order_registry")
public class OrderRegistryEntity {

    @Id
    @Column(name = "id")
    private String orderId;
    @Column(name = "expiration_date")
    private OffsetDateTime expirationDate;

    public OrderRegistryEntity(String orderId, OffsetDateTime expirationDate) {
        this.orderId = orderId;
        this.expirationDate = expirationDate;
    }

    public OrderRegistryEntity() {
    }

    public String orderId() {
        return this.orderId;
    }

    public OffsetDateTime expirationDate() {
        return this.expirationDate;
    }
}
