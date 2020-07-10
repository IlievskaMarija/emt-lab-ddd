package mk.ukim.finki.emt.lab.infra.eventlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.emt.lab.domain.base.DomainEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DomainEventLogService {

    private static final int LOG_SIZE = 20;

    private final StoredDomainEventRepository storedDomainEventRepository;
    private final ObjectMapper objectMapper;

    DomainEventLogService(StoredDomainEventRepository storedDomainEventRepository,
                          ObjectMapper objectMapper) {
        this.storedDomainEventRepository = storedDomainEventRepository;
        this.objectMapper = objectMapper;
    }

    private static long calculateHighFromLow(long low) {
        return low + LOG_SIZE - 1;
    }

    private static boolean isValidId(@NonNull DomainEventLogId id) {
        if (id.high() - id.low() + 1 != LOG_SIZE) {
            return false;
        }
        return (id.low() - 1) % LOG_SIZE == 0;
    }


    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<DomainEventLog> retrieveLog(@NonNull DomainEventLogId logId) {
        Objects.requireNonNull(logId, "logId must not be null");
        if (!isValidId(logId)) {
            return Optional.empty();
        }
        var currentId = currentLogId();
        var events = storedDomainEventRepository.findEventsBetween(logId.low(), logId.high()).collect(Collectors.toList());
        if (events.isEmpty() && !logId.equals(currentId)) {
            return Optional.empty();
        }
        var previousId = previousLogId(logId).orElse(null);
        var nextId = logId.equals(currentId) ? null : nextLogId(logId);
        return Optional.of(new DomainEventLog(logId, previousId, nextId, events));
    }


    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public DomainEventLog currentLog() {
        return retrieveLog(currentLogId()).orElseThrow();
    }

    @NonNull
    private List<StoredDomainEvent> findEvents(@NonNull DomainEventLogId logId) {
        return storedDomainEventRepository.findEventsBetween(logId.low(), logId.high()).collect(Collectors.toList());
    }

    @NonNull
    private DomainEventLogId currentLogId() {
        var max = storedDomainEventRepository.findHighestDomainEventId();
        if (max == null) {
            max = 0L;
        }
        var remainder = max % LOG_SIZE;
        if (remainder == 0 && max == 0) {
            remainder = LOG_SIZE;
        }

        var low = max - remainder + 1;
        if (low < 1) {
            low = 1;
        }
        var high = calculateHighFromLow(low);

        return new DomainEventLogId(low, high);
    }

    @NonNull
    private Optional<DomainEventLogId> previousLogId(@NonNull DomainEventLogId logId) {
        Objects.requireNonNull(logId, "logId must not be null");

        if (logId.isFirst()) {
            return Optional.empty();
        }

        var low = logId.low() - LOG_SIZE;
        if (low < 1) {
            low = 1;
        }
        var high = calculateHighFromLow(low);

        return Optional.of(new DomainEventLogId(low, high));
    }

    @NonNull
    private DomainEventLogId nextLogId(@NonNull DomainEventLogId logId) {
        var low = logId.high() + 1;
        var high = calculateHighFromLow(low);

        return new DomainEventLogId(low, high);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void append(@NonNull DomainEvent domainEvent) {
        var storedEvent = new StoredDomainEvent(domainEvent, objectMapper);
        storedDomainEventRepository.saveAndFlush(storedEvent);
    }
}
