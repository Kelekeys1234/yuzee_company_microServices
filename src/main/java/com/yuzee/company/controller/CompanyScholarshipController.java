package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyScholarshipDto;
import com.yuzee.company.endpoint.CompanyScholarshipInterface;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.enumeration.ScholarshipCoverage;
import com.yuzee.company.enumeration.ScholarshipValidity;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyScholarshipProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyScholarshipController implements CompanyScholarshipInterface {

	@Autowired
	private CompanyScholarshipProcessor companyScholarshipProcessor;

	@Override
	public Response addCompanyScholarship(String userId, String companyId,
			 CompanyScholarshipDto companyScholarshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyScholarshipController.addCompanyScholarship () method");
		this.validateScholarshipRequest(companyScholarshipDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipProcessor.addCompanyScholarship(userId, companyId, companyScholarshipDto)).setMessage("Company scholarship added successfully")
				.create();
	}

	@Override
	public Response updateCompanyScholarship(String userId, String companyId, String scholarshipId,
			 CompanyScholarshipDto companyScholarshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		this.validateScholarshipRequest(companyScholarshipDto);
		log.info("Inside CompanyScholarshipController.updateCompanyScholarship () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipProcessor.updateCompanyScholarship(userId, companyId, scholarshipId, companyScholarshipDto)).setMessage("Company scholarship added successfully")
				.create();
	}

	@Override
	public Response getAllCompanyScholarship(String userId, String companyId) throws NotFoundException {
		log.info("Inside CompanyScholarshipController.getAllCompanyScholarship () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyScholarshipProcessor.getAllCompanyScholarship(userId, companyId)).setMessage("Company scholarship fetched successfully")
				.create();
	}

	@Override
	public Response deleteCompanyScholarship(String userId, String companyId, String scholarshipId)
			throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Inside CompanyScholarshipController.deleteCompanyScholarship () method");
		companyScholarshipProcessor.deleteCompanyScholarship(userId, companyId, scholarshipId);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company scholarship deleted successfully")
				.create();

	}
	
	private void validateScholarshipRequest( CompanyScholarshipDto companyScholarshipDto) throws BadRequestException {
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyScholarshipDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		
		if (!EnumUtils.isValidEnum(ScholarshipValidity.class, companyScholarshipDto.getValidity())) {
			log.error("Valid value for validity is {}"
					+ (CommonUtills.getEnumNames(ScholarshipValidity.class)));
			throw new BadRequestException("Valid value for validity is"
					+ (CommonUtills.getEnumNames(ScholarshipValidity.class)));
		}
		
		if (!EnumUtils.isValidEnum(ScholarshipCoverage.class, companyScholarshipDto.getModeOfCovergae())) {
			log.error("Valid value for mode of coverage is {}"
					+ (CommonUtills.getEnumNames(ScholarshipCoverage.class)));
			throw new BadRequestException("Valid value for validity is"
					+ (CommonUtills.getEnumNames(ScholarshipCoverage.class)));
		}
		
		if (companyScholarshipDto.getModeOfCovergae().equals(ScholarshipCoverage.AMOUNT.name()) && (StringUtils.isEmpty(companyScholarshipDto.getCurrency())
				|| null == companyScholarshipDto.getAmount())) {
			log.error("current and amount should not be empty when mode_of_covergae is AMOUNT");
			throw new BadRequestException("current and amount should not be empty when mode_of_covergae is AMOUNT");
		}
		
		if (companyScholarshipDto.getModeOfCovergae().equals(ScholarshipCoverage.PERCENTAGE.name()) && null == companyScholarshipDto.getPercentage()) {
			log.error("percentage should not be empty when mode_of_covergae is PERCENTAGE");
			throw new BadRequestException("percentage should not be empty when mode_of_covergae is PERCENTAGE");
		}
	}
	
}
