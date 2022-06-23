package es.neifi.rohlikcasestudy.infraestructure.order.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.neifi.rohlikcasestudy.domain.order.FinishedPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.Order;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class H2OrderRepository implements OrderRepository {

    @Autowired
    private JpaOrderRepository jpaOrderRepository;
    @Autowired
    private JpaOrderProductRepository jpaOrderProductRepository;

    @Override
    public void saveOrder(Order order) {

        try {
            this.jpaOrderRepository.save(toOrderEntity(order));
            for (ProductId productId : order.orderQuantity().keySet()) {
                this.jpaOrderProductRepository.save(new OrderProductsEntity(
                        UUID.randomUUID().toString(),
                        order.orderId().id().toString(),
                        productId.id().toString(),
                        order.orderQuantity().get(productId).quantity()
                ));
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(OrderId orderId) {
        this.jpaOrderRepository.deleteById(orderId.id().toString());
    }

    @Override
    public Optional<Order> getOrder(OrderId orderId) {
        Optional<OrderEntity> order = this.jpaOrderRepository.findById(orderId.id().toString());
        return order.map(this::toOrder);
    }

    private Order toOrder(OrderEntity orderEntity) {
        Map<ProductId, Quantity> orderProducts = new HashMap<>();
        switch (orderEntity.status()) {
            case "PENDING" -> {
                mapToOrderProduct(orderEntity.orderId(), orderProducts);
                return new PendingPaymentOrder(
                        new OrderId(orderEntity.orderId()),
                        orderProducts,
                        orderEntity.createdAt()
                );
            }
            case "FINISHED" -> {
                mapToOrderProduct(orderEntity.orderId(), orderProducts);
                return new FinishedPaymentOrder(
                        new OrderId(orderEntity.orderId()),
                        orderProducts,
                        orderEntity.createdAt()
                );
            }

            default -> {
                return null;
            }
        }

    }

    private void mapToOrderProduct(String orderId, Map<ProductId, Quantity> orderProducts) {
        jpaOrderProductRepository.findAllById(Collections.singleton(orderId)).forEach(e -> {
            orderProducts.put(new ProductId(e.productId()), new Quantity(e.quantity()));
        });
    }

    private OrderEntity toOrderEntity(Order order) throws JsonProcessingException {

        return new OrderEntity(
                order.orderId().id().toString(),
                order.status().toString().toUpperCase(Locale.ROOT),
                order.createdAt()
        );

    }
}
