package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyPartnerDetailsResponseDto {
	
	@NotBlank(message = "privacy_level is required")
	@JsonProperty("privacy_level")
	private String privacyLevel;

	@JsonProperty("company_partners")
	private List<CompanyPartnerResponseDto> listOfCompanyPartnerResponseDto = new ArrayList<>();
}
