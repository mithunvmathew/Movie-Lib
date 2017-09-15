package tech.joes.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.elasticsearch.client.Response;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import tech.joes.repositories.ElasticSerachMovieRepository;
import tech.joes.utilities.JacksonMapper;


@RunWith(JMockit.class)
public class SearchBasedOnKeywordTest {
	
	@Tested
	SearchBasedOnKeywordImplementation searchBasedOnKeyword;
	
	@Injectable
	ElasticSerachMovieRepository movieElasticSerachRepository;
	
	@Injectable
	JacksonMapper jacksonMapper;
	
	@Injectable
	Response response;
	
	@Injectable
	StatusLine sline;
	
	@Test
	public void shouldReturnCollectionOfMoviesWhenSearchwithKeyword() throws Exception{
		
		new Expectations() {
			{
				movieElasticSerachRepository.getMoviesBasedOnKeyword((String) any);
				returns(response);

				response.getStatusLine();
				returns(sline);
				
				sline.getStatusCode();
				returns(200);

			}
		};
		
		searchBasedOnKeyword.searchByKeywordService("mmm");
		
	}

}
