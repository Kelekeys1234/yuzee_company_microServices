package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInternshipDto {
	
	@JsonProperty("company_internship_id")
	private String companyInternshipId;
	
	@JsonProperty("title")
	@NotBlank(message = "title should not be null/blank")
	private String title;
	
	@JsonProperty("description")
	@NotBlank(message = "description should not be null/blank")
	private String description;
	
	@JsonProperty("ìnternship_members")
	private Set<String> ìnternshipMembers = new HashSet<>();
	
	@JsonProperty("created_date")
	private Date createdDate;
	
	@JsonProperty("storage")
	private List<StorageDto> listOfStorageDto = new ArrayList<>();

	public CompanyInternshipDto(String companyInternshipId,
			@NotBlank(message = "title should not be null/blank") String title,
			@NotBlank(message = "description should not be null/blank") String description,
			Set<String> ìnternshipMembers,Date createdDate) {
		super();
		this.companyInternshipId = companyInternshipId;
		this.title = title;
		this.description = description;
		this.ìnternshipMembers = ìnternshipMembers;
		this.createdDate = createdDate;
	}
	
	

}
