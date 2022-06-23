package es.neifi.rohlikcasestudy.domain.order.repository;

import es.neifi.rohlikcasestudy.domain.order.Order;
import es.neifi.rohlikcasestudy.domain.order.OrderId;

import java.util.Optional;

public interface OrderRepository {
    void saveOrder(Order order);

    void deleteOrder(OrderId orderId);

    Optional<Order> getOrder(OrderId orderId);
}
