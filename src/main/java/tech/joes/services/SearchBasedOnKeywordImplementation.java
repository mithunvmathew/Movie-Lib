package tech.joes.services;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.elasticsearch.client.Response;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.joes.models.Movie;
import tech.joes.repositories.ElasticSerachMovieRepository;
import tech.joes.utilities.JacksonMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchBasedOnKeywordImplementation.
 */
@Service
public class SearchBasedOnKeywordImplementation implements SearchBasedOnKeyword {

	Logger LOGGER = Logger.getLogger(SearchBasedOnKeywordImplementation.class);
	
	/** The movie elastic serach repository. */
	@Autowired
	private ElasticSerachMovieRepository movieElasticSerachRepository;

	/** The jackson mapper. */
	@Autowired
	private JacksonMapper jacksonMapper;

	
	/* (non-Javadoc)
	 * @see tech.joes.services.SearchBasedOnKeyword#searchByKeywordService(java.lang.String)
	 */
	@Override
	public Collection<Movie> searchByKeywordService(String keyword) throws IOException, ParseException {

		Collection<Movie> movieList = null;
		String jsonString = "{" + "\"query\":{\"multi_match\" : {\"query\":" + "\"" + keyword + "\""
				+ ",\"fields\": [ \"title\", \"blurb\" ]" + "}}}";
		LOGGER.debug(jsonString);
		Response response = movieElasticSerachRepository.getMoviesBasedOnKeyword(jsonString);
		LOGGER.debug("response :"+response );
		if (response.getStatusLine().getStatusCode() == 200) {
			movieList = jacksonMapper.getBackTheDataFromResponse(response);
		}
		return movieList;
	}

}
