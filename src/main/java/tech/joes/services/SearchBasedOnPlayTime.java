package tech.joes.services;

import java.io.IOException;
import java.util.Collection;

import org.json.simple.parser.ParseException;

import tech.joes.models.Movie;

// TODO: Auto-generated Javadoc
/**
 * The Interface SearchBasedOnPlayTime.
 */
public interface SearchBasedOnPlayTime {
	
	/**
	 * Process the query.
	 *
	 * @param minValue the min value
	 * @param maxValue the max value
	 * @return the collection
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Collection<Movie> processTheQuery(String minValue, String maxValue) throws IOException, ParseException;

}
