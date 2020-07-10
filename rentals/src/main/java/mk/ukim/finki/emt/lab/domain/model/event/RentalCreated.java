package mk.ukim.finki.emt.lab.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.model.RentalId;
import mk.ukim.finki.emt.lab.domain.base.DomainEvent;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;

public class RentalCreated implements DomainEvent {

    @JsonProperty("rentalId")
    private final RentalId rentalId;
    @JsonProperty("occurredOn")
    private final Instant occurredOn;

    @JsonCreator
    public RentalCreated(@JsonProperty("rentalId") @NonNull RentalId rentalId,
                         @JsonProperty("occurredOn") @NonNull Instant occurredOn) {
        this.rentalId = Objects.requireNonNull(rentalId, "rentalId must not be null");
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    @NonNull
    public RentalId orderId() {
        return rentalId;
    }

    @Override
    @NonNull
    public Instant occurredOn() {
        return occurredOn;
    }
}
