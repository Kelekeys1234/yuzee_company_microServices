package com.yuzee.company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InternshipEducationNeedDto {

	@JsonProperty("education_need")
	private String educationNeed;
}
