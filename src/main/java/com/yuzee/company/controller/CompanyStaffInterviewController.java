package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyStaffInterviewDto;
import com.yuzee.company.endpoint.CompanyStaffInterviewInterface;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyStaffInterviewProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyStaffInterviewController implements CompanyStaffInterviewInterface {

	@Autowired
	private CompanyStaffInterviewProcessor companyStaffInterviewProcessor;

	@Override
	public Response addCompanyStaffInterview(String userId, String companyId,
			CompanyStaffInterviewDto companyStaffInterviewDto) throws NotFoundException, UnauthorizeException {
		log.info("Inside CompanyStaffInterviewController.addCompanyStaffInterview() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyStaffInterviewProcessor.addCompanyStaffInterview(userId, companyId, companyStaffInterviewDto)).setMessage("Company staff interview added successfully")
				.create();
	}

	@Override
	public Response updateCompanyStaffInterview(String userId, String companyId, String staffInterviewId,
			CompanyStaffInterviewDto companyStaffInterviewDto) throws NotFoundException, UnauthorizeException {
		log.info("Inside CompanyStaffInterviewController.updateCompanyStaffInterview() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyStaffInterviewProcessor.updateCompanyStaffInterview(userId, companyId, staffInterviewId, companyStaffInterviewDto)).setMessage("Company staff interview updated successfully")
				.create();
	}

	@Override
	public Response getAllCompanyStaffInterview(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyStaffInterviewController.getAllCompanyStaffInterview() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyStaffInterviewProcessor.getAllCompanyStaffInterview(userId, companyId)).setMessage("Company staff interview fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyStaffInterview(String userId, String companyId, String staffInterviewId)
			throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyStaffInterviewController.deleteCompanyStaffInterview() method");
		companyStaffInterviewProcessor.deleteCompanyStaffinterview(userId, companyId, staffInterviewId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company staff interview deleted successfully")
				.create();
	}

	@Override
	public Response getCompanyStaffInterview(String userId, String companyId, String staffInterviewId)
			throws NotFoundException, ServiceInvokeException {
		log.info("Inside CompanyStaffInterviewController.getCompanyStaffInterview() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyStaffInterviewProcessor.getCompanyStaffInterview(userId, companyId, staffInterviewId)).setMessage("Company staff interview fetched successfully")
				.create();
	}
}
