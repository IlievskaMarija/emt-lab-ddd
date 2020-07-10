package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.model.event.RentalStateChanged;
import mk.ukim.finki.emt.lab.domain.EmailAddress;
import mk.ukim.finki.emt.lab.domain.Type;
import mk.ukim.finki.emt.lab.domain.base.AbstractAggregateRoot;
import mk.ukim.finki.emt.lab.domain.base.ConcurrencySafeDomainObject;
import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import mk.ukim.finki.emt.lab.domain.financial.Currency;
import mk.ukim.finki.emt.lab.domain.financial.CurrencyConverter;
import mk.ukim.finki.emt.lab.domain.financial.Money;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "orders")
public class Rental extends AbstractAggregateRoot<RentalId> implements ConcurrencySafeDomainObject {

    @Version
    private Long version;
    @Column(name = "ordered_on", nullable = false)
    private Instant orderedOn;
    @Column(name = "valid_to", nullable = false)
    private Instant validTo;
    @Column(name = "status", nullable = false)
    private Boolean status;
    @Column(name = "order_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Column(name = "order_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalState state;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_state_changes")
    private Set<RentalStateChange> stateChangeHistory;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email", nullable = false)),
    })
    private EmailAddress shippingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Set<RentedItem> items;

    @SuppressWarnings("unused") // Used by JPA only
    public Rental() {
    }

    public Rental(@NonNull Instant orderedOn, @NonNull Currency currency,
                  @NonNull EmailAddress shippingAddress) {
        super(DomainObjectId.randomId(RentalId.class));
        this.stateChangeHistory = new HashSet<>();
        this.items = new HashSet<>();
        setOrderedOn(orderedOn);
        setCurrency(currency);
        setState(RentalState.CREATED, orderedOn);
        setShippingAddress(shippingAddress);
        this.validTo = orderedOn.plus(Duration.ofDays(2));
        this.status = false;
    }

    @NonNull
    @JsonProperty("currency")
    public Currency currency() {
        return currency;
    }

    private void setCurrency(@NonNull Currency currency) {
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
    }

    @NonNull
    @JsonProperty("status")
    public Boolean status() {
        return status;
    }

    private void setStatus(@NonNull Boolean status) {
        this.status = status;
    }

    @NonNull
    @JsonProperty("orderedOn")
    public Instant orderedOn() {
        return orderedOn;
    }

    private void setOrderedOn(@NonNull Instant orderedOn) {
        this.orderedOn = Objects.requireNonNull(orderedOn, "orderedOn must not be null");
    }

    @NonNull
    @JsonProperty("validTo")
    public Instant validTo() {
        return validTo;
    }

    private void setValidTo() {
        this.validTo = orderedOn.plus(Duration.ofHours(48));
    }

    @NonNull
    @JsonProperty("state")
    public RentalState state() {
        return state;
    }

    private void setState(@NonNull RentalState state, @NonNull Clock clock) {
        Objects.requireNonNull(clock, "clock must not be null");
        setState(state, clock.instant());
    }

    private void setState(@NonNull RentalState state, @NonNull Instant changedOn) {
        Objects.requireNonNull(state, "state must not be null");
        Objects.requireNonNull(changedOn, "changedOn must not be null");
        if (stateChangeHistory.stream().anyMatch(stateChange -> stateChange.state().equals(state))) {
            throw new IllegalStateException("Rental has already been in state " + state);
        }
        this.state = state;
        var stateChange = new RentalStateChange(changedOn, state);
        stateChangeHistory.add(stateChange);
        if (stateChangeHistory.size() > 1) { // Don't fire an event for the initial state
            registerEvent(new RentalStateChanged(id(), stateChange.state(), stateChange.changedOn()));
        }
    }

    @NonNull
    @JsonProperty("shippingAddress")
    public EmailAddress shippingAddress() {
        return shippingAddress;
    }

    private void setShippingAddress(@NonNull EmailAddress shippingAddress) {
        this.shippingAddress = Objects.requireNonNull(shippingAddress, "shippingAddress must not be null");
    }


    @NonNull
    public RentedItem addItem(@NonNull Movie movie, @NonNull String type, @NonNull CurrencyConverter currencyConverter) {
        Objects.requireNonNull(movie, "movie must not be null");
        Objects.requireNonNull(currencyConverter, "currencyConverter must not be null");
        Money price = null;
        if(type.equals("Regular"))
            price = movie.priceTypeOne();
        else
            price = movie.priceTypeTwo();
        var item = new RentedItem(movie.id(), movie.name(), currencyConverter.convert(price, currency()),
                movie.valueAddedTax());
        item.setType(type);
        items.add(item);
        return item;
    }

    @NonNull
    @JsonProperty("items")
    public Stream<RentedItem> items() {
        return items.stream();
    }

    @NonNull
    @JsonProperty("stateChangeHistory")
    public Stream<RentalStateChange> stateChangeHistory() {
        return stateChangeHistory.stream();
    }

    @NonNull
    @JsonProperty("totalExcludingVAT")
    public Money totalExcludingTax() {
        return items().map(RentedItem::itemPrice).reduce(new Money(currency, 0), Money::add);
    }

    public void cancel(@NonNull Clock clock) {
        setState(RentalState.DENIED, clock);
    }

    public void startProcessing(@NonNull Clock clock) {
        setState(RentalState.PROCESSING, clock);
    }

    public void finishProcessing(@NonNull Clock clock) {
        setState(RentalState.PROCESSED, clock);
    }

    public void watchRentedItems(@NonNull Clock clock){
        for(RentedItem rentedItem : items){
            if(rentedItem.type().equals(Type.Regular.toString()) && rentedItem.canWatch()){
                rentedItem.setCanWatch(false); // watched
            } else if (rentedItem.type().equals(Type.Premium.toString()) &&
                    Instant.now().isBefore(validTo) && rentedItem.canWatch()){
                rentedItem.setCanWatch(false); // watched
            }
        }
        if(this.items.stream().noneMatch(RentedItem::canWatch))
            setState(RentalState.EXPIRED, clock);


    }

    @Override
    @Nullable
    public Long version() {
        return version;
    }
}
