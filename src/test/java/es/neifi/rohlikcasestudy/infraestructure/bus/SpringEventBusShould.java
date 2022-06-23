package es.neifi.rohlikcasestudy.infraestructure.bus;

import es.neifi.rohlikcasestudy.application.order.CancelOrderRequest;
import es.neifi.rohlikcasestudy.application.order.CancelOrderService;
import es.neifi.rohlikcasestudy.application.order.PayOrderRequest;
import es.neifi.rohlikcasestudy.domain.order.CancellationReason;
import es.neifi.rohlikcasestudy.domain.order.CancelledOrder;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.PendingPaymentOrder;
import es.neifi.rohlikcasestudy.domain.order.StartedOrder;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.product.ProductId;
import es.neifi.rohlikcasestudy.domain.product.Quantity;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@RecordApplicationEvents
@Sql({"/data.sql"})
@ActiveProfiles("test")
class SpringEventBusShould {

    @Autowired
    private SpringEventBus springEventBus;

    @Test
    void publish() {
        String id = "82e6e579-2c6d-42ab-9358-a78f934abdfe";
        OrderId orderId = new OrderId(id);
        StartedOrder startedOrder = new StartedOrder(orderId);
        ProductId productId = new ProductId("7637cab3-69a3-440f-a63a-25c5339d800d");
        startedOrder.addProduct(productId, new Quantity(5));
        PendingPaymentOrder pendingPaymentOrder = PendingPaymentOrder.toPending(startedOrder);

        OrderCancelled events = new OrderCancelled(CancelledOrder.toCancelled(pendingPaymentOrder, CancellationReason.PAYMENT_TIMEOUT));
        springEventBus.publish(events);

       // Mockito.verify(cancelOrderService).handle(any());
       // Mockito.verify(cancelOrderService).execute(any());
    }
}