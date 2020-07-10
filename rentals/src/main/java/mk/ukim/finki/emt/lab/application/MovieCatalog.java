package mk.ukim.finki.emt.lab.application;

import mk.ukim.finki.emt.lab.domain.model.Movie;

import java.util.List;

public interface MovieCatalog {

    List<Movie> findAll();
}
