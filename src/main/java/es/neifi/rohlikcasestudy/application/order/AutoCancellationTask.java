package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.domain.order.CancellationReason;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistryCreated;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.OffsetDateTime;

public class AutoCancellationTask  {
    private final EventBus eventBus;

    public AutoCancellationTask(EventBus eventBus) {
        this.eventBus = eventBus;

    }

    public void execute(OrderRegistryCreated orderRegistryCreated){
        OrderRegistry orderRegistry = orderRegistryCreated.oderRegistry();
        OffsetDateTime expirationDate = orderRegistry.expirationDate();
        OffsetDateTime now = OffsetDateTime.now();
        if (now.isEqual(expirationDate) || expirationDate.isAfter(now)) {
            eventBus.publish(new OrderCancelled(orderRegistry.orderId(), CancellationReason.PAYMENT_TIMEOUT));
        }

    }


}
