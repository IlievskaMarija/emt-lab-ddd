package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.EmailAddress;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class RecipientAddress extends EmailAddress {

    @Column(name = "recipient_name")
    private String name;

    @SuppressWarnings("unused") // Used by JPA only.
    protected RecipientAddress() {
    }

    public RecipientAddress(@NonNull String name, @NonNull String addressLine1) {
        super(addressLine1);
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @NonNull
    @JsonProperty("name")
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecipientAddress that = (RecipientAddress) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
