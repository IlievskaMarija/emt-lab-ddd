package mk.ukim.finki.emt.lab.application.form;

import mk.ukim.finki.emt.lab.domain.financial.Currency;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RentalForm implements Serializable {

    @NotNull
    private Currency currency;
    @Valid
    @NotNull
    private RecipientAddressForm shippingAddress = new RecipientAddressForm();
    @Valid
    @NotEmpty
    private List<RentedItemForm> items = new ArrayList<>();

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public RecipientAddressForm getShippingAddress() {
        return shippingAddress;
    }

    public List<RentedItemForm> getItems() {
        return items;
    }
}
