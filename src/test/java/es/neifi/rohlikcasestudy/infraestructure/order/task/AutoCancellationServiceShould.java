package es.neifi.rohlikcasestudy.infraestructure.order.task;

import es.neifi.rohlikcasestudy.application.order.CancelOrderService;
import es.neifi.rohlikcasestudy.domain.order.OrderId;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistry;
import es.neifi.rohlikcasestudy.domain.order.OrderRegistryCreated;
import es.neifi.rohlikcasestudy.domain.order.repository.OrderRegistryRepository;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
class AutoCancellationServiceShould {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private OrderRegistryRepository orderRegistryRepository;

    @MockBean
    private CancelOrderService cancelOrderService;

    @Autowired
    private AutoCancellationService autoCancellationService;

    @Test
    void cancelOrderAfterPaymentTimeout() throws InterruptedException {
        OrderId orderId = new OrderId("f3a43f72-00b9-4ec5-8a21-44ebb4d860a6");
        OrderRegistry orderRegistry = new OrderRegistry(
                orderId,
                OffsetDateTime.now().plusSeconds(1));

        OrderRegistryCreated orderRegistryCreated = new OrderRegistryCreated(orderRegistry);
        autoCancellationService.scheduleFixedRateTaskAsync(orderRegistryCreated.toPrimitives());
        TimeUnit.SECONDS.sleep(2);

    }
}