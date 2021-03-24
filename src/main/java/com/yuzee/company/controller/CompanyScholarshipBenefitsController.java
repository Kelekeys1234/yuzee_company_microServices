package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyScholarshipGenericDto;
import com.yuzee.company.endpoint.CompanyScholarshipBenefitsInterface;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyScholarshipBenefitsProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyScholarshipBenefitsController implements CompanyScholarshipBenefitsInterface {

	@Autowired
	private CompanyScholarshipBenefitsProcessor companyScholarshipBenefitsProcessor;

	@Override
	public Response addUpdateScholarshipBenefits(String userId, String companyId, String scholarshipId,
			CompanyScholarshipGenericDto companyScholarshipGenericDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyScholarshipBenefitsController.addUpdateScholarshipBenefits () method");
		companyScholarshipBenefitsProcessor.addUpdateCompanyScholarshipBenefits(userId, companyId, scholarshipId, companyScholarshipGenericDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company scholarship benefit added successfully")
				.create();
	}

	@Override
	public Response getScholarshipBenefits(String userId, String companyId, String scholarshipId)
			throws NotFoundException {
		log.info("Inside CompanyScholarshipBenefitsController.getScholarshipBenefits () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipBenefitsProcessor.getCompanyScholarshipBenefits(userId, companyId, scholarshipId)).setMessage("Company scholarship benefit fetched successfully")
				.create();
	}
}
