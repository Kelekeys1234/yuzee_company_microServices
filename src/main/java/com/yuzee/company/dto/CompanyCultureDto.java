package com.yuzee.company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyCultureDto {

	@JsonProperty("company_culture_id")
	private String companyCultureId;
	
	@JsonProperty("company_culture_vision")
	private CompanyCultureVisionDto companyCultureVisionDto;

	@JsonProperty("company_culture_mission")
	private CompanyCultureMissionDto companyCultureMissionDto;
}
