package tech.joes.services;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Response;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.joes.models.Movie;
import tech.joes.models.PlayTime;
import tech.joes.repositories.ElasticSerachMovieRepository;
import tech.joes.utilities.JacksonMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchBasedOnPlayTimeImplementation.
 */
@Service
public class SearchBasedOnPlayTimeImplementation implements SearchBasedOnPlayTime {
	
	Logger LOGGER = Logger.getLogger(SearchBasedOnPlayTimeImplementation.class);

	/** The movie elastic serach repository. */
	@Autowired
	ElasticSerachMovieRepository movieElasticSerachRepository;

	/** The jackson mapper. */
	@Autowired
	JacksonMapper jacksonMapper;

	
	/* (non-Javadoc)
	 * @see tech.joes.services.SearchBasedOnPlayTime#processTheQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection<Movie> processTheQuery(String minValue, String maxValue) throws IOException, ParseException {

		Collection<Movie> movieList = null;
		PlayTime playTime = new PlayTime(minValue, maxValue);
		Response response = movieElasticSerachRepository.getMoviesBasedOnPlayTime(playTime);
		LOGGER.debug("response:"+response);
		if (response.getStatusLine().getStatusCode() == 200) {
			movieList = jacksonMapper.getBackTheDataFromResponse(response);
		}
		return movieList;

	}

}
