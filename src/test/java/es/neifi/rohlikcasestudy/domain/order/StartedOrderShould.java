package es.neifi.rohlikcasestudy.domain.order;

import es.neifi.rohlikcasestudy.domain.order.exception.EmptyProductsException;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import org.junit.jupiter.api.Test;

import static es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder.*;
import static org.junit.jupiter.api.Assertions.*;

class StartedOrderShould {

    @Test
    void createValidOrderWithInitialStatusToStarted() {
        StartedOrder startedOrder = new StartedOrder(
                new OrderId("8c002616-29bb-4c3e-b0db-28af9131562c")
        );
        startedOrder.addProduct(
                new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e"),
                new Quantity(1)
        );

        assertTrue(startedOrder.orderQuantity().containsKey(new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e")));
        assertEquals(startedOrder.orderQuantity().get(new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e")).quantity(), 1);
        assertEquals(OrderStatus.STARTED, startedOrder.status());
    }

    @Test
    void notPassToPendingStatusWhenTheProductsOfTheOrderAreEmpty() {
        StartedOrder startedOrder = new StartedOrder(
                new OrderId("8c002616-29bb-4c3e-b0db-28af9131562c")
        );

        assertThrows(EmptyProductsException.class, () -> toPending(startedOrder));
    }

    @Test
    void createValidOrderandPassToPendingStatus() {
        StartedOrder startedOrder = new StartedOrder(
                new OrderId("8c002616-29bb-4c3e-b0db-28af9131562c")
        );
        startedOrder.addProduct(
                new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e"),
                new Quantity(1)
        );

        PendingPaymentOrder pendingPaymentOrder = toPending(startedOrder);

        assertTrue(pendingPaymentOrder.orderQuantity().containsKey(new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e")));
        assertEquals(pendingPaymentOrder.orderQuantity().get(new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e")).quantity(), 1);
        assertEquals(OrderStatus.PENDING, pendingPaymentOrder.status());
    }

    @Test
    void addQuantityToOrderWhenSameProductIdIsGiven() {
        StartedOrder startedOrder = new StartedOrder(
                new OrderId("8c002616-29bb-4c3e-b0db-28af9131562c")
        );
        startedOrder.addProduct(
                new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e"),
                new Quantity(1)
        );

        startedOrder.addProduct(
                new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e"),
                new Quantity(1)
        );

        assertTrue(startedOrder.orderQuantity().containsKey(new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e")));
        assertEquals(startedOrder.orderQuantity()
                .get(new ProductId("7c002515-19cc-4c3e-b0db-18af9131522e"))
                .quantity(),2);
        assertEquals(OrderStatus.STARTED, startedOrder.status());
    }
}