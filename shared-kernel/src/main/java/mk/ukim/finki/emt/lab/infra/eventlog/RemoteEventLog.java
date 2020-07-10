package mk.ukim.finki.emt.lab.infra.eventlog;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;


public interface RemoteEventLog {

    boolean isCurrent();

    @NonNull
    Optional<RemoteEventLog> previous();


    @NonNull
    Optional<RemoteEventLog> next();


    default boolean containsEvent(@NonNull Long eventId) {
        return events().stream().anyMatch(event -> eventId.equals(event.id()));
    }


    @NonNull
    List<StoredDomainEvent> events();
}
