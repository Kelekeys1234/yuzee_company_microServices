package com.yuzee.company.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyPartnerDetailDto {
	
	@NotBlank(message = "privacy_level is required")
	@JsonProperty("privacy_level")
	private String privacyLevel;
	
	@JsonProperty("institute_partner_id")
	private Set<String> listOfInstitutePartnerId = new HashSet<>();
	
	@JsonProperty("company_partner_id")
	private Set<String> listOfCompanyPartnerId = new HashSet<>();
}
