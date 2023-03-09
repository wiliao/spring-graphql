package com.example.takehome.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.takehome.model.Continent;
import com.example.takehome.model.ContinentResponse;
import com.example.takehome.service.ContinentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ContinentController {

	private final ContinentService service;

	private final Bucket bucketLow;
	private final Bucket bucketHigh;

	/**
	 * This constructor will inject the rate limit parameters,
	 * the lower rate limit defaults to 5 requests per second, the higher rate limit defaults to 20 requests per second.
	 *
	 * @param service
	 * @param countLow
	 * @param periodLow
	 * @param countHigh
	 * @param periodHigh
	 */
	public ContinentController(ContinentService service, 
			@Value("${graphql.server.rate-limit.count:5}") long countLow,
			@Value("${graphql.server.rate-limit.period:1}") long periodLow,
		    @Value("${graphql.server.rate-limit-authenticated.count:20}") long countHigh,
		    @Value("${graphql.server.rate-limit-authenticated.period:1}") long periodHigh) {
		this.service = service;
		Bandwidth limitLow = Bandwidth.classic(countLow, Refill.greedy(countLow, Duration.ofSeconds(periodLow)));
		this.bucketLow = Bucket.builder().addLimit(limitLow).build();
		Bandwidth limitHigh = Bandwidth.classic(countHigh, Refill.greedy(countHigh, Duration.ofSeconds(periodHigh)));
		this.bucketHigh = Bucket.builder().addLimit(limitHigh).build();
	}

	@GetMapping("/continents")
	public ResponseEntity<ContinentResponse> getContinents(@RequestParam(name = "countryCode") List<String> countryCodes,
			@AuthenticationPrincipal Jwt jwt) throws JsonProcessingException {

		long numOfValidCodes = countryCodes.stream().filter(code -> StringUtils.isAllUpperCase(code) && code.length()==2).count();
		if (numOfValidCodes!=countryCodes.size()) {
			throw new IllegalArgumentException("Country code should be two capital letters!");
		}

		String scope = jwt.getClaims().get("scope").toString();
		log.info("scope: {}", scope);

		if (scope.contains("message:write") && bucketHigh.tryConsume(1)) {
			return callGraphql(countryCodes, jwt);
		} else if (scope.contains("message:read") && bucketLow.tryConsume(1)) {
			return callGraphql(countryCodes, jwt);
		}

		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

	}

	private ResponseEntity<ContinentResponse> callGraphql(List<String> countryCodes, Jwt jwt) throws JsonProcessingException {
		log.info("Input: {}", countryCodes);
		ContinentResponse response = new ContinentResponse();

		Map<String, Continent> map = new HashMap<>();
		for (String code : countryCodes) {
			Continent continent = service.getContinentDetails(code);
			Continent currentContinent = map.putIfAbsent(continent.getName(), continent);
			if (currentContinent != null) {
				currentContinent.getCountries().add(code);
				currentContinent.getOtherCountries().remove(code);
			}
		}
		List<Continent> continents = map.values().stream().collect(Collectors.toList());
		log.debug("{}", continents);
		response.setContinent(continents);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		log.info(ow.writeValueAsString(response));
		return ResponseEntity.ok(response);
	}

}
