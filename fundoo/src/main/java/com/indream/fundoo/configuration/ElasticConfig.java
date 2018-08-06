/**
 * 
 */
package com.indream.fundoo.configuration;

import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Akshay
 *
 */
@Configuration
public class ElasticConfig {

	@Bean
	public RestHighLevelClient getHighClient() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
		return client;
	}

	@PreDestroy
	public void closeEsClient() {
		try {
			getHighClient().close();
		} catch (Exception e) {
			System.out.println("Error while closing elasticsearch client");

		}

	}

}
