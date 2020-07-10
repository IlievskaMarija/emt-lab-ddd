package mk.ukim.finki.emt.lab.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class FullName implements ValueObject {

    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;


    @SuppressWarnings("unused") // Used by JPA only
    public FullName() {
        this.firstName = null;
        this.lastName = null;
    }

    /**
     * Creates a new {@code FullName} object.
     *
     * @param firstName first name.
     * @param lastName  last name.
     */


    @JsonCreator
    public FullName(@NonNull @JsonProperty("firstName") String firstName, @NonNull @JsonProperty("lastName") String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return this.firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(firstName, fullName.firstName) &&
                Objects.equals(lastName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}