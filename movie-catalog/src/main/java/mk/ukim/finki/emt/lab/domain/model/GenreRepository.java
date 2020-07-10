package mk.ukim.finki.emt.lab.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Repository of {@link Genre}s.
 */
public interface GenreRepository extends JpaRepository<Genre, GenreId> {
    @Query("select p from Genre p order by p.name")
    List<Genre> findActive();
}



