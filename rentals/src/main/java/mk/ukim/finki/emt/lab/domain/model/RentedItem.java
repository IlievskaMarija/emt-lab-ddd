package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.Type;
import mk.ukim.finki.emt.lab.domain.base.AbstractEntity;
import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import mk.ukim.finki.emt.lab.domain.financial.Currency;
import mk.ukim.finki.emt.lab.domain.financial.Fee4K;
import mk.ukim.finki.emt.lab.domain.financial.Money;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_items", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "order_id"}))
public class RentedItem extends AbstractEntity<RentedItemId> {

    @Column(name = "product_id", nullable = false)
    private MovieId movieId;
    @Column(name = "item_description", nullable = false)
    private String itemDescription;
    @Column(name = "item_currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency itemPriceCurrency;
    @Column(name = "item_price", nullable = false)
    private int itemPrice;
    @Column(name = "value_added_tax", nullable = false)
    private Fee4K valueAddedTax;
    @Column(name = "type", nullable = false)
    private Type type;
    @Column(name = "canWatch", nullable = false)
    private Boolean canWatch;

    @SuppressWarnings("unused") // Used by JPA only
    public RentedItem() {
    }

    RentedItem(@NonNull MovieId movieId, @NonNull String itemDescription, @NonNull Money itemPrice,
               @NonNull Fee4K valueAddedTax) {
        super(DomainObjectId.randomId(RentedItemId.class));
        setMovieId(movieId);
        setItemDescription(itemDescription);
        setItemPrice(itemPrice);
        setValueAddedTax(valueAddedTax);
        canWatch = true;
    }

    @NonNull
    @JsonProperty("movieId")
    public MovieId productId() {
        return movieId;
    }

    private void setMovieId(@NonNull MovieId movieId) {
        this.movieId = Objects.requireNonNull(movieId, "movieId must not be null");
    }

    @NonNull
    @JsonProperty("description")
    public String itemDescription() {
        return itemDescription;
    }

    private void setItemDescription(@NonNull String itemDescription) {
        this.itemDescription = Objects.requireNonNull(itemDescription, "itemDescription must not be null");
    }

    @NonNull
    @JsonProperty("price")
    public Money itemPrice() {
        return Money.valueOf(itemPriceCurrency, itemPrice);
    }

    private void setItemPrice(@NonNull Money itemPrice) {
        Objects.requireNonNull(itemPrice, "itemPrice must not be null");
        this.itemPrice = itemPrice.fixedPointAmount();
        this.itemPriceCurrency = itemPrice.currency();
    }

    @NonNull
    @JsonProperty("valueAddedTax")
    public Fee4K valueAddedTax() {
        return valueAddedTax;
    }

    private void setValueAddedTax(@NonNull Fee4K valueAddedTax) {
        this.valueAddedTax = Objects.requireNonNull(valueAddedTax, "valueAddedTax must not be null");
    }

    @NonNull
    @JsonProperty("canWatch")
    public Boolean  canWatch() {
        return canWatch;
    }

    public void setCanWatch(Boolean watch) {
        this.canWatch = watch;
    }



    @NonNull
    @JsonProperty("type")
    public String  type() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.valueOf(type);
    }

}
