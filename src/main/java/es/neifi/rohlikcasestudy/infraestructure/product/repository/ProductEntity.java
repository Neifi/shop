package es.neifi.rohlikcasestudy.infraestructure.product.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(name = "id")
    private String productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "unit_price")
    private double pricePerUnit;
    private int stock;

    public ProductEntity(String productId, String productName, double pricePerUnit, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
        this.stock = stock;
    }

    public ProductEntity() {
    }

    public String productId() {
        return productId;
    }

    public String productName() {
        return productName;
    }

    public double pricePerUnit() {
        return pricePerUnit;
    }

    public int stock() {
        return stock;
    }
}
