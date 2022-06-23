package es.neifi.rohlikcasestudy.domain.shared;

public interface DomainEventHandler< T extends DomainEvent<T>> {
    void handleEvent(T event);
}
