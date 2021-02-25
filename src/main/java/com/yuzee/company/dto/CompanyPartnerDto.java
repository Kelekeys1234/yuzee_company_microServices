package com.yuzee.company.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyPartnerDto {

	@JsonProperty("institute_partner_id")
	private Set<String> listOfInstitutePartnerId = new HashSet<>();
	
	@JsonProperty("company_partner_id")
	private Set<String> listOfCompanyPartnerId = new HashSet<>();
}
