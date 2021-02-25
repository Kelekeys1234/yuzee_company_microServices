package com.yuzee.company.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum WorkWithUs {

	LIFE("LIFE","Life@"),
	INNOVATIVE_WORK("INNOVATIVE_WORK","Innovative work"),
	STUDENTS("STUDENTS","Students"), 
	INTERNSHIP("INTERNSHIP","Internship");
	
	@Getter
	private String name;
	
	@Getter
	private String value;
}
