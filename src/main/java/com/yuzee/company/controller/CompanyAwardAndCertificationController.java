package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyAwardAndCertificationDto;
import com.yuzee.company.endpoint.CompanyAwardAndCertificationInterface;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyAwardAndCertificationProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyAwardAndCertificationController implements CompanyAwardAndCertificationInterface {
	
	@Autowired
	private CompanyAwardAndCertificationProcessor companyAwardAndCertificationProcessor;
	
	@Override
	public Response addCompanyAwardAndCertification(String userId, String companyId,
			CompanyAwardAndCertificationDto companyAwardAndCertificationDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyAwardAndCertificationController.addCompanyAwardAndCertification () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyAwardAndCertificationDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level valid values are"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAwardAndCertificationProcessor.addCompanyAwardAndCertification(userId, companyId, companyAwardAndCertificationDto)).setMessage("Company award and certification added successfully")
				.create();
	}

	@Override
	public Response updateCompanyAwardAndCertification(String userId, String companyId, String awardCertificationId,
			CompanyAwardAndCertificationDto companyAwardAndCertificationDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyAwardAndCertificationController.updateCompanyAwardAndCertification () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyAwardAndCertificationDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level valid values are"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAwardAndCertificationProcessor.updateCompanyAwardAndCertification(userId, companyId,awardCertificationId , companyAwardAndCertificationDto)).setMessage("Company award and certification updated successfully")
				.create();
	}

	@Override
	public Response getAllAwardAndCertification(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyAwardAndCertificationController.getAllAwardAndCertification () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAwardAndCertificationProcessor.getAllCompanyAwardAndCertification(userId, companyId)).setMessage("Company award and certification fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyAwardAndCertification(String userId, String companyId, String awardCertificationId) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyAwardAndCertificationController.deleteCompanyAwardAndCertification () method");
		companyAwardAndCertificationProcessor.deleteCompanyAwardAndCertification(userId, companyId, awardCertificationId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company award and certification deleted successfully")
				.create();
	}

}
