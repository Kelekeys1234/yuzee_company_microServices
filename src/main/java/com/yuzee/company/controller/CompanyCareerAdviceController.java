package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyCareerAdviceDto;
import com.yuzee.company.endpoint.CompanyCareerAdviceInterface;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyCareerAdviceProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyCareerAdviceController implements CompanyCareerAdviceInterface{
	
	@Autowired
	private CompanyCareerAdviceProcessor companyCareerAdviceProcessor;

	@Override
	public Response addCareerAdvice(String userId, String companyId, CompanyCareerAdviceDto companyCareerAdviceDto)
			throws NotFoundException, UnauthorizeException {
		log.info("Inside CompanyCareerAdviceController.addCareerAdvice () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyCareerAdviceProcessor.addCompanyCareerAdvice(userId, companyId, companyCareerAdviceDto)).setMessage("Company career advice added successfully")
				.create();
	}

	@Override
	public Response updateCareerAdvice(String userId, String companyId, String careerAdviceId,
			CompanyCareerAdviceDto companyCareerAdviceDto) throws NotFoundException, UnauthorizeException {
		log.info("Inside CompanyCareerAdviceController.updateCareerAdvice () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyCareerAdviceProcessor.updateCompanyCareerAdvice(userId, companyId, careerAdviceId, companyCareerAdviceDto)).setMessage("Company career advice updated successfully")
				.create();
	}

	@Override
	public Response getAllCareerAdvice(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyCareerAdviceController.getAllCareerAdvice () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyCareerAdviceProcessor.getAllCompanyCareerAdvice(userId, companyId)).setMessage("Company career advice fetched successfully")
				.create();
	}

	@Override
	public Response deleteCareerAdvice(String userId, String companyId, String careerAdviceId)
			throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyCareerAdviceController.deleteCareerAdvice () method");
		companyCareerAdviceProcessor.deleteCompanyCareerAdvice(userId, companyId, careerAdviceId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company career advice deleted successfully")
				.create();
	}
}
