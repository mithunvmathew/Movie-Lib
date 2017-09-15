package tech.joes.repositories;

import java.io.IOException;
import java.util.Collection;

import org.elasticsearch.client.Response;
import org.json.simple.parser.ParseException;

import tech.joes.models.Movie;
import tech.joes.models.PlayTime;


/**
 * The Interface ElasticSerachMovieRepository.
 */
public interface ElasticSerachMovieRepository {
	
	/**
	 * Save.
	 *
	 * @param movie the movie
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void save(Movie movie) throws IOException;

	/**
	 * Find all.
	 *
	 * @return the collection
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Collection<Movie> findAll() throws IOException, ParseException;

	/**
	 * Gets the movies based on play time.
	 *
	 * @param playTime the play time
	 * @return the movies based on play time
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Response getMoviesBasedOnPlayTime(PlayTime playTime) throws IOException, ParseException;

	/**
	 * Gets the movies based on keyword.
	 *
	 * @param query the query
	 * @return the movies based on keyword
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Response getMoviesBasedOnKeyword(String query) throws IOException, ParseException;

	/**
	 * Delete index.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleteIndex() throws IOException;

}
