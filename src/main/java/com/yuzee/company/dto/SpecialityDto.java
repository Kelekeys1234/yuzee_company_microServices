package com.yuzee.company.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityDto {

	@JsonProperty(value = "speciality_id")
	private String specialityId;
	
	@JsonProperty("speciality_name")
	@NotBlank(message = "speciality_name should be not null/empty")
	private String specialityName;
}
