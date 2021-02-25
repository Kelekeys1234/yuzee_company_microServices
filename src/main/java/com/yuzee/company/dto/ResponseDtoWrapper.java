package com.yuzee.company.dto;

import lombok.Data;

@Data
public class ResponseDtoWrapper <T> {
 
	private String message;

	private String status;
	
    private T data; 
}
