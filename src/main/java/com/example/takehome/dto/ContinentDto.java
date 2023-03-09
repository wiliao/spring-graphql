package com.example.takehome.dto;

import java.util.List;

import lombok.Getter;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Getter
public class ContinentDto {

	private CountryData data;

	@Getter
	public class CountryData {

		private List<Country> countries;

	}

	@Getter
	public static class Country {

		private String code;

	}
}
