package com.yuzee.company.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyContactDetailsDto {
	
	@JsonProperty("contact_detail_id")
	private String contactDetailId;

	@NotBlank(message = "key should not be null / blank")
	@JsonProperty("key")
	private String key;
	
	@NotBlank(message = "value should not be null / blank")
	@JsonProperty("value")
	private String value;
}
