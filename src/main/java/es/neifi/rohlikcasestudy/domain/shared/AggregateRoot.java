package es.neifi.rohlikcasestudy.domain.shared;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public void registerDomainEvent(DomainEvent<?> event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> pullDomainEvents() {
        return this.domainEvents;
    }
}
