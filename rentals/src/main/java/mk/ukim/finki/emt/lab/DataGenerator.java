package mk.ukim.finki.emt.lab;

import mk.ukim.finki.emt.lab.domain.model.RentalRepository;
import net.pkhapps.ddd.orders.domain.model.*;
import mk.ukim.finki.emt.lab.domain.financial.CurrencyConverter;

import javax.annotation.PostConstruct;

//@Component Disabled for now
public class DataGenerator {

    private final RentalRepository rentalRepository;
    private final CurrencyConverter currencyConverter;

    public DataGenerator(RentalRepository rentalRepository, CurrencyConverter currencyConverter) {
        this.rentalRepository = rentalRepository;
        this.currencyConverter = currencyConverter;
    }

    @PostConstruct
    public void generateData() {

    }
}
