package es.neifi.rohlikcasestudy.infraestructure.bus;

import es.neifi.rohlikcasestudy.domain.shared.DomainEvent;
import es.neifi.rohlikcasestudy.domain.shared.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class SpringEventBus implements EventBus {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent<?> events) {
        applicationEventPublisher.publishEvent(events);
    }
}
