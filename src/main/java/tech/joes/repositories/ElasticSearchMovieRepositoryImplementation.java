package tech.joes.repositories;

import static tech.joes.utilities.Constants.DELETE;
import static tech.joes.utilities.Constants.DELETEINDEX;
import static tech.joes.utilities.Constants.FINDINDEX;
import static tech.joes.utilities.Constants.GET;
import static tech.joes.utilities.Constants.POST;
import static tech.joes.utilities.Constants.RANGEQUERY;
import static tech.joes.utilities.Constants.SAVEINDEX;
import static tech.joes.utilities.Constants.SEARCHINDEX;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tech.joes.models.Movie;
import tech.joes.models.PlayTime;
import tech.joes.services.SearchBasedOnPlayTimeImplementation;
import tech.joes.utilities.ElasticSearchRestClient;
import tech.joes.utilities.JacksonMapper;

/**
 * The Class ElasticSearchMovieRepositoryImplementation.
 */
@Repository
public class ElasticSearchMovieRepositoryImplementation implements ElasticSerachMovieRepository {
	
	Logger LOGGER = Logger.getLogger(ElasticSearchMovieRepositoryImplementation.class);

	/** The elastic search rest client. */
	@Autowired
	ElasticSearchRestClient elasticSearchRestClient;

	/** The jackson mapper. */
	@Autowired
	JacksonMapper jacksonMapper;

	/* (non-Javadoc)
	 * @see tech.joes.repositories.ElasticSerachMovieRepository#save(tech.joes.models.Movie)
	 */
	@Override
	public void save(Movie movie) throws IOException {

		RestClient restClient = elasticSearchRestClient.connect();

		Map<String, String> params = Collections.emptyMap();
		String jsonString = jacksonMapper.objectToJsonString(movie);
		LOGGER.debug("jsonString:"+jsonString);
		HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
		restClient.performRequest(POST, SAVEINDEX, params, entity);
		restClient.close();

	}

	
	/* (non-Javadoc)
	 * @see tech.joes.repositories.ElasticSerachMovieRepository#findAll()
	 */
	@Override
	public Collection<Movie> findAll() throws IOException, ParseException {
		Map<String, String> params = Collections.singletonMap("pretty", "true");
		RestClient restClient = elasticSearchRestClient.connect();
		Response response = restClient.performRequest(GET, FINDINDEX, params);
		Collection<Movie> result = jacksonMapper.getBackTheDataFromResponse(response);
		LOGGER.info("No Of Docs:"+result.size());
		restClient.close();
		return result;

	}

	
	/* (non-Javadoc)
	 * @see tech.joes.repositories.ElasticSerachMovieRepository#getMoviesBasedOnPlayTime(tech.joes.models.PlayTime)
	 */
	@Override
	public Response getMoviesBasedOnPlayTime(PlayTime playTime) throws IOException, ParseException {

		RestClient restClient = elasticSearchRestClient.connect();

		Map<String, String> params = Collections.emptyMap();
		String jsonString = "{" + RANGEQUERY + jacksonMapper.objectToJsonString(playTime)
				+ "}}}";
		LOGGER.debug("jsonString:"+jsonString);
		HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
		Response response = restClient.performRequest(POST, SEARCHINDEX, params, entity);
		restClient.close();
		return response;
	}

	
	/* (non-Javadoc)
	 * @see tech.joes.repositories.ElasticSerachMovieRepository#getMoviesBasedOnKeyword(java.lang.String)
	 */
	@Override
	public Response getMoviesBasedOnKeyword(String query) throws IOException, ParseException {

		RestClient restClient = elasticSearchRestClient.connect();

		Map<String, String> params = Collections.emptyMap();

		HttpEntity entity = new NStringEntity(query, ContentType.APPLICATION_JSON);
		Response response = restClient.performRequest(POST, SEARCHINDEX, params, entity);
		restClient.close();
		return response;

	}

	
	/* (non-Javadoc)
	 * @see tech.joes.repositories.ElasticSerachMovieRepository#deleteIndex()
	 */
	public void deleteIndex() throws IOException {
		RestClient restClient = elasticSearchRestClient.connect();
		restClient.performRequest(DELETE, DELETEINDEX);
	}

}
