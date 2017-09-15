package tech.joes.utilities;

import static tech.joes.utilities.Constants.BLURB;
import static tech.joes.utilities.Constants.HITS;
import static tech.joes.utilities.Constants.ID;
import static tech.joes.utilities.Constants.RELEASEYEAR;
import static tech.joes.utilities.Constants.RUNTIME;
import static tech.joes.utilities.Constants.SOURCE;
import static tech.joes.utilities.Constants.TITLE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import org.elasticsearch.client.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.joes.models.Movie;

/**
 * The Class JacksonMapperImpl.
 */
@Component
public class JacksonMapperImpl implements JacksonMapper {

	/** The mapper. */
	ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

	
	/* (non-Javadoc)
	 * @see tech.joes.utilities.JacksonMapper#objectToJsonString(java.lang.Object)
	 */
	@Override
	public String objectToJsonString(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	
	/* (non-Javadoc)
	 * @see tech.joes.utilities.JacksonMapper#getBackTheDataFromResponse(org.elasticsearch.client.Response)
	 */
	@Override
	public Collection<Movie> getBackTheDataFromResponse(Response response) {

		StringBuffer responseBuf = null;
		String inputLine;
		responseBuf = new StringBuffer();
		Collection<Movie> result = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()))) {
			while ((inputLine = bufferedReader.readLine()) != null) {
				responseBuf.append(inputLine);
			}
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(responseBuf.toString());
			JSONObject hitsource1 = (JSONObject) jsonObject.get(HITS);
			JSONArray hitsource2 = (JSONArray) hitsource1.get(HITS);
			for (int i = 0; i < hitsource2.size(); i++) {
				JSONObject arrObj = (JSONObject) hitsource2.get(i);
				JSONObject srcObj = (JSONObject) arrObj.get(SOURCE);
				Movie movie = new Movie();

				movie.setTitle((String) srcObj.get(TITLE));
				movie.setBlurb((String) srcObj.get(BLURB));
				movie.setReleaseYear(Integer.valueOf(srcObj.get(RELEASEYEAR).toString()));
				movie.setRuntime(Integer.valueOf(srcObj.get(RUNTIME).toString()));
				movie.setId(Integer.valueOf(srcObj.get(ID).toString()));
				result.add(movie);
			}
		} catch (IOException | ParseException ex) {
			throw new RuntimeException(ex.getMessage());
		}

		return result;
	}
}
