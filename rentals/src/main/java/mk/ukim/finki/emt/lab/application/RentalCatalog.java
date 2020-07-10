package mk.ukim.finki.emt.lab.application;

import mk.ukim.finki.emt.lab.domain.model.RecipientAddress;
import mk.ukim.finki.emt.lab.domain.model.Rental;
import mk.ukim.finki.emt.lab.domain.model.RentalId;
import mk.ukim.finki.emt.lab.domain.model.RentalRepository;
import mk.ukim.finki.emt.lab.domain.model.event.RentalCreated;
import mk.ukim.finki.emt.lab.application.form.RentalForm;
import mk.ukim.finki.emt.lab.application.form.RecipientAddressForm;
import mk.ukim.finki.emt.lab.domain.financial.CurrencyConverter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RentalCatalog {

    private final Validator validator;
    private final RentalRepository rentalRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Clock clock;
    private final CurrencyConverter currencyConverter;

    RentalCatalog(Validator validator,
                  RentalRepository rentalRepository,
                  ApplicationEventPublisher applicationEventPublisher,
                  Clock clock,
                  CurrencyConverter currencyConverter) {
        this.validator = validator;
        this.rentalRepository = rentalRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.clock = clock;
        this.currencyConverter = currencyConverter;
    }

    @NonNull
    public RentalId createOrder(@NonNull RentalForm form) {
        Objects.requireNonNull(form, "form must not be null");
        var constraintViolations = validator.validate(form);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The RentalForm is not valid", constraintViolations);
        }
        var order = rentalRepository.saveAndFlush(toDomainModel(form));
        applicationEventPublisher.publishEvent(new RentalCreated(order.id(), order.orderedOn()));
        return order.id();
    }

    @NonNull
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @NonNull
    public Optional<Rental> findById(@NonNull RentalId rentalId) {
        Objects.requireNonNull(rentalId, "rentalId must not be null");
        return rentalRepository.findById(rentalId);
    }

    public void startProcessing(@Nonnull RentalId rentalId) {
        rentalRepository.findById(rentalId).ifPresent(order -> {
            order.startProcessing(clock);
            rentalRepository.save(order);
        });
    }

    public void finishProcessing(@Nonnull RentalId rentalId) {
        rentalRepository.findById(rentalId).ifPresent(order -> {
            order.finishProcessing(clock);
            rentalRepository.save(order);
        });
    }

    public void watchRentedItems(@Nonnull RentalId rentalId) {
        rentalRepository.findById(rentalId).ifPresent(order -> {
            order.watchRentedItems(clock);
            rentalRepository.save(order);
        });
    }

    @NonNull
    private Rental toDomainModel(@NonNull RentalForm rentalForm) {
        var order = new Rental(clock.instant(), rentalForm.getCurrency(),
                toDomainModel(rentalForm.getShippingAddress()));
        rentalForm.getItems().forEach(item -> order.addItem(item.getMovie(), item.getType().toString(), currencyConverter));
        return order;
    }

    @NonNull
    private RecipientAddress toDomainModel(@NonNull RecipientAddressForm form) {
        return new RecipientAddress(form.getName(), form.getAddressLine1());
    }
}
