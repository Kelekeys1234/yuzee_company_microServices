package com.yuzee.company.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPreferredCourseDto {
	
	@JsonProperty("preferred_course_id")
	@NotEmpty(message = "preferred_course_id should not be null / empty")
	private String preferredCourseId;
	
	@NotEmpty(message = "preferred_course_name should not be null / empty")
	@JsonProperty("preferred_course_name")
	private String preferredCourseName;

}
