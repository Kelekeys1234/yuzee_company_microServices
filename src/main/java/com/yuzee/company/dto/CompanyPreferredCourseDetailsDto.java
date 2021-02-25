package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyPreferredCourseDetailsDto {

	@NotBlank(message = "privacy_level is required")
	@JsonProperty("privacy_level")
	private String privacyLevel;
	
	@Valid
	@JsonProperty("preferred_courses")
	private List<CompanyPreferredCourseDto> listOfCompanyPreferredCourseDto = new ArrayList<>();
}
