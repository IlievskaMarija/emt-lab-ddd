package mk.ukim.finki.emt.lab.ui;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import mk.ukim.finki.emt.lab.application.RentalCatalog;
import mk.ukim.finki.emt.lab.domain.model.Rental;
import mk.ukim.finki.emt.lab.domain.model.RentalId;

@Route("")
@PageTitle("Rental Browser")
public class RentalBrowserView extends VerticalLayout {

    private final RentalCatalog rentalCatalog;
    private final Grid<Rental> ordersGrid;

    public RentalBrowserView(RentalCatalog rentalCatalog) {
        this.rentalCatalog = rentalCatalog;

        setSizeFull();

        var title = new Html("<h3>Rental Browser</h3>");
        add(title);

        ordersGrid = new Grid<>();
        ordersGrid.addColumn(Rental::orderedOn).setHeader("Rented on");
        ordersGrid.addColumn(Rental::validTo).setHeader("Valid to");
        ordersGrid.addColumn(Rental::state).setHeader("State");
        ordersGrid.addColumn(Rental::currency).setHeader("Currency");
        ordersGrid.addColumn(Rental::totalExcludingTax).setHeader("Total");
        ordersGrid.addColumn(new ComponentRenderer<>(order -> new Button("Details", evt -> showOrder(order.id()))));
        add(ordersGrid);

        var createOrder = new Button("Create Rental", et -> createOrder());
        createOrder.getElement().getThemeList().set("primary", true);
        var refresh = new Button("Refresh", evt -> refreshOrders());

        var buttons = new FlexLayout(refresh, createOrder);
        buttons.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttons.setWidth("100%");
        add(buttons);

        refreshOrders();
    }

    private void refreshOrders() {
        ordersGrid.setItems(rentalCatalog.findAll());
    }

    private void createOrder() {
        getUI().ifPresent(ui -> ui.navigate(CreateRentalView.class));
    }

    private void showOrder(RentalId rentalId) {
        getUI().ifPresent(ui -> ui.navigate(RentalDetailsView.class, rentalId.toUUID()));
    }
}
