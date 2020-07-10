package mk.ukim.finki.emt.lab.domain.financial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import mk.ukim.finki.emt.lab.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Fee4K implements ValueObject {

    private final int percentage;


    @JsonCreator
    public Fee4K(int percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Fee4K cannot be negative");
        }
        this.percentage = percentage;
    }

    public static Fee4K valueOf(Integer percentage) {
        return percentage == null ? null : new Fee4K(percentage);
    }


    @JsonValue
    public int toInteger() {
        return percentage;
    }


    public double toDouble() {
        return percentage / 100d;
    }

    @NonNull
    public Money addTax(@NonNull Money amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        return amount.add(calculateTax(amount));
    }

    @NonNull
    public Money subtractTax(@NonNull Money amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        var withoutTax = (amount.fixedPointAmount() * 100) / (percentage + 100);
        return new Money(amount.currency(), withoutTax);
    }


    @NonNull
    public Money calculateTax(@NonNull Money amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        var tax = (amount.fixedPointAmount() * percentage) / 100;
        return new Money(amount.currency(), tax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee4K fee4K = (Fee4K) o;
        return percentage == fee4K.percentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentage);
    }

    @Override
    public String toString() {
        return String.format("%d %%", percentage);
    }
}
