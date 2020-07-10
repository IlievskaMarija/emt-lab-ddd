package mk.ukim.finki.emt.lab.rest.controller;

import mk.ukim.finki.emt.lab.application.RentalCatalog;
import mk.ukim.finki.emt.lab.domain.model.Rental;
import mk.ukim.finki.emt.lab.domain.model.RentalId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
class RentalCatalogController {

    private final RentalCatalog rentalCatalog;

    RentalCatalogController(RentalCatalog rentalCatalog) {
        this.rentalCatalog = rentalCatalog;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> findById(@PathVariable("id") String orderId) {
        return rentalCatalog.findById(new RentalId(orderId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/startProcessing")
    public void startProcessing(@PathVariable("id") String orderId) {
        rentalCatalog.startProcessing(new RentalId(orderId));
    }

    @PutMapping("/{id}/finishProcessing")
    public void finishProcessing(@PathVariable("id") String orderId) {
        rentalCatalog.finishProcessing(new RentalId(orderId));
    }

    @GetMapping("/{id}/watchRentedItems")
    public void watchRentedItems(@PathVariable("id") String orderId){
        rentalCatalog.watchRentedItems(new RentalId(orderId));

    }


    @GetMapping
    public List<Rental> findAll() {
        return rentalCatalog.findAll();
    }
}
