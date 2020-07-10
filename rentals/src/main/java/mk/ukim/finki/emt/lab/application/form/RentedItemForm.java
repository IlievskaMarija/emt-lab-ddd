package mk.ukim.finki.emt.lab.application.form;

import mk.ukim.finki.emt.lab.domain.model.Movie;
import mk.ukim.finki.emt.lab.domain.Type;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RentedItemForm implements Serializable {

    @NotNull
    private Movie movie;
    @NotNull
    private Type type;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type  type) {
        this.type = type;
    }
}
