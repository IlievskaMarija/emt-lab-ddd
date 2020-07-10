package mk.ukim.finki.emt.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Clock;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@Import(SharedConfiguration.class)
public class RentalsApp {

    public static void main(String[] args) {
        SpringApplication.run(RentalsApp.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
