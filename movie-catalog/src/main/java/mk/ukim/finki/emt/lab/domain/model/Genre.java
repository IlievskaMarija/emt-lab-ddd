package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.base.AbstractEntity;
import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "genres")
public class Genre extends AbstractEntity<GenreId> {

    @Column(name = "genre")
    private String name;

    @Column(name = "description", length = 2048)
    private String description;

    public Genre() {
    }
    public Genre(String name, String description){
        super(DomainObjectId.randomId(GenreId.class));
        setName(name);
        setDescription(description);
        movies = new ArrayList<>();
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }

    private void setName(@NonNull String name) {
        this.name = name;
    }

    private void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    @JsonProperty("name")
    public String name(){
        return name;
    }


    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Movie> movies;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Director> directors;
}


















