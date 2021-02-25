package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyStaffInterviewDto {

	@JsonProperty("company_staff_interview_id")
	private String companyStaffInterviewId;
	
	@NotBlank(message = "title should not be null / blank")
	@JsonProperty("title")
	private String title;
	
	@NotBlank(message = "discription should not be null / blank")
	@JsonProperty("discription")
	private String discription;
	
	@JsonProperty("taged_interviewee")
	private Set<String> tagedInterviewee = new HashSet<>();
	
	@JsonProperty("storage")
	private List<StorageDto> listOfStorageDto = new ArrayList<>();
}
