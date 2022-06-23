package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Map;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "id")
    private String orderId;
    private String status;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;


    public OrderEntity(String orderId, String status, OffsetDateTime createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public OrderEntity() {
    }

    public String orderId() {
        return orderId;
    }

    public String status() {
        return status;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

}
