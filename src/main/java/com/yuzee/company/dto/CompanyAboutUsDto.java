package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyAboutUsDto {

	@NotEmpty(message = "description can not be null / empty")
	@JsonProperty("description")
	private String description;
	
	@NotEmpty(message = "industry can not be null / empty")
	@JsonProperty("industry")
	private String industry;
	
	@NotEmpty(message = "industry_type can not be null / empty")
	@JsonProperty("industry_type")
	private String industryType;
	
	@NotNull(message = "year_founded can not be null / empty")
	@JsonProperty("year_founded")
	private Integer yearFounded;
	
	@JsonProperty("speciality")
	@Valid
	private List<CompanySpecialityDto> speciality = new ArrayList<>();
	
	@JsonProperty("vidoes")
	private List<StorageDto> videos = new ArrayList<>();
	
}
