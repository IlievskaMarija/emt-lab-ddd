package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.util.Objects;

@Embeddable
public class RentalStateChange implements ValueObject {

    @Column(name = "changed_on", nullable = false)
    private Instant changedOn;
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalState state;

    @SuppressWarnings("unused") // Used by JPA only
    public RentalStateChange() {
    }

    RentalStateChange(@NonNull Instant changedOn, @NonNull RentalState state) {
        this.changedOn = Objects.requireNonNull(changedOn, "changedOn must not be null");
        this.state = Objects.requireNonNull(state, "state must not be null");
    }

    @NonNull
    @JsonProperty("changedOn")
    public Instant changedOn() {
        return changedOn;
    }

    @NonNull
    @JsonProperty("state")
    public RentalState state() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalStateChange that = (RentalStateChange) o;
        return Objects.equals(changedOn, that.changedOn) &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(changedOn, state);
    }
}
