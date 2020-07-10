package mk.ukim.finki.emt.lab.infra.eventlog;

import mk.ukim.finki.emt.lab.domain.base.DomainEvent;
import org.springframework.lang.NonNull;

import java.util.Optional;


public interface RemoteEventTranslator {

    boolean supports(@NonNull StoredDomainEvent remoteEvent);

    @NonNull
    Optional<DomainEvent> translate(@NonNull StoredDomainEvent remoteEvent);
}
