package com.yuzee.company.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyCultureMissionDto {
	
	@NotBlank(message = "privacy_level should not be null/blank")
	@JsonProperty("privacy_level")
	private String privacyLevel;
	
	@NotBlank(message = "mission should not be null/blank")
	@JsonProperty("mission")
	private String mission;

}
