package mk.ukim.finki.emt.lab.application.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RecipientAddressForm implements Serializable {

    @NotEmpty
    private String name;
    @NotEmpty
    private String addressLine1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

}
