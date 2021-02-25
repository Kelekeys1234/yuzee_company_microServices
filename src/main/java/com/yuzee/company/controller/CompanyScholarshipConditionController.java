package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyScholarshipGenericDto;
import com.yuzee.company.endpoint.CompanyScholarshipConditionInterface;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyScholarshipConditionProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyScholarshipConditionController implements CompanyScholarshipConditionInterface {
	
	@Autowired
	private CompanyScholarshipConditionProcessor companyScholarshipConditionProcessor;

	@Override
	public Response addUpdateCompanyScholarshipCondition(String userId, String companyId, String scholarshipId,
			 CompanyScholarshipGenericDto companyScholarshipGenericDto) throws NotFoundException, UnauthorizeException {
		log.info("Inside CompanyScholarshipConditionController.addUpdateCompanyScholarshipCondition () method");
		companyScholarshipConditionProcessor.addUpdateCompanyScholarshipCondition(userId, companyId, scholarshipId, companyScholarshipGenericDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company scholarship condition added successfully")
				.create();
	}

	@Override
	public Response getCompanyScholarshipCondition(String userId, String companyId, String scholarshipId)
			throws NotFoundException {
		log.info("Inside CompanyScholarshipBenefitsController.getCompanyScholarshipCondition () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipConditionProcessor.getCompanyScholarshipCondition(userId, companyId, scholarshipId)).setMessage("Company scholarship benefit fetched successfully")
				.create();
	}

}
