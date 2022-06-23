package es.neifi.rohlikcasestudy.domain.shared;

import java.util.List;

public interface EventBus {

    void publish(DomainEvent<?> events);
}
