package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.AchievementTagedUserDao;
import com.yuzee.company.dto.CompanyAchievementNotificationDto;
import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;
import com.yuzee.company.model.AchievementTagedUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationListingProcessor {
	
	@Autowired
	private AchievementTagedUserDao achievementTagedUserDao;
	
	public List<CompanyAchievementNotificationDto> getCompanyAchievementRequest(String userId , AchievementTagedUserRequestStatus achievementTagedUserRequestStatus
			,Integer pageNumber , Integer pageSize) {
		List<CompanyAchievementNotificationDto> listOfCompanyAchievementNotificationDto = new ArrayList<>();
		log.debug("Inside NotificationListingProcessor.getCompanyAchievementRequest() method");
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		log.info("Getting all achievement request for user {} status {}",userId,achievementTagedUserRequestStatus);
		List<AchievementTagedUser> listOfAchievementTagedUser = achievementTagedUserDao.getAchivementTagedUserBasedOnStatus(userId, achievementTagedUserRequestStatus, pageable);
		if (!CollectionUtils.isEmpty(listOfAchievementTagedUser)) {
			log.info("listOfAchievementTagedUser not empty creating respose");
			listOfCompanyAchievementNotificationDto = listOfAchievementTagedUser.stream().map(achievementTagedUser -> new  CompanyAchievementNotificationDto
					(achievementTagedUser.getCompanyAchievement().getCompany().getId(), achievementTagedUser.getCompanyAchievement().getCompany().getCompanyName(),  achievementTagedUser.getCompanyAchievement().getId(), achievementTagedUser.getCompanyAchievement().getTitle(), achievementTagedUser.getCompanyAchievement().getDescription(), achievementTagedUser.getCompanyAchievement().getAchievedDate())).collect(Collectors.toList());
		}
		return listOfCompanyAchievementNotificationDto;
	}
	
	public void changeCompanyAchievementRequestStatus (String userId, String achievementId , AchievementTagedUserRequestStatus achievementTagedUserRequestStatus ) {
		log.debug("Inside NotificationListingProcessor.changeCompanyAchievementRequestStatus() method");
		log.info("Getting achievement taged user for user id {} and id {}",userId, achievementId);
		AchievementTagedUser achievementTagedUser = achievementTagedUserDao.getAchievementTagedUserByUserIdAndAchivementTagedId(userId, achievementId);
		if (!ObjectUtils.isEmpty(achievementTagedUser)) {
			log.info("Changing status of achievement taged user from {} to {}",achievementTagedUser.getAchievementTagedUserRequestStatus(),achievementTagedUserRequestStatus);
			achievementTagedUser.setAchievementTagedUserRequestStatus(achievementTagedUserRequestStatus);
			achievementTagedUser.setUpdatedOn(new Date());
			achievementTagedUser.setUpdatedBy("API");
		}
		log.info("Adding achievement taged user into DB");
		achievementTagedUserDao.addAchievementTagedUser(achievementTagedUser);
	}
}
