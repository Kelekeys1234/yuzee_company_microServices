package com.yuzee.company.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CompanyDto {
	
	@JsonProperty(access = Access.READ_ONLY,value ="company_id")
	String id;
	
	@NotEmpty(message = "company_name can not be null / empty")
	@JsonProperty("company_name")
	private String companyName;
	
	@NotEmpty(message = "description can not be null / empty")
	@JsonProperty("description")
	private String description;
	
	@NotEmpty(message = "tag_line can not be null / empty")
	@JsonProperty("tag_line")
	private String tagLine;
	
	@JsonProperty(access = Access.READ_ONLY, value = "profile_pic_url")
	private String profilePicUrl;
	
	@JsonProperty(access = Access.READ_ONLY, value ="cover_photo_url")
	private String coverPhotoUrl;

}
