package mk.ukim.finki.emt.lab.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class EmailAddress implements ValueObject {

    @JsonProperty("email")
    private String email;

    public EmailAddress(String email) {
        this.setEmail(email);
    }

    public EmailAddress(){}

    private void setEmail(@NonNull @JsonProperty("email") String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid e-mail address.");
        }
        this.email = email;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{3}");
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String  getEmailAddress(){
        return email;
    }
}
