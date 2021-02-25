package com.yuzee.company.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyPartnerDetailDto;
import com.yuzee.company.endpoint.CompanyPartnerInterface;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyPartnerProcessor;
import com.yuzee.company.utills.CommonUtills;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyPartnerController implements CompanyPartnerInterface {
	
	@Autowired
	private CompanyPartnerProcessor companyPartnerProcessor;

	@Override
	public Response addUpdateCompanyPartner(String userId, String companyId,
			 CompanyPartnerDetailDto companyPartnerDetailDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Inside CompanyPartnerController.addUpdateCompanyPartner () method");
		
		if (!EnumUtils.isValidEnum(PrivacyLevelEnum.class, companyPartnerDetailDto.getPrivacyLevel())) {
			log.error("Privacy level should be {}"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
			throw new BadRequestException("Invalid value of privacy level valid values are"
					+ (CommonUtills.getEnumNames(PrivacyLevelEnum.class)));
		}
		companyPartnerProcessor.addUpdateCompanyPartner(userId, companyId, companyPartnerDetailDto);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Company partner added successfully")
				.create();
	}

	@Override
	public Response getAllCompanyPartner(String userId, String companyId) throws NotFoundException, ServiceInvokeException {
		log.info("Inside CompanyPartnerController.getAllCompanyPartner () method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(companyPartnerProcessor.getAllCompanyPartner(userId, companyId)
				).setMessage("Company partner fetched successfully")
				.create();
	}

}
