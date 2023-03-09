package com.example.takehome.model;

import java.util.List;

import lombok.Data;

@Data
public class Continent {
	private List<String> countries;
	private String name;
	private List<String> otherCountries;
}
