package mk.ukim.finki.emt.lab.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Repository of {@link Director}s.
 */
public interface DirectorRepository extends JpaRepository<Director, DirectorId> {
    @Query("select p from Director p order by p.name")
    List<Director> findActive();
}



