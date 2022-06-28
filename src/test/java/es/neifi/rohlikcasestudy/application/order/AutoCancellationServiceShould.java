package es.neifi.rohlikcasestudy.application.order;

import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistryCreated;
import es.neifi.rohlikcasestudy.domain.order.event.OrderCancelled;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import es.neifi.rohlikcasestudy.infraestructure.order.task.AutoCancellationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoCancellationServiceShould {

    @Mock
    private EventBus eventBus;
    @Mock
    private OrderRegistryRepository orderRegistryRepository;

    @InjectMocks
    private AutoCancellationService autoCancellationService;

    @Captor
    private ArgumentCaptor<DomainEvent<?>> orderCancelledArg;
    @Test
    void publishOrderCancelledDomainEventWhenPaymentTimeoutIsReached() {
        OrderId orderId = new OrderId("f3a43f72-00b9-4ec5-8a21-44ebb4d860a6");
        OrderRegistry orderRegistry = new OrderRegistry(
                orderId,
                OffsetDateTime.now().plusSeconds(1));

        OrderRegistryCreated orderRegistryCreated = new OrderRegistryCreated(orderRegistry);
        autoCancellationService.scheduleFixedRateTaskAsync(orderRegistryCreated.toPrimitives());

        verify(eventBus, times(1)).publish(orderCancelledArg.capture());
        OrderCancelled domainEvent = (OrderCancelled) orderCancelledArg.getValue();
        assertEquals(orderId, domainEvent.aggregateId());
    }

}