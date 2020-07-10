package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.Quality;
import mk.ukim.finki.emt.lab.domain.base.AbstractAggregateRoot;
import mk.ukim.finki.emt.lab.domain.base.ConcurrencySafeDomainObject;
import mk.ukim.finki.emt.lab.domain.base.DeletableDomainObject;
import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import mk.ukim.finki.emt.lab.domain.financial.Currency;
import mk.ukim.finki.emt.lab.domain.financial.Money;
import mk.ukim.finki.emt.lab.domain.financial.Fee4K;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "movies")
public class Movie extends AbstractAggregateRoot<MovieId> implements DeletableDomainObject,
        ConcurrencySafeDomainObject {

    @Version
    private Long version;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "available_quality", nullable = false)
    @Enumerated(EnumType.STRING)
    private Quality availableQuality;

    @Column(name = "price_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency priceCurrency;

    @Column(name = "price_value", nullable = false)
    private Integer priceValue;

    @Column(name = "fee4K", nullable = false)
    private Fee4K fee4K;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "duration", nullable = false)
    private String duration;

    //    @ManyToMany(fetch = FetchType.EAGER)
    @ElementCollection(targetClass=Director.class)
    private List<Director> directors;

    //    @ManyToMany(fetch = FetchType.EAGER)
    @ElementCollection(targetClass=Genre.class)
    private List<Genre> genres;


    @SuppressWarnings("unused") // Used by JPA only
    public Movie() {
    }

    public Movie(@NonNull String name, @NonNull Money price, @NonNull Fee4K fee4K,
                 @NonNull String duration, @NonNull Double rating,
                 @NonNull Quality availableQuality, @NonNull String description,
                 @NonNull List<Genre> genres, @NonNull List<Director> directors) {
        super(DomainObjectId.randomId(MovieId.class));
        setName(name);
        setPrice(price);
        setVAT(fee4K);
        setDescription(description);
        setAvailableQuality(availableQuality);
        setDuration(duration);
        setRating(rating);
        setDirectors(directors);
        setGenres(genres);
    }

    @NonNull
    @JsonProperty("name")
    public String name() {
        return name;
    }

    private void setName(@NonNull String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    private void setAvailableQuality(@NonNull Quality quality){
        this.availableQuality = quality;
    }

    @NonNull
    @JsonProperty("availableQuality")
    public String availableQuality() {
        return this.availableQuality.toString();
    }

    @Nullable
    @JsonProperty("description")
    public String description() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    @JsonProperty("rating")
    public Double rating() {
        return rating;
    }

    public void setRating(@Nullable Double rating) {
        this.rating = rating;
    }

    @Nullable
    @JsonProperty("duration")
    public String duration() {
        return duration;
    }

    public void setDuration(@Nullable String  duration) {
        this.duration = duration;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @NonNull
    @JsonProperty("priceType1")
    public Money priceType1() {
        if(availableQuality.equals(Quality.HD))
            return Money.valueOf(priceCurrency, priceValue);
        return vat().addTax(Money.valueOf(priceCurrency, priceValue));
    }

    @NonNull
    @JsonProperty("priceType2")
    public Money priceType2() {
        if(availableQuality.equals(Quality.HD))
            return Money.valueOf(priceCurrency, priceValue).add(
                    Money.valueOf(priceCurrency, (int) (priceValue*0.25))
            );
        return vat().addTax(Money.valueOf(priceCurrency, priceValue).add(
                Money.valueOf(priceCurrency, (int) (priceValue*0.25))));
    }


    private void setPrice(@NonNull Money price) {
        Objects.requireNonNull(price, "price must not be null");
        priceCurrency = price.currency();
        priceValue = price.fixedPointAmount();
    }

    @NonNull
    @JsonProperty("valueAddedTax")
    public Fee4K vat() {
        return fee4K;
    }

    private void setVAT(@NonNull Fee4K fee4K) {
        this.fee4K = Objects.requireNonNull(fee4K);
    }

    @Override
    @Nullable
    public Long version() {
        return version;
    }

    @Override
    @JsonProperty("isDeleted")
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void delete() {
        this.deleted = true;
    }
}
