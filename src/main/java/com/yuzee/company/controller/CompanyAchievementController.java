package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyAchievementDto;
import com.yuzee.company.endpoint.CompanyAchievementInterface;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyAchievementProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyAchievementController implements CompanyAchievementInterface {
	
	@Autowired
	private CompanyAchievementProcessor companyAchievementProcessor;

	@Override
	public Response addCompanyAchievement(String userId, String companyId, CompanyAchievementDto companyAchievementDto)
			throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyAchievementController.addCompanyAchievement () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyAchievementDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level valid values are"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAchievementProcessor.addCompanyAchievement(userId, companyId, companyAchievementDto)).setMessage("Company Achievement added successfully")
				.create();
	}

	@Override
	public Response updateCompanyAchievement(String userId, String companyId, String achievementId,
			CompanyAchievementDto companyAchievementDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyAchievementController.updateCompanyAchievement () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyAchievementDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level valid values are"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAchievementProcessor.updateCompanyAchievement(userId, companyId, achievementId, companyAchievementDto)).setMessage("Company Achievement updated successfully")
				.create();
	}

	@Override
	public Response getAllCompanyAchievement(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyAchievementController.getAllCompanyAchievement () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAchievementProcessor.getAllCompanyAchievements(userId, companyId)).setMessage("Company Achievements fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyAchievement(String userId, String companyId, String achievementId)
			throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyAchievementController.deleteCompanyAchievement () method");
		companyAchievementProcessor.deleteCompanyAchievement(userId, companyId, achievementId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company Achievement deleted successfully")
				.create();
	}

	@Override
	public Response getCompanyAchievement(String userId, String companyId, String achievementId)
			throws NotFoundException, ServiceInvokeException {
		log.info("Inside CompanyAchievementController.getCompanyAchievement () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAchievementProcessor.getCompanyAchievementById(userId, companyId, achievementId)).setMessage("Company Achievement fetched successfully")
				.create();
	}

}
