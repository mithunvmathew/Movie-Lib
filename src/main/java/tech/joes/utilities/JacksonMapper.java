package tech.joes.utilities;

import java.io.IOException;
import java.util.Collection;

import org.elasticsearch.client.Response;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

import tech.joes.models.Movie;

/**
 * The Interface JacksonMapper.
 */
public interface JacksonMapper {

	/**
	 * Object to json string.
	 *
	 * @param obj the obj
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	public String objectToJsonString(Object obj) throws JsonProcessingException;

	/**
	 * Gets the back the data from response.
	 *
	 * @param response the response
	 * @return the back the data from response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 */
	public Collection<Movie> getBackTheDataFromResponse(Response response) throws IOException, ParseException;

}
