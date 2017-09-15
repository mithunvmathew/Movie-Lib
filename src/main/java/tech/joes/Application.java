package tech.joes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.javafaker.Faker;

import tech.joes.models.Movie;
import tech.joes.repositories.ElasticSerachMovieRepository;
import tech.joes.repositories.MovieRepository;

/**
 * Created by joe on 05/04/2017.
 */
@SpringBootApplication
public class Application {

	Faker faker = new Faker();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner initializeDb(MovieRepository repository) {
		return (args) -> {
			repository.deleteAll();
			// Create and insert some Movies with fake data
			for (int i = 0; i < 20; i++) {
				repository.save(new Movie(faker.lorem().word(), faker.number().numberBetween(1970, 2017),
						faker.number().numberBetween(1, 9999), faker.lorem().paragraph()));
			}
		};
	}

	/**
	 * Initialize elastic search db.
	 *
	 * @param repository the repository
	 * @return the command line runner
	 */
	@Bean
	public CommandLineRunner initializeElasticSearchDb(ElasticSerachMovieRepository repository) {
		return (args) -> {
			repository.deleteIndex();
			// Create and insert some Movies with fake data
			for (int i = 0; i < 20; i++) {
				repository.save(new Movie(faker.lorem().word(), faker.number().numberBetween(1970, 2017),
						faker.number().numberBetween(1, 9999), faker.lorem().paragraph(), i));
			}
		};
	}
}