package mk.ukim.finki.emt.lab.ui;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import mk.ukim.finki.emt.lab.domain.model.Movie;
import mk.ukim.finki.emt.lab.application.RentalCatalog;
import mk.ukim.finki.emt.lab.application.MovieCatalog;
import mk.ukim.finki.emt.lab.application.form.RentalForm;
import mk.ukim.finki.emt.lab.application.form.RentedItemForm;
import mk.ukim.finki.emt.lab.application.form.RecipientAddressForm;
import mk.ukim.finki.emt.lab.domain.Type;
import mk.ukim.finki.emt.lab.domain.financial.Currency;

@Route("create-order")
@PageTitle("Create Rental")
public class CreateRentalView extends VerticalLayout {

    private final MovieCatalog movieCatalog;
    private final RentalCatalog rentalCatalog;
    private final Binder<RentalForm> binder;
    private final Grid<RentedItemForm> itemGrid;

    public CreateRentalView(MovieCatalog movieCatalog, RentalCatalog rentalCatalog) {
        this.movieCatalog = movieCatalog;
        this.rentalCatalog = rentalCatalog;

        setSizeFull();

        binder = new Binder<>();

        var title = new Html("<h3>Create Rental</h3>");
        add(title);

        var tabs = new Tabs();
        tabs.setWidth("630px");
        add(tabs);
        var tabContainer = new TabContainer(tabs);
        tabContainer.setWidth("630px");
        tabContainer.setHeight("100%");
        add(tabContainer);

        var currency = new ComboBox<>("Currency", Currency.values());
        binder.forField(currency)
                .asRequired()
                .bind(RentalForm::getCurrency, RentalForm::setCurrency);
        tabContainer.addTab("Rental Info", currency);

//        var billingAddress = new AddressLayout();
//        billingAddress.bind(binder, RentalForm::getBillingAddress);
//        tabContainer.addTab("Billing Address", billingAddress);

        var shippingAddress = new AddressLayout();
        shippingAddress.bind(binder, RentalForm::getShippingAddress);
        tabContainer.addTab("Shipping Address", shippingAddress);

        itemGrid = new Grid<>();
        itemGrid.addColumn(form -> form.getMovie().name()).setHeader("Product");
        itemGrid.addColumn(RentedItemForm::getType).setHeader("Type");

        var orderItemLayout = new OrderItemLayout();
        tabContainer.addTab("Items", new Div(itemGrid, orderItemLayout));

        var createOrder = new Button("Create Rental", evt -> createOrder());
        createOrder.setEnabled(false);
        createOrder.getElement().getThemeList().set("primary", true);

        add(createOrder);

        binder.setBean(new RentalForm());
        binder.addValueChangeListener(evt -> createOrder.setEnabled(binder.isValid()));
        tabs.setSelectedIndex(0);
    }

    private void addItem(RentedItemForm item) {
        binder.getBean().getItems().add(item);
        itemGrid.setItems(binder.getBean().getItems());
    }

    private void createOrder() {
        try {
            var orderId = rentalCatalog.createOrder(binder.getBean());
            getUI().ifPresent(ui -> ui.navigate(RentalDetailsView.class, orderId.toUUID()));
        } catch (Exception ex) {
            Notification.show(ex.getMessage());
        }
    }

    class AddressLayout extends VerticalLayout {

        private TextField name;
        private TextField addressLine1;


        AddressLayout() {
            setPadding(false);
            setWidth("630px");

            name = createTextField("Name");
            addressLine1 = createTextField("Email address");

            var line1 = new HorizontalLayout(name, addressLine1);

            add(line1);
        }

        private TextField createTextField(String caption) {
            var field = new TextField(caption);
            field.setWidth("100%");
            return field;
        }

        private void bind(Binder<RentalForm> binder, ValueProvider<RentalForm, RecipientAddressForm> parentProvider) {
            binder.forField(name)
                    .asRequired()
                    .bind(getter(parentProvider, RecipientAddressForm::getName), setter(parentProvider, RecipientAddressForm::setName));
            binder.forField(addressLine1)
                    .asRequired()
                    .bind(getter(parentProvider, RecipientAddressForm::getAddressLine1), setter(parentProvider, RecipientAddressForm::setAddressLine1));

        }

        private <V> ValueProvider<RentalForm, V> getter(ValueProvider<RentalForm, RecipientAddressForm> parentProvider, ValueProvider<RecipientAddressForm, V> valueProvider) {
            return rentalForm -> valueProvider.apply(parentProvider.apply(rentalForm));
        }

        private <V> Setter<RentalForm, V> setter(ValueProvider<RentalForm, RecipientAddressForm> parentProvider, Setter<RecipientAddressForm, V> setter) {
            return (rentalForm, value) -> setter.accept(parentProvider.apply(rentalForm), value);
        }
    }

    class OrderItemLayout extends HorizontalLayout {

        private Binder<RentedItemForm> binder;
        private ComboBox<Movie> product;
        private ComboBox<Type> type;
        private TextField itemPrice;
        private TextField tax;
        private TextField duration;
        private TextField rating;
        private TextField description;
        private Button addItem;

        OrderItemLayout() {
            setWidth("630px");

            setAlignItems(Alignment.END);
            type = new ComboBox<>("Type", Type.values());
            type.setWidth("100px");
            add(type);

            setAlignItems(Alignment.END);
            product = new ComboBox<>("Movie", movieCatalog.findAll());
            product.setItemLabelGenerator(Movie::name);
            add(product);

            duration = new TextField("Duration");
            duration.setReadOnly(true);
            duration.setWidth("60px");
            add(duration);

            rating = new TextField("Rating");
            rating.setReadOnly(true);
            rating.setWidth("60px");
            add(rating);

            description = new TextField("Description");
            description.setReadOnly(true);
            description.setWidth("200px");
            add(description);

            itemPrice = new TextField("Price");
            itemPrice.setReadOnly(true);
            itemPrice.setWidth("100%");
            add(itemPrice);

            addItem = new Button("Add", evt -> {
                addItem(binder.getBean());
                binder.setBean(new RentedItemForm());
                addItem.setEnabled(false);
            });
            addItem.setEnabled(false);
            add(addItem);

            product.addValueChangeListener(evt -> {
                var p = evt.getValue();
                if (p == null) {
                    itemPrice.setValue("");
                    duration.setValue("");
                    rating.setValue("");
                    description.setValue("");
                } else {
                    itemPrice.setValue(p.price(type.getValue()).toString());
                    duration.setValue(p.duration().toString());
                    rating.setValue(p.rating().toString());
                    description.setValue(p.description());
                }
            });

            binder = new Binder<>();
            binder.forField(product)
                    .asRequired()
                    .bind(RentedItemForm::getMovie, RentedItemForm::setMovie);
            binder.forField(type)
                    .asRequired()
                    .bind(RentedItemForm::getType, RentedItemForm::setType);
            binder.addValueChangeListener(evt -> addItem.setEnabled(binder.isValid()));
            binder.setBean(new RentedItemForm());
        }
    }
}
