package com.yuzee.company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPartnerResponseDto {

	@JsonProperty("entity_id")
	private String entityId;
	
	@JsonProperty("entity_type")
	private String entityType;
	
	@JsonProperty("entity_name")
	private String entityName;
	
	@JsonProperty("icon")
	private String icon;
}
