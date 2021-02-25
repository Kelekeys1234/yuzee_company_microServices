package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyScholarshipDto {

	@JsonProperty("company_scholarship_id")
	private String companyScholarshipId;
	
	@NotBlank(message = "privacy_level should not be null/blank")
	@JsonProperty("privacy_level")
	private String privacyLevel;
	
	@JsonProperty("title")
	@NotBlank(message = "title should not be null / blank")
	private String title;
	
	@JsonProperty("description")
	@NotBlank(message = "description should not be null / blank")
	private String description;
	
	@JsonProperty("validity")
	@NotBlank(message = "validity should not be null / blank")
	private String validity;
	
	@JsonProperty("mode_of_covergae")
	@NotBlank(message = "mode_of_covergae should not be null / blank")
	private String modeOfCovergae;
	
	@JsonProperty("percentage")
	private Double percentage;
	
	@JsonProperty("amount")
	private Double amount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("eligible_country")
	private Set<String> eligibleCountry = new HashSet<>();
	
	@JsonProperty("eligible_level")
	private Set<String> eligibleLevel = new HashSet<>();
	
	@JsonProperty( "application_deadline")
	@NotNull(message = "application_deadline should not be null")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date applicationDeadLine;
	
	@JsonProperty("eligible_entity")
	@Valid
	private List<ScholarshipEligibleEntity> listOfScholarshipEligibleEntity = new ArrayList<>();
	
}
