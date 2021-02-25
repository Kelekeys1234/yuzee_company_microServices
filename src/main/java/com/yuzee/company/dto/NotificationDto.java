package com.yuzee.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDto {
	
	private PayloadDto payload;
	private String channel;
	private String user;
	private String userId;
	private String tenantCode;
	private String notificationType;
	private String action;
}
