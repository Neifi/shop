package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.application.order.exception.OrderNotFoundException;
import es.neifi.rohlikcasestudy.application.product.exception.ProductNotFoundException;
import es.neifi.rohlikcasestudy.domain.order.Order;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.OrderStatus;
import es.neifi.rohlikcasestudy.domain.order.FinishedPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.event.PaidOrder;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRepository;
import es.neifi.rohlikcasestudy.domain.product.Product;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.ProductRepository;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;

import java.util.Map;

public class PayOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderRegistryRepository orderRegistryRepository;
    private final EventBus eventBus;
    private final PaymentGateway paymentGateway;

    public PayOrderService(OrderRepository orderRepository,
                           ProductRepository productRepository, OrderRegistryRepository orderRegistryRepository,
                           EventBus eventBus,
                           PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderRegistryRepository = orderRegistryRepository;
        this.eventBus = eventBus;
        this.paymentGateway = paymentGateway;
    }

    public void execute(PayOrderRequest request) {
        OrderId orderId = new OrderId(request.orderId());
        Order order = this.orderRepository.getOrder(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.status().equals(OrderStatus.PENDING)) {
            FinishedPaymentOrder finishedPaymentOrder = FinishedPaymentOrder.toPaidOrder((PendingPaymentOrder) order);
            Map<ProductId, Quantity> orderProducts = finishedPaymentOrder.orderQuantity();
            double totalAmount = getTotalAmount(orderProducts);

            paymentGateway.charge(totalAmount);
            orderRepository.saveOrder(finishedPaymentOrder);
            orderRegistryRepository.deleteRegistry(orderId);

            eventBus.publish(new PaidOrder(order));
        } else {
            throw new UnsupportedOperationException("The order must be in pending payment status");
        }
    }

    private double getTotalAmount(Map<ProductId, Quantity> orderProducts) {
        double totalAmount = 0d;
        for (ProductId p : orderProducts.keySet()) {
            Product product = productRepository.findProductById(p)
                    .orElseThrow(() -> new ProductNotFoundException(p.id().toString()));
            totalAmount += product.pricePerUnit().price() * orderProducts.get(p).quantity();
        }
        return totalAmount;
    }
}
