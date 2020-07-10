package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.Quality;
import mk.ukim.finki.emt.lab.domain.Type;
import mk.ukim.finki.emt.lab.domain.base.ValueObject;
import mk.ukim.finki.emt.lab.domain.financial.Fee4K;
import mk.ukim.finki.emt.lab.domain.financial.Money;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class Movie implements ValueObject {

    private final MovieId id;
    private final String name;
    private final Fee4K valueAddedTax;
    private final Money price_typeOne;
    private final Money price_typeTwo;
    private final Double rating;
    private final String  duration;
    private final String description;
    private final Quality quality;

    @JsonCreator
    public Movie(@JsonProperty("id") @NonNull MovieId id,
                 @JsonProperty("name") @NonNull String name,
                 @JsonProperty("valueAddedTax") @NonNull Fee4K valueAddedTax,
                 @JsonProperty("priceType1") @NonNull Money price1,
                 @JsonProperty("priceType2") @NonNull Money price2,
                 @JsonProperty("rating") @NonNull Double rating,
                 @JsonProperty("duration") @NonNull String duration,
                 @JsonProperty("availableQuality") @NonNull Quality quality,
                 @JsonProperty("description") @NonNull String description){
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.valueAddedTax = Objects.requireNonNull(valueAddedTax, "valueAddedTax must not be null");
        this.price_typeOne = Objects.requireNonNull(price1, "price must not be null");
        this.price_typeTwo = Objects.requireNonNull(price2, "price must not be null");
        this.duration = Objects.requireNonNull(duration, "duration must not be null");
        this.rating = Objects.requireNonNull(rating, "rating must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.quality = Objects.requireNonNull(quality, "quality must not be null");}

    @NonNull
    public MovieId id() {
        return id;
    }

    @NonNull
    public String name() {
        return name;
    }

    @NonNull
    public Fee4K valueAddedTax() {
        return valueAddedTax;
    }

    @NonNull
    public Money priceTypeOne() {
        return this.price_typeOne;
    }

    @NonNull
    public Money priceTypeTwo() {
        return this.price_typeTwo;
    }

    @NonNull
    public Money price(Type type) {
        if(Type.valueOf("Regular").equals(type))
        return this.price_typeOne;
        else return this.price_typeTwo;
    }

    @NonNull
    public String duration() {
        return duration;
    }

    @NonNull
    public Double rating() {
        return rating;
    }

    @NonNull
    public Quality quality(){
        return quality;
    }

    @NonNull
    public String description(){
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) &&
                Objects.equals(name, movie.name) &&
                Objects.equals(valueAddedTax, movie.valueAddedTax) &&
                Objects.equals(price_typeOne, movie.price_typeOne) &&
                Objects.equals(price_typeTwo, movie.price_typeTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, valueAddedTax, price_typeOne, price_typeTwo);
    }
}
