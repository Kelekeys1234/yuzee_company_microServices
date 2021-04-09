package com.yuzee.company.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CompanyDto {
	
	@JsonProperty(access = Access.READ_ONLY,value ="company_id")
	String id;
	
	@NotEmpty(message = "company_type can not be null / empty")
	@JsonProperty("company_type")
	private String companyType;
	
	@NotEmpty(message = "company_name can not be null / empty")
	@JsonProperty("company_name")
	private String companyName;
	
	@NotEmpty(message = "tag_line can not be null / empty")
	@JsonProperty("tag_line")
	private String tagLine;
	
	@NotEmpty(message = "description can not be null / empty")
	@JsonProperty("description")
	private String description;
	
	@NotEmpty(message = "public_url can not be null / empty")
	@JsonProperty("public_url")
	private String publicUrl;
	
	@NotEmpty(message = "industry can not be null / empty")
	@JsonProperty("industry")
	private String industry;
	
	@NotEmpty(message = "industry_type can not be null / empty")
	@JsonProperty("industry_type")
	private String industryType;
	
	@NotNull(message = "year_founded can not be null / empty")
	@JsonProperty("year_founded")
	private Integer yearFounded;
	
	@JsonProperty("speciality")
	@Valid
	@NotNull(message = "speciality can not be null / empty")
	private List<CompanySpecialityDto> specialitys = new ArrayList<>();
	
	@JsonProperty("locations")
	@Valid
	@NotNull(message = "locations can not be null / empty")
	private List<CompanyLocationDto> locations = new ArrayList<>();
	
	@JsonProperty("profile_pic_url")
	private String profilePicUrl;
	
	@JsonProperty("cover_photo_url")
	private String coverPhotoUrl;
}