package com.example.takehome.dto;

import lombok.Getter;

/**
 * @Author: William Liao
 * @Email: liaotoca@yahoo.com
 */
@Getter
public class CountryDto {

  private CountryData data;

  @Getter
  public class CountryData {

    private Country country;

    @Getter
    public class Country {

//      private String name;
//      private String capital;
//      private String currency;
    	private String code;
    	private Continent continent;
    	
    	@Getter
    	public class Continent {
    		private String code;
    		private String name;
    	}
    	
    	
    }
  }
}

