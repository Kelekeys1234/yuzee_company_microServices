package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAwardAndCertificationDto {

	@JsonProperty("award_certification_id")
	private String awardCertificationId;
	
	@NotBlank(message = "privacy_level should not be null/blank")
	@JsonProperty("privacy_level")
	private String privacyLevel;
	
	@JsonProperty("title")
	@NotBlank(message = "title can not be null/empty")
	private String title;
	
	@JsonProperty("description")
	@NotBlank(message = "description can not be null/empty")
	private String description;
	
	@JsonProperty("storage")
	private List<StorageDto> listOfStorageDto = new ArrayList<>();

	public CompanyAwardAndCertificationDto(String awardCertificationId,
			@NotBlank(message = "title can not be null/empty") String title,
			@NotBlank(message = "description can not be null/empty") String description, String privacyLevel) {
		super();
		this.awardCertificationId = awardCertificationId;
		this.title = title;
		this.description = description;
		this.privacyLevel = privacyLevel;
	}
}