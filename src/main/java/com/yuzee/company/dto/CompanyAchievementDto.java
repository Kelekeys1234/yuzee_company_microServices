package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CompanyAchievementDto {
	
	@JsonProperty("company_achievement_id")
	private String companyAchievementId;
	
	@NotBlank(message = "privacy_level should not be null/blank")
	@JsonProperty("privacy_level")
	private String privacyLevel;

	@JsonProperty("achievement_name")
	@NotBlank(message = "achievement_name can not be null/empty")
	private String achievementName;
	
	@JsonProperty("achievement_description")
	@NotBlank(message = "achievement_description can not be null/empty")
	private String achievementDescription;
	
	@JsonProperty("achievement_start_date")
	@NotNull(message = "achievement_start_date should not be null")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date achievementStartDate;
	
	@JsonProperty("taged_user_id")
	private Set<String> tagedUser = new HashSet<>();
	
	@JsonProperty("storage")
	private List<StorageDto> listOfStorageDto = new ArrayList<>();
	
	
}
