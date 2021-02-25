package com.yuzee.company.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ScholarshipEligibleEntity {

	@NotBlank(message = "entity_type should not be null/blank")
	@JsonProperty("entity_type")
	private String entityType;
	
	@NotBlank(message = "entity_id should not be null/blank")
	@JsonProperty("entity_id")
	private String entityId;
}
