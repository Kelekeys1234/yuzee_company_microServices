package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyPreferredCourseDetailsDto;
import com.yuzee.company.endpoint.CompanyPreferredCourseInterface;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyPreferredCourseProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyPreferredCourseController implements CompanyPreferredCourseInterface {
	
	@Autowired
	private CompanyPreferredCourseProcessor companyPreferredCourseProcessor;

	@Override
	public Response addUpdateCompanyPreferredCourse(String userId, String companyId,
			CompanyPreferredCourseDetailsDto companyPreferredCourseDetailsDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyPreferredCourseController.addUpdateCompanyPartner () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyPreferredCourseDetailsDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level valid values are"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		companyPreferredCourseProcessor.addUpdateCompanyPreferredCourse(userId, companyId, companyPreferredCourseDetailsDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company preferred course added successfully")
				.create();
	}

	@Override
	public Response getAllCompanyPreferredCourse(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyPreferredCourseController.getAllCompanyPreferredCourse () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyPreferredCourseProcessor.getAllCompanyPreferredCourse(userId, companyId)
				).setMessage("Company preferred course fetched successfully")
				.create();
	}

}
