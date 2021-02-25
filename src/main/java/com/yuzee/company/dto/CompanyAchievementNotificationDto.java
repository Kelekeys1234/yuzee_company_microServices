package com.yuzee.company.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAchievementNotificationDto {
	
	@JsonProperty("company_id")
	private String companyId;
	
	@JsonProperty("company_name")
	private String companyName;
		
	@JsonProperty("company_achievement_id")
	private String companyAchievementId;

	@JsonProperty("achievement_name")
	private String achievementName;
	
	@JsonProperty("achievement_description")
	private String achievementDescription;
	
	@JsonProperty("achievement_start_date")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private Date achievementStartDate;

}
