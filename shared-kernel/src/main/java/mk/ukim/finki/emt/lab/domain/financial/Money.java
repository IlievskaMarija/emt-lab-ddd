package mk.ukim.finki.emt.lab.domain.financial;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.base.ValueObject;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Money implements ValueObject {

    @JsonProperty("currency")
    private final Currency currency;
    @JsonProperty("amount")
    private final int amount;


    @JsonCreator
    public Money(@NonNull @JsonProperty("currency") Currency currency, @JsonProperty("amount") int amount) {
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
        this.amount = amount;
    }


    public Money(@NonNull Currency currency, double amount) {
        this(currency, (int) (amount * 100));
    }


    public static Money valueOf(Currency currency, Integer value) {
        if (currency == null || value == null) {
            return null;
        } else {
            return new Money(currency, value);
        }
    }

    @NonNull
    public Money add(@NonNull Money augend) {
        Objects.requireNonNull(augend, "augend must not be null");
        if (currency != augend.currency) {
            throw new IllegalArgumentException("Cannot add two Money objects with different currencies");
        }
        return new Money(currency, amount + augend.amount);
    }

    @NonNull
    public Money subtract(@NonNull Money subtrahend) {
        Objects.requireNonNull(subtrahend, "subtrahend must not be null");
        if (currency != subtrahend.currency) {
            throw new IllegalArgumentException("Cannot subtract two Money objects with different currencies");
        }
        return new Money(currency, amount - subtrahend.amount);
    }


    @NonNull
    public Money multiply(Double multiplicand) {
        return new Money(currency, amount * multiplicand);
    }


    @NonNull
    public Currency currency() {
        return currency;
    }


    public int fixedPointAmount() {
        return amount;
    }


    public double doubleAmount() {
        return amount / 100d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        String amountString;
        if (amount == 0) {
            amountString = "000";
        } else {
            amountString = Integer.toString(amount);
        }
        return String.format("%s %s.%s", currency, amountString.substring(0, amountString.length() - 2),
                amountString.substring(amountString.length() - 2));
    }
}
