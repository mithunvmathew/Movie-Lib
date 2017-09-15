package tech.joes.services;

import org.apache.http.StatusLine;
import org.elasticsearch.client.Response;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import tech.joes.models.PlayTime;
import tech.joes.repositories.ElasticSerachMovieRepository;
import tech.joes.utilities.JacksonMapper;

@RunWith(JMockit.class)
public class SearchBasedOnPlayTimeTest {

	@Tested
	SearchBasedOnPlayTimeImplementation searchBasedOnPlayTime;
	
	@Injectable
	ElasticSerachMovieRepository movieElasticSerachRepository;
	
	@Injectable
	JacksonMapper jacksonMapper;
	
	@Injectable
	Response response;
	
	@Injectable
	StatusLine sline;
	
	@Test
	public void shouldReturnCollectionOfMoviesWhenSearchwithPlayTime() throws Exception{
		PlayTime playTime = new PlayTime("200","360");
		new Expectations() {
			{
				movieElasticSerachRepository.getMoviesBasedOnPlayTime((PlayTime) any);
				returns(response);

				response.getStatusLine();
				returns(sline);
				
				sline.getStatusCode();
				returns(200);

			}
		};
		
		searchBasedOnPlayTime.processTheQuery("1000", "2000");
		
	}
}
