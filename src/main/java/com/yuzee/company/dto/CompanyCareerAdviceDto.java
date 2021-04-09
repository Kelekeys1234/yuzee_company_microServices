package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyCareerAdviceDto {
	
	@JsonProperty("company_career_advice_id")
	private String companyCareerAdviceId;

	@JsonProperty("title")
	@NotBlank(message = "title should not be null / blank")
	private String title;
	
	@JsonProperty("description")
	@NotBlank(message = "description should not be null / blank")
	private String description;
	
	@JsonProperty("created_date")
	private Date createdDate;
	
	@JsonProperty("collaborations")
	private Set<String> collaborations = new HashSet<String>();
	
	@JsonProperty("user_in_employment")
	private Set<String> userInEmployment = new HashSet<String>();
	
	@JsonProperty("storage")
	private List<StorageDto> listOfStorageDto = new ArrayList<>();
	
}
