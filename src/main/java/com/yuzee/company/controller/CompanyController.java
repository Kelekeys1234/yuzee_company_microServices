package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyDto;
import com.yuzee.company.endpoint.CompanyInterface;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyController implements CompanyInterface {
	
	@Autowired
	private CompanyProcessor companyProcessor;

	@Override
	public Response addCompanyInitialInfo(String userId, CompanyDto companyDto) throws BadRequestException {
		log.info("Inside CompanyController.addCompanyInitialInfo () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyProcessor.addCompanyInitialInfo(userId, companyDto)).setMessage("Company initial info added successfully")
				.create();
	}

	@Override
	public Response updateCompanyInitialInfo(String userId, String companyId, CompanyDto companyDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyController.updateCompanyInitialInfo () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyProcessor.updateCompanyInitialInfo(userId, companyId, companyDto)).setMessage("Company initial info updated successfully")
				.create();
	}

	@Override
	public Response getCompanyInitialInfo(String userId, String companyId) throws NotFoundException, ServiceInvokeException {
		log.info("Inside CompanyController.getCompanyInitialInfo () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyProcessor.getCompanyInitialInfo(userId, companyId)).setMessage("Company initial info fetched successfully")
				.create();
	}
}
