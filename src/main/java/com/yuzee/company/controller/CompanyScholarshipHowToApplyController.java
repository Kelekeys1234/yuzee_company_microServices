package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyScholarshipGenericDto;
import com.yuzee.company.endpoint.CompanyScholarshipHowToApplyInterface;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyScholarshipHowToApplyProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyScholarshipHowToApplyController implements CompanyScholarshipHowToApplyInterface {

	@Autowired
	private CompanyScholarshipHowToApplyProcessor companyScholarshipHowToApplyProcessor;

	@Override
	public Response addUpdateCompanyScholarshipHowToApply(String userId, String companyId, String scholarshipId,
		 CompanyScholarshipGenericDto companyScholarshipGenericDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyScholarshipHowToApplyController.addUpdateCompanyScholarshipHowToApply () method");
		companyScholarshipHowToApplyProcessor.addUpdateCompanyScholarshipHowToApply(userId, companyId, scholarshipId, companyScholarshipGenericDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company scholarship how to apply added successfully")
				.create();
	}

	@Override
	public Response getCompanyScholarshipHowToApply(String userId, String companyId, String scholarshipId)
			throws NotFoundException {
		log.info("Inside CompanyScholarshipHowToApplyController.getCompanyScholarshipHowToApply () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipHowToApplyProcessor.getCompanyScholarshipHowToApply(userId, companyId, scholarshipId)).setMessage("Company scholarship how to apply fetched successfully")
				.create();
	}
}
