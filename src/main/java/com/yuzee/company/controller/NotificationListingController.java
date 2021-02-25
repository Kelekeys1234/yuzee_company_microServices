package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.endpoint.NotificationListingInterface;
import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.processor.NotificationListingProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class NotificationListingController implements NotificationListingInterface {

	@Autowired
	private NotificationListingProcessor notificationListingProcessor;
	
	@Override
	public Response getAllUserCompanyAchievementTagedRequest(String userId, String status, Integer pageNumber,
			Integer pageSize) throws BadRequestException {
		log.info("Inside NotificationListingController.getAllUserCompanyAchievementTagedRequest() method");
		if (pageNumber < 1) {
			log.error("Page number can not be less than 1");
			throw new BadRequestException("Page number can not be less than 1");
		}
		
		if (pageSize < 1) {
			log.error("Page size can not be less than 1");
			throw new BadRequestException("Page size can not be less than 1");
		}
				
		if (!EnumUtils.isValidEnum(AchievementTagedUserRequestStatus.class, status)) {
			log.error("Valid status values {} are value passed in request", CommonUtills.getEnumNames(AchievementTagedUserRequestStatus.class),status);
			throw new BadRequestException("Valid status values are "+ CommonUtills.getEnumNames(AchievementTagedUserRequestStatus.class));
		}
		
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(notificationListingProcessor.getCompanyAchievementRequest(userId, AchievementTagedUserRequestStatus.valueOf(status), pageNumber, pageSize)
				).setMessage("Work with us enum value fetch successfully")
				.create();
	}

	@Override
	public Response changeCompanyAchievementTagedUserStatus(String userId, String achievementId, String status) throws BadRequestException {
		log.info("Inside NotificationListingController.changeCompanyAchievementTagedUserStatus () method");
		if (!EnumUtils.isValidEnum(AchievementTagedUserRequestStatus.class, status)) {
			log.error("Valid status values {} are value passed in request", CommonUtills.getEnumNames(AchievementTagedUserRequestStatus.class),status);
			throw new BadRequestException("Valid status values are "+ CommonUtills.getEnumNames(AchievementTagedUserRequestStatus.class));
		}
		notificationListingProcessor.changeCompanyAchievementRequestStatus(userId, achievementId, AchievementTagedUserRequestStatus.valueOf(status));
		return new GenericResponseHandlers.Builder().setStatus(Status.OK)
				.setMessage("Achievement taged status change successfully")
				.create();
	}

}
