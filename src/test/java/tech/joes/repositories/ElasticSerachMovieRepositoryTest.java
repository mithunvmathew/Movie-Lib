package tech.joes.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import tech.joes.models.Movie;
import tech.joes.models.PlayTime;
import tech.joes.utilities.ElasticSearchRestClient;
import tech.joes.utilities.JacksonMapper;

@RunWith(JMockit.class)
public class ElasticSerachMovieRepositoryTest {

	@Tested
	ElasticSearchMovieRepositoryImplementation elasticSerachMovieRepository;

	@Injectable
	RestClient restClient;

	@Injectable
	ElasticSearchRestClient elasticSearchRestClient;

	@Injectable
	Response response;

	@Injectable
	JacksonMapper jacksonMapper;

	@Test
	public void shouldReturnResponseWhenElasticSearchWithKeyword() throws Exception {
		Map<String, String> testMap = new HashMap<>();
		new Expectations() {
			{
				elasticSearchRestClient.connect();
				returns(restClient);

				restClient.performRequest("POST", "/movielib/_search", testMap, (HttpEntity) any);
				returns(response);

				restClient.close();
			}
		};

		elasticSerachMovieRepository.getMoviesBasedOnKeyword("tui");

	}

	@Test
	public void shouldReturnResponseWhenElasticSearchWithPlayTime() throws Exception {
		Map<String, String> testMap = new HashMap<>();
		PlayTime playTime = new PlayTime("1000", "3000");

		new Expectations() {
			{
				elasticSearchRestClient.connect();
				returns(restClient);

				jacksonMapper.objectToJsonString(playTime);
				returns("");

				restClient.performRequest("POST", "/movielib/_search", testMap, (HttpEntity) any);
				returns(response);

				restClient.close();
			}
		};

		elasticSerachMovieRepository.getMoviesBasedOnPlayTime(playTime);

	}

	@Test
	public void shouldReturnAllDocsWhenElasticSearchWithFindAll() throws Exception {
		Map<String, String> params = Collections.singletonMap("pretty", "true");
		// Map<String, String> testMap = new HashMap<>();
		Movie movie = new Movie("tui", 2017, 1888, "family Storry", 1);
		List<Movie> expectedResult = new ArrayList<>();
		Collection<Movie> result;
		expectedResult.add(movie);

		new Expectations() {
			{
				elasticSearchRestClient.connect();
				returns(restClient);

				restClient.performRequest("GET", "/movielib/movie/_search", params);
				returns(response);

				jacksonMapper.getBackTheDataFromResponse((Response) any);
				returns(result);

				restClient.close();

			}
		};

		elasticSerachMovieRepository.findAll();
	}
}
