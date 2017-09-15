package tech.joes.utilities;

import org.elasticsearch.client.RestClient;

/**
 * The Interface ElasticSearchRestClient.
 */
public interface ElasticSearchRestClient {

	/**
	 * Connect.
	 *
	 * @return the rest client
	 */
	public RestClient connect();
}
