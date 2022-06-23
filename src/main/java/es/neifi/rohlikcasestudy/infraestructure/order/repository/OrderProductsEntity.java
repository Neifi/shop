package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_products")
public class OrderProductsEntity {
    @Column(name = "id")
    @Id
    private String id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "product_id")
    private String productId;

    private int quantity;

    public OrderProductsEntity(String id, String orderId, String productId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderProductsEntity() {
    }

    public String orderId() {
        return orderId;
    }

    public String productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }
}
