package com.yuzee.company.controller;

import java.util.Arrays;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyWorkWithUsDto;
import com.yuzee.company.endpoint.CompanyWorkWithUsInterface;
import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyWorkWithUsProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyWorkWithUsController implements CompanyWorkWithUsInterface {

	@Autowired
	private CompanyWorkWithUsProcessor companyWorkWithUsProcessor;

	@Override
	public Response addUpdateCompanyWorkWithUs(String userId, String companyId,
			CompanyWorkWithUsDto companyWorkWithUsDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyWorkWithUsController.addUpdateCompanyWorkWithUs() method");
		if (!EnumUtils.isValidEnum(WorkWithUs.class, companyWorkWithUsDto.getTitleName())) {
			log.error("Value of Title name should be {}"
					+ (CommonUtills.getEnumNames(WorkWithUs.class)));
			throw new BadRequestException("Invalid value of Title name"
					+ (CommonUtills.getEnumNames(WorkWithUs.class)));
		}
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyWorkWithUsProcessor.addUpdateCompanyWorkWithUs(userId, companyId, companyWorkWithUsDto)).setMessage("Work with us added/updated successfully")
				.create();
	}

	@Override
	public Response getAllCompanyWorkWithUs(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyWorkWithUsController.getAllCompanyWorkWithUs() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyWorkWithUsProcessor.getAllWorkWithUsInfo(userId, companyId)).setMessage("Work with us fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyWorkWithUs(String userId, String companyId, String companyWorkWithUsId) throws NotFoundException, UnauthorizeException {
		log.info("Inside CompanyWorkWithUsController.deleteCompanyWorkWithUs() method");
		companyWorkWithUsProcessor.deleteCompanyWorkWithUsInfo(userId, companyId, companyWorkWithUsId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Work with us deleted successfully")
				.create();
	}
}