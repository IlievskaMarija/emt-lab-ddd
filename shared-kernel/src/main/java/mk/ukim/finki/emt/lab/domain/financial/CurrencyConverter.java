package mk.ukim.finki.emt.lab.domain.financial;

import org.springframework.lang.NonNull;


public interface CurrencyConverter {

    @NonNull
    Money convert(@NonNull Money amount, @NonNull Currency newCurrency);
}
