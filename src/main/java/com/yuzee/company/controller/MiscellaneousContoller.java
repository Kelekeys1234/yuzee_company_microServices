package com.yuzee.company.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.endpoint.MiscellaneousInterface;
import com.yuzee.company.enumeration.CompanyType;
import com.yuzee.company.enumeration.ContactTypeEnum;
import com.yuzee.company.enumeration.IndustryType;
import com.yuzee.company.enumeration.WeekDayEnum;
import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MiscellaneousContoller implements MiscellaneousInterface {
	
	@Value("${s3.url}")
	private String s3BaseUrl;
	
	@Value("${s3.usercontact.image.location}")
	private String testIconImageLocation;

	@Override
	public Response getWorkWithUsValues() {
		log.info("Inside MiscellaneousContoller.getWorkWithUsValues() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(Arrays.asList(WorkWithUs.values())).setMessage("Work with us enum value fetch successfully")
				.create();
	}

	@Override
	public Response getContactTypeValues() {
		log.info("Inside MiscellaneousContoller.getContactTypeValues() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(Arrays.asList(ContactTypeEnum.values()).stream().map(e -> {
			if(!e.isInitialized()) {
				e.setImageName(createFullImageURL(e.getImageName()));
				e.setInitialized(true);
			}
			return e;
			}).collect(Collectors.toList())).setMessage("Contact type values fetch successfully")
				.create(); 
	}

	@Override
	public Response getCompanyTypeValues() {
		log.info("Inside MiscellaneousContoller.getCompanyTypeValues() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(Arrays.asList(CompanyType.values())).setMessage("Company type enum value fetch successfully")
				.create();
	}

	@Override
	public Response getIndustryTypeValues() {
		log.info("Inside MiscellaneousContoller.getIndustryTypeValues() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(Arrays.asList(IndustryType.values())).setMessage("Industry type enum value fetch successfully")
				.create();
	}

	@Override
	public Response getWeekDaysEnumValues() {
		log.info("Inside MiscellaneousContoller.getWeekDaysEnumValues() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(Arrays.asList(WeekDayEnum.values())).setMessage("Week day enum value fetch successfully")
				.create();
	}
	
	private String createFullImageURL(String name) {
		return new StringBuffer().append(s3BaseUrl).append(testIconImageLocation).append(name).toString();
	}
}