package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyScholarshipEntranceRequirementDto;
import com.yuzee.company.endpoint.CompanyScholarshipEntranceRequirementInterface;
import com.yuzee.company.enumeration.Gender;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyScholarshipEntranceRequirementProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyScholarshipEntranceRequirementController implements CompanyScholarshipEntranceRequirementInterface {
	
	@Autowired
	private CompanyScholarshipEntranceRequirementProcessor companyScholarshipEntranceRequirementProcessor;

	@Override
	public Response addUpdateCompanyScholarshipEntranceRequirement(String userId, String companyId,
			String scholarshipId,  CompanyScholarshipEntranceRequirementDto companyScholarshipEntranceRequirementDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyScholarshipEntranceRequirementController.addUpdateCompanyScholarshipEntranceRequirement () method");
		if (!EnumUtils.isValidEnum(Gender.class, companyScholarshipEntranceRequirementDto.getGender())) {
			log.error("Gender value should be {}"
					+ (CommonUtills.getEnumNames(Gender.class)));
			throw new BadRequestException("Invalid value of gender valid values are"
					+ (CommonUtills.getEnumNames(Gender.class)));
		}
		
		
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipEntranceRequirementProcessor.addUpdateCompanyScholarshipEntranceRequirement(userId, companyId, scholarshipId, companyScholarshipEntranceRequirementDto)).setMessage("Company scholarship entrance requirement added successfully")
				.create();
	}

	@Override
	public Response getCompanyScholarshipEntranceRequirement(String userId, String companyId, String scholarshipId)
			throws NotFoundException {
		log.info("Inside CompanyScholarshipEntranceRequirementController.getCompanyScholarshipEntranceRequirement () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipEntranceRequirementProcessor.getCompanyScholarshipEntranceRequirement(userId, companyId, scholarshipId)
				).setMessage("Company scholarship entrance requirement fetched successfully")
				.create();
	}

}
