package tech.joes.controllers; /**
								* Created by joe on 05/04/2017.
								*/

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tech.joes.models.Movie;
import tech.joes.repositories.ElasticSerachMovieRepository;
import tech.joes.repositories.MovieRepository;
import tech.joes.services.SearchBasedOnKeyword;
import tech.joes.services.SearchBasedOnPlayTime;

// TODO: Auto-generated Javadoc
/**
 * The Class MovieController.
 */
@RestController
@EnableAutoConfiguration
public class MovieController {

	/** The repository. */
	@Autowired
	private MovieRepository repository;

	/** The query based on play time. */
	@Autowired
	private SearchBasedOnPlayTime queryBasedOnPlayTime;

	/** The search based on keyword. */
	@Autowired
	private SearchBasedOnKeyword searchBasedOnKeyword;

	/** The elastic serach repository. */
	@Autowired
	private ElasticSerachMovieRepository elasticSerachRepository;

	/**
	 * Gets the all movies.
	 *
	 * @return the all movies
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/movies")
	@ResponseBody
	public ResponseEntity<Collection<Movie>> getAllMovies() throws IOException, ParseException {

		Collection<Movie> movies = elasticSerachRepository.findAll();
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	/**
	 * Gets the movie with id.
	 *
	 * @param id the id
	 * @return the movie with id
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/movies/{id}")
	@ResponseBody
	public ResponseEntity<Movie> getMovieWithId(@PathVariable Integer id) {

		Movie movie = repository.findOne(id);

		if (movie == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(movie, HttpStatus.OK);
	}

	/**
	 * Gets the movies by release year.
	 *
	 * @param year the year
	 * @return the movies by release year
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/movies/releaseYear/{year}")
	@ResponseBody
	public ResponseEntity<List<Movie>> getMoviesByReleaseYear(@PathVariable Integer year) {

		List<Movie> movies = repository.findMoviesByReleaseYear(year);

		if (movies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	/**
	 * Adds the movie.
	 *
	 * @param input the input
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/movies")
	@ResponseBody
	public ResponseEntity addMovie(@RequestBody Movie input) {
		return new ResponseEntity<>(repository.save(input), HttpStatus.CREATED);
	}

	/**
	 * Update movie.
	 *
	 * @param id the id
	 * @param input the input
	 * @return the response entity
	 */
	/*
	 * Update movie with id with new values
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/movies/{id}")
	@ResponseBody
	public ResponseEntity updateMovie(@PathVariable Integer id, @RequestBody Movie input) {
		Movie movie = repository.findOne(id);

		if (movie == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		movie.setTitle(input.getTitle());
		movie.setBlurb(input.getBlurb());
		movie.setReleaseYear(input.getReleaseYear());
		movie.setRuntime(input.getRuntime());

		return new ResponseEntity<>(repository.save(movie), HttpStatus.OK);

	}

	/**
	 * Delete movie.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/movies/{id}")
	@ResponseBody
	public ResponseEntity deleteMovie(@PathVariable Integer id) {
		Movie movie = repository.findOne(id);

		if (movie == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		repository.delete(movie);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	
	/**
	 * Gets the movies by play time.
	 *
	 * @param minValue the min value
	 * @param maxValue the max value
	 * @return the movies by play time
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/movies/playtime")
	@ResponseBody
	public ResponseEntity<Collection<Movie>> getMoviesByPlayTime(
			@RequestParam(value = "min", required = false) String minValue,
			@RequestParam(value = "max", required = false) String maxValue) throws IOException, ParseException {

		Collection<Movie> movies = queryBasedOnPlayTime.processTheQuery(minValue, maxValue);

		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
	

	/**
	 * Gets the movies by search.
	 *
	 * @param keyword the keyword
	 * @return the movies by search
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/movies/search")
	@ResponseBody
	public ResponseEntity<Collection<Movie>> getMoviesBySearch(
			@RequestParam(value = "keyword", required = false) String keyword) throws IOException, ParseException {

		Collection<Movie> movies = searchBasedOnKeyword.searchByKeywordService(keyword);

		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
}