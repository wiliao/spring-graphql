package com.example.takehome.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.takehome.client.CountryClient;
import com.example.takehome.model.Continent;
import com.example.takehome.dto.ContinentDto;
import com.example.takehome.dto.CountryDto;
import com.example.takehome.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Slf4j
@Service
public class ContinentService {

	private final CountryClient client;

	public ContinentService(CountryClient client) {
		this.client = client;
	}

	public Continent getContinentDetails(String countryCode) {

		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

			Continent continent = new Continent();
			CountryDto countryDto = client.getCountryDetails(countryCode);
			log.debug(ow.writeValueAsString(countryDto));

			continent.setName(countryDto.getData().getCountry().getContinent().getName());
			continent.setCountries(new ArrayList<>());
			continent.getCountries().add(countryDto.getData().getCountry().getCode());

			String continentCode = countryDto.getData().getCountry().getContinent().getCode();
			ContinentDto continentDto = client.getCountries(continentCode);
			log.info(ow.writeValueAsString(continentDto));
			continent.setOtherCountries(new ArrayList<>());
			continent.getOtherCountries()
					.addAll(continentDto.getData().getCountries().stream()
							.filter(c -> !c.getCode().equalsIgnoreCase(countryCode)).map(c -> c.getCode())
							.collect(Collectors.toList()));
			return continent;
		} catch (NullPointerException ex1) {
			log.error(ex1.getMessage());
			throw new ResourceNotFoundException("Country code: " + countryCode);
		} catch (IOException ex2) {
			log.error(ex2.getMessage());
			throw new RuntimeException("Can not get country details for " + countryCode);
		}

	}

}
