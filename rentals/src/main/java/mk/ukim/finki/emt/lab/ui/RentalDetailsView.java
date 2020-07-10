package mk.ukim.finki.emt.lab.ui;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import mk.ukim.finki.emt.lab.domain.model.RentalId;
import mk.ukim.finki.emt.lab.application.RentalCatalog;
import mk.ukim.finki.emt.lab.domain.model.Rental;
import mk.ukim.finki.emt.lab.domain.model.RentedItem;
import mk.ukim.finki.emt.lab.domain.EmailAddress;

import java.util.Optional;

@Route("order")
@PageTitle("Show Rental")
public class RentalDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    private final RentalCatalog rentalCatalog;

    public RentalDetailsView(RentalCatalog rentalCatalog) {
        this.rentalCatalog = rentalCatalog;
        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Optional<Rental> order = Optional.ofNullable(parameter).map(RentalId::new).flatMap(rentalCatalog::findById);
        if (order.isPresent()) {
            showOrder(order.get());
        } else {
            showNoSuchOrder();
        }
    }

    private void showOrder(Rental rental) {
        var title = new Html("<h3>Rental Details</h3>");
        add(title);

        var header = new FormLayout();
        header.addFormItem(createReadOnlyTextField(rental.orderedOn().toString()), "Ordered on");
        header.addFormItem(createReadOnlyTextField(rental.validTo().toString()), "Valid to");
        header.addFormItem(createReadOnlyAddressArea(rental.shippingAddress()), "Shipping Address");
        add(header);

        var items = new Grid<RentedItem>();
        items.addColumn(RentedItem::itemDescription).setHeader("Description");
        items.addColumn(RentedItem::type).setHeader("Type");
        var total = items.addColumn(RentedItem::itemPrice).setHeader("Price");

        items.setItems(rental.items());
        var footerRow = items.appendFooterRow();
        footerRow.getCell(total).setText(rental.totalExcludingTax().toString());

        add(items);
    }

    private TextField createReadOnlyTextField(String value) {
        var textField = new TextField();
        textField.setReadOnly(true);
        textField.setValue(value);
        return textField;
    }

    private TextArea createReadOnlyAddressArea(EmailAddress address) {
        var textArea = new TextArea();
        textArea.setHeight("140px");
        textArea.setValue(formatAddress(address));
        textArea.setReadOnly(true);
        return textArea;
    }

    private String formatAddress(EmailAddress address) {
        var sb = new StringBuilder();
        sb.append(address.getEmailAddress()).append("\n");
        return sb.toString();
    }

    private void showNoSuchOrder() {
        add(new Text("The order does not exist."));
    }
}
