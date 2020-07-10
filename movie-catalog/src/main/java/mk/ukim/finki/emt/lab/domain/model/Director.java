package mk.ukim.finki.emt.lab.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import mk.ukim.finki.emt.lab.domain.FullName;
import mk.ukim.finki.emt.lab.domain.base.AbstractEntity;
import mk.ukim.finki.emt.lab.domain.base.DomainObjectId;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directors")
public class Director extends AbstractEntity<DirectorId> {


    @Column(name = "biography", length = 2048)
    private String biography;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "secondName", column = @Column(name = "second_name"))
    })
    private FullName name;

    public Director() {
    }

    //    public Director(String firstName, String lastName, String biography){
//        id = DomainObjectId.randomId(DirectorId.class);
//        setName(firstName, lastName);
//        setBiography(biography);
//    }
    public Director(String firstName, String lastName, String biography){
        super(DomainObjectId.randomId(DirectorId.class));
        setName(firstName, lastName);
        setBiography(biography);
        movies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }

    private void setName(@NonNull String firstName, @NonNull String lastName) {
        this.name = new FullName(firstName, lastName);
    }

    @NonNull
    @JsonProperty("name")
    public String name(){
        return name.toString();
    }

    @NonNull
    @JsonProperty("biography")
    public String biography() {
        return biography;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "directors")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Movie> movies;


    private void setBiography(@NonNull String biography) {
        this.biography = biography;
    }

}


