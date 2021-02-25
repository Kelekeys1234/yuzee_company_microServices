package com.yuzee.company.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuzee.company.constant.Constants;
import com.yuzee.company.dto.NotificationDto;
import com.yuzee.company.exception.ServiceInvokeException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationHandler {
		
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String PUSH_NOTIFICATION = "/api/v1/push";
	
	public void sendPushNotification(NotificationDto notificationDto) throws Exception {
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constants.NOTIFICATION_BASE_PATH).append(PUSH_NOTIFICATION);
			ResponseEntity<String> status = restTemplate.postForEntity(path.toString(), notificationDto, String.class);
			if(status.getStatusCodeValue() != 200) {
				throw new ServiceInvokeException("Error response recieved from notification service with error code " + status.getStatusCodeValue());
			}
		} catch (Exception e) {
			log.error("Error invoking notification service {}", e);
			if (e instanceof ServiceInvokeException) {
				throw e;
			} else {
				throw new ServiceInvokeException("Error invoking notification service");
			}	
		}
	}

}
