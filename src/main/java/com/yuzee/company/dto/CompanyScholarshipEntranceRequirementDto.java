package com.yuzee.company.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyScholarshipEntranceRequirementDto {

	@JsonProperty("eligible_nationality")
	private String eligibleNationality;
	
	@JsonProperty("gender")
	@NotBlank(message = "gender should not be null / blank")
	private String gender;
	
	@JsonProperty("general_requirements")
	@NotBlank(message = "general_requirements should not be null / blank")
	private String generalRequirements;
	
	@JsonProperty("languages")
	private Set<String> languages = new HashSet<>();
}
