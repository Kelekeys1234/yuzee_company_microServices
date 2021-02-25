package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyLocationDto;
import com.yuzee.company.endpoint.CompanyLocationInterface;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyLocationProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyLocationController implements CompanyLocationInterface {
	
	@Autowired
	private CompanyLocationProcessor companyLocationProcessor;

	@Override
	public Response addCompanyLocation(String userId, String companyId, CompanyLocationDto companyLocationDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyLocationController.addCompanyLocation () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyLocationDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyLocationProcessor.addCompanyLocation(userId, companyId, companyLocationDto)).setMessage("Company location added successfully")
				.create();
	}

	@Override
	public Response updateCompanyLocation(String userId, String companyId, String locationId,
		 CompanyLocationDto companyLocationDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyLocationController.updateCompanyLocation () method");
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyLocationDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyLocationProcessor.updateCompanyLocation(userId, companyId, locationId, companyLocationDto)).setMessage("Company location updated successfully")
				.create();
	}

	@Override
	public Response getAllCompanyLocation(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyLocationController.getAllCompanyLocation () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyLocationProcessor.getAllCompanyLocation(userId, companyId)
				).setMessage("Company location fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyLocation(String userId, String companyId, String locationId)
			throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyLocationController.deleteCompanyLocation () method");
		companyLocationProcessor.deleteCompanyLocation(userId, companyId, locationId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK)
				.setMessage("Company location deleted successfully")
				.create();
	}

}
