package com.example.takehome.client;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.takehome.model.GraphqlRequestBody;
import com.example.takehome.dto.ContinentDto;
import com.example.takehome.dto.CountryDto;
import com.example.takehome.util.GraphqlSchemaReaderUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Service
@Slf4j
public class CountryClient {

	private final WebClient webClient;

	public CountryClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public CountryDto getCountryDetails(final String countryCode) throws IOException {

		GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

		final String query = GraphqlSchemaReaderUtils.getSchemaFromFileName("getCountryDetails");
		final String variables = GraphqlSchemaReaderUtils.getSchemaFromFileName("code_variables");

		log.info("query: {}", query);
		log.info("variables: {}", variables);

		graphQLRequestBody.setQuery(query);
		graphQLRequestBody.setVariables(variables.replace("countryCode", countryCode));

		return webClient.post().bodyValue(graphQLRequestBody).retrieve().bodyToMono(CountryDto.class).block();
	}

	public ContinentDto getCountries(final String continentCode) throws IOException {

		GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

		final String query = GraphqlSchemaReaderUtils.getSchemaFromFileName("getCountries");
		final String variables = GraphqlSchemaReaderUtils.getSchemaFromFileName("filter_variables");

		log.info("query: {}", query);
		log.info("variables: {}", variables);

		graphQLRequestBody.setQuery(query);
		graphQLRequestBody.setVariables(variables.replace("continentCode", continentCode));

		return webClient.post().bodyValue(graphQLRequestBody).retrieve().bodyToMono(ContinentDto.class).block();
	}

}
