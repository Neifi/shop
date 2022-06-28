package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import es.neifi.rohlikcasestudy.domain.order.Order;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.StartedOrder;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class H2OrderRepositoryShould {

    @Autowired
    private H2OrderRepository orderRepository;

    @Test
    void saveOrder() {
        String orderIdStr = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        OrderId orderId = new OrderId(orderIdStr);
        StartedOrder startedOrder = new StartedOrder(orderId);
        ProductId productId = new ProductId("ec30fc59-0790-4fe8-95e0-c740e24d24f0");
        startedOrder.addProduct(productId, new Quantity(5));
        PendingPaymentOrder pendingPaymentOrder = PendingPaymentOrder.toPending(startedOrder);

        orderRepository.saveOrder(pendingPaymentOrder);
    }

    @Test
    void getExistentOrder() {
        String orderIdStr = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        OrderId orderId = new OrderId(orderIdStr);
        StartedOrder startedOrder = new StartedOrder(orderId);
        ProductId productId = new ProductId("ec30fc59-0790-4fe8-95e0-c740e24d24f0");
        startedOrder.addProduct(productId, new Quantity(5));
        PendingPaymentOrder pendingPaymentOrder = PendingPaymentOrder.toPending(startedOrder);

        orderRepository.saveOrder(pendingPaymentOrder);

        Optional<Order> order = orderRepository.getOrder(orderId);

        assertTrue(order.isPresent());
        assertEquals(pendingPaymentOrder,order.get());
    }

    @Test
    void returnEmptyWhenOrderDoesNotExist() {
        OrderId orderId = new OrderId("c2895fc7-8f3b-4c86-8c36-9ecf83acdd15");

        Optional<Order> order = orderRepository.getOrder(orderId);

        assertTrue(order.isEmpty());
    }

    @Test
    void deleteExistentOrder() {
        String orderIdStr = "55abeedf-bdfc-4bf4-8a7e-ee414f399778";
        OrderId orderId = new OrderId(orderIdStr);
        StartedOrder startedOrder = new StartedOrder(orderId);
        ProductId productId = new ProductId("ec30fc59-0790-4fe8-95e0-c740e24d24f0");
        startedOrder.addProduct(productId, new Quantity(5));
        PendingPaymentOrder pendingPaymentOrder = PendingPaymentOrder.toPending(startedOrder);

        orderRepository.saveOrder(pendingPaymentOrder);
        orderRepository.deleteOrder(orderId);

        Optional<Order> order = orderRepository.getOrder(orderId);

        assertTrue(order.isEmpty());
    }

    @Test
    void throwExceptionWhenOrderToDeleteDoesNotExist() {
        OrderId orderId = new OrderId("f52c0f6e-55ea-4aec-8d06-9fb3a4b0c3d9");


        assertThrows(EmptyResultDataAccessException.class,() -> orderRepository.deleteOrder(orderId));
    }
}