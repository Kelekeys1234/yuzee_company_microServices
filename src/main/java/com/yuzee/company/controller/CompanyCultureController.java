package com.yuzee.company.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.CompanyCultureMissionDto;
import com.yuzee.company.dto.CompanyCultureVisionDto;
import com.yuzee.company.endpoint.CompanyCultureInterface;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.processor.CompanyCultureProcessor;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CompanyCultureController implements CompanyCultureInterface {
	
	@Autowired
	private CompanyCultureProcessor companyCultureProcessor;

	@Override
	public Response addUpdateCompanyCultureVision(String userId, String companyId,
			@Valid @NotNull(message = "Request body should not be null/empty") CompanyCultureVisionDto companyCultureVisionDto)
			throws NotFoundException, UnauthorizeException, BadRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response addUpdateCompanyCultureMission(String userId, String companyId,
			@Valid @NotNull(message = "Request body should not be null/empty") CompanyCultureMissionDto companyCultureMissionDto)
			throws NotFoundException, UnauthorizeException, BadRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
