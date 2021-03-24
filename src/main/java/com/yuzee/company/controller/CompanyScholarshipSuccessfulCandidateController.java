package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyScholarshipGenericDto;
import com.yuzee.company.endpoint.CompanyScholarshipSuccessfulCandidateInterface;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyScholarshipSuccessfulCandidateProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyScholarshipSuccessfulCandidateController implements CompanyScholarshipSuccessfulCandidateInterface {

	@Autowired
	private CompanyScholarshipSuccessfulCandidateProcessor companyScholarshipSuccessfulCandidateProcessor;

	@Override
	public Response addUpdateCompanyScholarshipSuccessfulCandidate(String userId, String companyId,
			String scholarshipId, CompanyScholarshipGenericDto companyScholarshipGenericDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyScholarshipSuccessfulCandidateController.addUpdateCompanyScholarshipSuccessfulCandidate () method");
		companyScholarshipSuccessfulCandidateProcessor.addUpdateCompanyScholarshipSuccessfulCandidate(userId, companyId, scholarshipId, companyScholarshipGenericDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company scholarship successful candidate added successfully")
				.create();
	}

	@Override
	public Response getCompanyScholarshipSuccessfulCandidate(String userId, String companyId, String scholarshipId)
			throws NotFoundException {
		log.info("Inside CompanyScholarshipSuccessfulCandidateController.getCompanyScholarshipSuccessfulCandidate () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipSuccessfulCandidateProcessor.getCompanyScholarshipSuccessfulCandidate(userId, companyId, scholarshipId)).setMessage("Company scholarship successful candidate fetched successfully")
				.create();
	}
}
