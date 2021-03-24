package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyInternshipDto;
import com.yuzee.company.endpoint.CompanyInternshipInterface;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyInternshipProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyInternshipController implements CompanyInternshipInterface {
	
	@Autowired
	private CompanyInternshipProcessor companyInternshipProcessor;

	@Override
	public Response addCompanyInternship(String userId, String companyId, CompanyInternshipDto companyInternshipDto)
			throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyInternshipController.addCompanyInternship () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyInternshipProcessor.addCompanyInternship(userId, companyId, companyInternshipDto)).setMessage("Company Internship added successfully")
				.create();
	}

	@Override
	public Response updateCompanyInternship(String userId, String companyId, String companyInternshipId,
			CompanyInternshipDto companyInternshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyInternshipController.updateCompanyInternship () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyInternshipProcessor.updateCompanyInternship(userId, companyId, companyInternshipId, companyInternshipDto)).setMessage("Company Internship updated successfully")
				.create();
	}

	@Override
	public Response getAllCompanyInternship(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyInternshipController.getAllCompanyInternship () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyInternshipProcessor.getAllCompanyInternships(userId, companyId)).setMessage("Company Internship fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyInternship(String userId, String companyId, String companyInternshipId)
			throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyInternshipController.deleteCompanyInternship () method");
		companyInternshipProcessor.deleteCompanyInternship(userId, companyId, companyInternshipId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company Internship deleted successfully")
				.create();
	}

	@Override
	public Response getCompanyInternship(String userId, String companyId, String companyInternshipId)
			throws NotFoundException, ServiceInvokeException {
		log.info("Inside CompanyInternshipController.getCompanyInternship () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyInternshipProcessor.getCompanyInternshipById(userId, companyId, companyInternshipId)).setMessage("Company Internship fetched successfully")
				.create();
	}

}
