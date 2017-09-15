package tech.joes.utilities;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class ElasticSearchRestClientImpl.
 */
@Component
public class ElasticSearchRestClientImpl implements ElasticSearchRestClient {

	/** The host. */
	@Value("${elasticsearch.server}")
	private String host;

	/** The protocol. */
	@Value("${elasticsearch.protocol}")
	private String protocol;
	

	/* (non-Javadoc)
	 * @see tech.joes.utilities.ElasticSearchRestClient#connect()
	 */
	@Override
	public RestClient connect() {
		return RestClient.builder(new HttpHost(host, 9200, protocol), new HttpHost(host, 9201, protocol)).build();
	}
}
