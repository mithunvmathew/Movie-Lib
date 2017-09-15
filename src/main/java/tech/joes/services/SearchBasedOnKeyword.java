package tech.joes.services;

import java.io.IOException;
import java.util.Collection;

import org.json.simple.parser.ParseException;

import tech.joes.models.Movie;

// TODO: Auto-generated Javadoc
/**
 * The Interface SearchBasedOnKeyword.
 */
public interface SearchBasedOnKeyword {
	
	/**
	 * Search by keyword service.
	 *
	 * @param keyword the keyword
	 * @return the collection
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Collection<Movie> searchByKeywordService(String keyword) throws IOException, ParseException;
}
