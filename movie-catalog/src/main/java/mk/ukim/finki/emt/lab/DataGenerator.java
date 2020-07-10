//package net.pkhapps.ddd.productcatalog;
//
//import net.pkhapps.ddd.productcatalog.domain.model.Product;
//import net.pkhapps.ddd.productcatalog.domain.model.ProductRepository;
//import Currency;
//import Money;
//import net.pkhapps.ddd.shared.domain.financial.VAT;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//
//@Component
//class DataGenerator {
//
//    private final ProductRepository productRepository;
//
//    DataGenerator(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    @PostConstruct
//    @Transactional
//    public void generateData() {
//        var products = new ArrayList<Product>();
//        products.add(createProduct("Flashlight L", "A large flashlight", new VAT(24), new Money(Currency.EUR, 5642)));
//        products.add(createProduct("Flashlight M", "A medium flashlight", new VAT(24), new Money(Currency.EUR, 4029)));
//        products.add(createProduct("Flashlight S", "A small flashlight", new VAT(24), new Money(Currency.EUR, 2416)));
//        productRepository.saveAll(products);
//    }
//
//    private Product createProduct(String name, String description, VAT vat, Money price) {
//        var product = new Product(name, price, vat);
//        product.setDescription(description);
//        return product;
//    }
//}
package mk.ukim.finki.emt.lab;

import mk.ukim.finki.emt.lab.domain.model.*;
import mk.ukim.finki.emt.lab.domain.Quality;
import mk.ukim.finki.emt.lab.domain.financial.Currency;
import mk.ukim.finki.emt.lab.domain.financial.Fee4K;
import mk.ukim.finki.emt.lab.domain.financial.Money;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
class DataGenerator {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    DataGenerator(MovieRepository movieRepository, DirectorRepository directorRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.genreRepository = genreRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData() {
        var movies = new ArrayList<Movie>();
        var directors = new ArrayList<Director>();
        var genres = new ArrayList<Genre>();

        directors.add(new Director("M. Night", "Shyamalan", "M. Night Shyamalan born August 6, 1970 is an American filmmaker, philanthropist and actor. He is known for making films with contemporary supernatural plots and twist endings. He was born in Mahé, Pondicherry, India, and raised in Penn Valley, Pennsylvania. "));
        genres.add(new Genre("thriller", "Thriller film, also known as suspense film or suspense thriller, is a broad film genre that evokes excitement and suspense in the audience. The suspense element found in most films' plots is particularly exploited by the filmmaker in this genre. Tension is created by delaying what the audience sees as inevitable, and is built through situations that are menacing or where escape seems impossible.[2]"));
        movies.add(createMovie("Split ", "The film follows a man with 24 different personalities who kidnaps and imprisons three teenage girls in an isolated underground facility.", new Fee4K(24), new Money(Currency.EUR, 3.99),
                "117 min", 7.3, Quality.valueOf("fourK"), new ArrayList<>(Arrays.asList(genres.get(0))),
                new ArrayList<>(Arrays.asList(directors.get(0)))));

        directors.add(new Director("Martin", "Scorsese", "Martin Charles Scorsese born November 17, 1942) is an American film director, producer, screenwriter, and actor. One of the major figures of the New Hollywood era, he is widely regarded as one of the most significant and influential directors in film history. Scorsese's body of work explores themes such as Italian-American identity, Catholic concepts of guilt and redemption,faith, machismo, crime and tribalism. Many of his films are known for their depiction of violence, and the liberal use of profanity and rock music. "));
        genres.add(new Genre("crime", "Crime films, in the broadest sense, are a film genre inspired by and analogous to the crime fiction literary genre. Films of this genre generally involve various aspects of crime and its detection. Stylistically, the genre may overlap and combine with many other genres, such as drama or gangster film, but also include comedy, and, in turn, is divided into many sub-genres, such as mystery, suspense or noir."));
        movies.add(createMovie("The Irishman", "The film follows Frank Sheeran, a truck driver who becomes a hitman involved with mobster Russell Bufalino  and his crime family, including his time working for the powerful Teamster Jimmy Hoffa.", new Fee4K(24), new Money(Currency.EUR, 3.99),
                "209 min" , 7.9, Quality.valueOf("fourK"), new ArrayList<>(Arrays.asList(genres.get(1))),
                new ArrayList<>(Arrays.asList(directors.get(1)))));

        directors.add(new Director(" Quentin", "Tarantino", "Quentin Jerome Tarantino ( born March 27, 1963) is an American filmmaker and actor. His films are characterized by nonlinear storylines, aestheticization of violence, extended scenes of dialogue, ensemble casts, references to popular culture and a wide variety of other films, soundtracks primarily containing songs and score pieces from the 1960s to the 1980s, alternate history, and features of neo-noir film."));
        genres.add(new Genre("war", "War film is a film genre concerned with warfare, typically about naval, air, or land battles, with combat scenes central to the drama. It has been strongly associated with the 20th century. The fateful nature of battle scenes means that war films often end with them. Themes explored include combat, survival and escape, camaraderie between soldiers, sacrifice, the futility and inhumanity of battle, the effects of war on society, and the moral and human issues raised by war. "));
        movies.add(createMovie("Inglourious Basterds", "The film tells an alternate history story of two plots to assassinate Nazi Germany's leadership, one planned by Shosanna Dreyfus , a young French Jewish cinema proprietor, and the other by a team of Jewish American soldiers led by First Lieutenant Aldo Raine. ", new Fee4K(24), new Money(Currency.EUR, 3.99),
                "153 min", 8.3, Quality.valueOf("fourK"), new ArrayList<>(Arrays.asList(genres.get(2))),
                new ArrayList<>(Arrays.asList(directors.get(2)))));


//        directorRepository.saveAll(directors);
//        genreRepository.saveAll(genres);
//        movieRepository.saveAll(movies);
    }

    private Movie createMovie(String name, String description, Fee4K fee4K, Money price,
                              String duration, Double rating, Quality quality,
                              List<Genre> genres, List<Director> directors) {
        return new Movie(name, price, fee4K, duration, rating, quality, description, genres, directors);
    }
}
