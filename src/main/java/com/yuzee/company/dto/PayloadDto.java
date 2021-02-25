package com.yuzee.company.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayloadDto {
	private String title;
	private String content;
	private String platform;
	private List<String> deviceId;
}
