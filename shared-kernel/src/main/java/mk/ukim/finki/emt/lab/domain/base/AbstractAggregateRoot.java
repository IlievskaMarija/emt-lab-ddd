package mk.ukim.finki.emt.lab.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.lang.NonNull;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.*;

@MappedSuperclass
public abstract class AbstractAggregateRoot<ID extends DomainObjectId> extends AbstractEntity<ID> {

    @Transient
    @JsonIgnore
    private List<DomainEvent> domainEvents = new ArrayList<>();

    protected AbstractAggregateRoot() {
    }

    protected AbstractAggregateRoot(@NonNull AbstractAggregateRoot<ID> source) {
        super(source);
    }

    protected AbstractAggregateRoot(ID id) {
        super(id);
    }

    @NonNull
    protected void registerEvent(@NonNull DomainEvent event) {
        Objects.requireNonNull(event, "event must not be null");
        this.domainEvents.add(event);
    }

    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }

    @DomainEvents
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
