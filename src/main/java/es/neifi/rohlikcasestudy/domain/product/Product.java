package es.neifi.rohlikcasestudy.domain.product;

import java.util.Objects;

public record Product(ProductId productId,
                      ProductName productName,
                      PricePerUnit pricePerUnit, Quantity stock) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productId, product.productId) &&
               Objects.equals(productName, product.productName) &&
               Objects.equals(pricePerUnit, product.pricePerUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, pricePerUnit);
    }

    public boolean hasStock() {
        return stock.quantity() > 0;
    }
}
