package com.yuzee.company.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmployedUserDto {
	
	@JsonProperty("employed_user")
	private Set<String> employedUser = new HashSet<String>();

}
