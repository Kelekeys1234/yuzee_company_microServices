package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyAboutUsDto;
import com.yuzee.company.endpoint.CompanyAboutUsInterface;
import com.yuzee.company.enumeration.IndustryType;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyAboutUsProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyAboutUsController implements CompanyAboutUsInterface {
	
	@Autowired
	private CompanyAboutUsProcessor companyAboutUsProcessor;

	@Override
	public Response addUpdateCompanyAboutUs(String userId, String companyId, CompanyAboutUsDto companyAboutUsDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyAboutUsController.addUpdateCompanyAboutUs () method");
		if (!EnumUtils.isValidEnum(IndustryType.class, companyAboutUsDto.getIndustryType())) {
			log.error("Value of Industry type should be {}"
					+ (CommonUtills.getEnumNames(IndustryType.class)));
			throw new BadRequestException("Invalid value of industry type"
					+ (CommonUtills.getEnumNames(IndustryType.class)));
		}
		
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAboutUsProcessor.addUpdateCompanyAboutUsInfo(userId, companyId, companyAboutUsDto)).setMessage("Company about us info added / updated successfully")
				.create();
	}

	@Override
	public Response getCompanyAboutUs(String userId, String companyId)
			throws NotFoundException, ServiceInvokeException {
		log.info("Inside CompanyAboutUsController.getCompanyAboutUs () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyAboutUsProcessor.getCompanyAboutUsInfo(userId, companyId)).setMessage("Company about us info fetched successfully")
				.create();
	}

}
