package com.yuzee.company.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyCultureVisionDto {
	
	@NotBlank(message = "privacy_level should not be null/blank")
	@JsonProperty("privacy_level")
	private String privacyLevel;
	
	@NotBlank(message = "vision should not be null/blank")
	@JsonProperty("vision")
	private String vision;

}
