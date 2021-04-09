package com.yuzee.company.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InternshipSkillsDto {
	
	@JsonProperty("skills")
	Set<String> skills = new HashSet<>();

}
