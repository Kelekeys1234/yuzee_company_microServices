package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyPreferredCourseDetailsDto {
	
	@Valid
	@JsonProperty("preferred_courses")
	private List<CompanyPreferredCourseDto> listOfCompanyPreferredCourseDto = new ArrayList<>();
}
