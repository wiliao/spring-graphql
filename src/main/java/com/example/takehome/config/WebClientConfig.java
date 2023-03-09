package com.example.takehome.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Configuration
public class WebClientConfig {

	@Value("${graphql.server.url}")
	private String baseUrl;

	@Bean
	WebClient webClient() {
		return WebClient.builder().baseUrl(baseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}

}
