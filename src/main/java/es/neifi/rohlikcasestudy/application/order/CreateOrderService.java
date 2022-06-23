package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.order.CancelledOrder;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.StartedOrder;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCreated;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;

import static es.neifi.rohlikcasestudy.domain.order.CancellationReason.NO_STOCK;
import static es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder.toPending;

public class CreateOrderService {

    private final ProductRepository productRepository;
    private OrderRepository orderRepository;
    private OrderRegistryRepository orderRegistryRepository;
    private EventBus eventBus;
    @Value("${order.ORDER_AUTO_CANCELLATION_TIME}")
    private int ORDER_AUTO_CANCELLATION_TIME = 30;

    public CreateOrderService(ProductRepository productRepository,
                              OrderRepository orderRepository,
                              OrderRegistryRepository orderRegistryRepository,
                              EventBus eventBus) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderRegistryRepository = orderRegistryRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateOrderRequest request) {
        StartedOrder startedOrder = new StartedOrder(new OrderId(request.orderId()));

        request.productQuantity().forEach((productId, quantity) -> {
            Product product = productRepository
                    .findProductById(new ProductId(productId))
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            if (product.hasStock() && product.stock().quantity() >= quantity) {
                startedOrder.addProduct(product.productId(), new Quantity(quantity));
            }
        });


        if (startedOrder.orderQuantity().isEmpty()) {
            cancelOrder(startedOrder);
        } else {

            setToPendingPaymentOrder(startedOrder);
        }
    }

    private void setToPendingPaymentOrder(StartedOrder startedOrder) {
        PendingPaymentOrder pendingPaymentOrder = toPending(startedOrder);
        this.orderRepository.saveOrder(pendingPaymentOrder);
        OffsetDateTime expirationDate = pendingPaymentOrder.getCreatedAt()
                .plusMinutes(ORDER_AUTO_CANCELLATION_TIME);
        this.orderRegistryRepository.save(
                new OrderRegistry(pendingPaymentOrder.orderId(),
                        expirationDate)
        );
        this.eventBus.publish(
                new OrderCreated(pendingPaymentOrder)
        );
    }

    private void cancelOrder(StartedOrder startedOrder) {
        CancelledOrder cancelledOrder = CancelledOrder.toCancelled(startedOrder, NO_STOCK);
        this.eventBus.publish(
                new OrderCancelled(cancelledOrder)
        );
    }
}
