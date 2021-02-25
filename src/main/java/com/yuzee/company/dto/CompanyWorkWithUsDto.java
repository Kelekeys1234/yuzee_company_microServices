package com.yuzee.company.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyWorkWithUsDto {

	@JsonProperty("company_work_with_us_id")
	private String companyWorkWithUsId;
	
	@JsonProperty("title_value")
	private String titleValue;
	
	@NotEmpty(message = "title_name should not be null / empty")
	@JsonProperty("title_name")
	private String titleName;
	
	@NotEmpty(message = "description should not be null / empty")
	@JsonProperty("description")
	private String description;
}