package mk.ukim.finki.emt.lab.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, MovieId> {


}