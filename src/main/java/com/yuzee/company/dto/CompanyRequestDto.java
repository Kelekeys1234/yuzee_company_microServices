package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyRequestDto {
	
	@JsonProperty("company_name")
	private String companyName;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("industry")
	private String industry;
	
	@JsonProperty("industry_type")
	private String industryType;
	
	@JsonProperty("year_founded")
	private Integer yearFounded;
	
	@JsonProperty("specialities")
	private List<SpecialityDto> listOfSpecialityDto = new ArrayList<>();
}