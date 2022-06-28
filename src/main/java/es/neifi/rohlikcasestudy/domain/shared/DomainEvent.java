package es.neifi.rohlikcasestudy.domain.shared;

import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public abstract class DomainEvent<T extends DomainEvent<?>> extends ApplicationEvent {

    private final ID aggregateId;
    private final EventId eventId;
    private final String occurredOn;

    public DomainEvent(ID aggregateId) {
        super(aggregateId);
        this.aggregateId = aggregateId;
        this.eventId     = new EventId(UUID.randomUUID().toString());
        this.occurredOn  = LocalDateTime.now().toString();
    }

    public DomainEvent(ID aggregateId, EventId eventId, String occurredOn) {
        super(aggregateId);
        this.aggregateId = aggregateId;
        this.eventId = eventId;
        this.occurredOn = occurredOn;
    }

    protected abstract String eventType();

    protected abstract Map<String, String> toPrimitives();

    protected abstract T fromPrimitives(
            String aggregateId,
            HashMap<String, Serializable> body,
            String eventId,
            String occurredOn
    );

    public ID aggregateId() {
        return aggregateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainEvent)) return false;
        DomainEvent<?> that = (DomainEvent<?>) o;
        return Objects.equals(aggregateId, that.aggregateId) && Objects.equals(eventId, that.eventId) && Objects.equals(occurredOn, that.occurredOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, eventId, occurredOn);
    }
}
