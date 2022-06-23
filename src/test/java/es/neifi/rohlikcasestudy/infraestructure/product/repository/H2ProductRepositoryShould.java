package es.neifi.rohlikcasestudy.infraestructure.product.repository;

import es.neifi.rohlikcasestudy.domain.product.PricePerUnit;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductName;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Sql({"/data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class H2ProductRepositoryShould {


    @Autowired
    private H2ProductRepository repository;

    @Test
    void returnExistentProductById() {
        String id = "7637cab3-69a3-440f-a63a-25c5339d800d";
        Product expectedProduct = new Product(
                new ProductId(id),
                new ProductName("Capers - Ox Eye Daisy"),
                new PricePerUnit(888.74),
                new Quantity(31)
        );
        Optional<Product> product = repository.findProductById(new ProductId(id));

        assertTrue(product.isPresent());

        assertEquals(expectedProduct, product.get());
    }

    @Test
    void returnEmptyWhenProductDoesNotExist() {
        Optional<Product> product = repository.findProductById(new ProductId("b83b94d2-f800-491f-9d0d-07313f248f24"));
        assertTrue(product.isEmpty());
    }

    @Test
    void saveProduct() {
        String id = "146f939b-a851-4f90-bb29-ab94965df996";
        Product expectedProduct = new Product(
                new ProductId(id),
                new ProductName("Orange"),
                new PricePerUnit(1),
                new Quantity(3)
        );

        repository.save(expectedProduct);
        Optional<Product> product = repository.findProductById(new ProductId(id));

        assertTrue(product.isPresent());
        assertEquals(expectedProduct,product.get());
    }

    @Test
    void deleteProduct() {

        String id = "b127b0cf-accb-4e48-bf33-93976719c5ba";
        Product expectedProduct = new Product(
                new ProductId(id),
                new ProductName("Orange"),
                new PricePerUnit(1),
                new Quantity(3)
        );

        repository.save(expectedProduct);
        repository.delete(expectedProduct.productId());

        Optional<Product> product = repository.findProductById(new ProductId(id));

        assertTrue(product.isEmpty());
    }

    @Test
    void updateProduct() {
        String id = "7dbce126-7572-4890-b48c-fba397c3bd3c";
        Product oldProduct = new Product(
                new ProductId(id),
                new ProductName("Orange"),
                new PricePerUnit(1),
                new Quantity(3)
        );
        Product expectedProduct = new Product(
                new ProductId(id),
                new ProductName("Orange"),
                new PricePerUnit(2),
                new Quantity(30)
        );

        repository.save(oldProduct);
        repository.update(expectedProduct);
        Optional<Product> product = repository.findProductById(new ProductId(id));

        assertTrue(product.isPresent());
        assertEquals(expectedProduct,product.get());
    }
}