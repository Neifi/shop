package es.neifi.rohlikcasestudy.infraestructure.order.task;

import es.neifi.rohlikcasestudy.domain.order.OrderRegistryCreated;
import es.neifi.rohlikcasestudy.domain.shared.DomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaDomainEventHandler implements DomainEventHandler<OrderRegistryCreated>
{
    @Autowired
    private KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @Override
    @EventListener
    public void handleEvent(OrderRegistryCreated event) {
        kafkaTemplate.send("orderRegistry",event.toPrimitives());
    }

}
