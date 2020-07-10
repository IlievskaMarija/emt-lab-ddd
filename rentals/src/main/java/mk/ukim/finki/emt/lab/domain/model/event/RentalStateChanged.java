package mk.ukim.finki.emt.lab.domain.model.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.model.RentalId;
import mk.ukim.finki.emt.lab.domain.model.RentalState;
import mk.ukim.finki.emt.lab.domain.base.DomainEvent;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;

public class RentalStateChanged implements DomainEvent {

    @JsonProperty("rentalId")
    private final RentalId rentalId;
    @JsonProperty("state")
    private final RentalState state;
    @JsonProperty("occurredOn")
    private final Instant occurredOn;

    @JsonCreator
    public RentalStateChanged(@JsonProperty("rentalId") @NonNull RentalId rentalId,
                              @JsonProperty("state") @NonNull RentalState state,
                              @JsonProperty("occurredOn") @NonNull Instant occurredOn) {
        this.rentalId = Objects.requireNonNull(rentalId, "rentalId must not be null");
        this.state = Objects.requireNonNull(state, "state must not be null");
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    @NonNull
    public RentalId orderId() {
        return rentalId;
    }

    @NonNull
    public RentalState state() {
        return state;
    }

    @Override
    @NonNull
    public Instant occurredOn() {
        return occurredOn;
    }
}
