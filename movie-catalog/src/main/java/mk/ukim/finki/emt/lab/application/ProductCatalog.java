package mk.ukim.finki.emt.lab.application;

import mk.ukim.finki.emt.lab.domain.model.Movie;
import mk.ukim.finki.emt.lab.domain.model.MovieId;
import mk.ukim.finki.emt.lab.domain.model.MovieRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
public class ProductCatalog {

    private final MovieRepository productRepository;

    ProductCatalog(MovieRepository productRepository) {
        this.productRepository = productRepository;
    }

    @NonNull
    public Optional<Movie> findById(@NonNull MovieId productId) {
        Objects.requireNonNull(productId, "productId must not be null");
        return productRepository.findById(productId);
    }

    @NonNull
    public List<Movie> findAll() {
        return productRepository.findAll();
    }
}
