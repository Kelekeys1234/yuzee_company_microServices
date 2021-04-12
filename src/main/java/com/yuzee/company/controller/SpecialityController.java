package com.yuzee.company.controller;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.company.dto.SpecialityDto;
import com.yuzee.company.endpoint.SpecialityInterface;
import com.yuzee.company.processor.SpecialityProcessor;
import com.yuzee.company.utills.GenericResponseHandlers;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SpecialityController implements SpecialityInterface {
	
	@Autowired
	private SpecialityProcessor specialityProcessor;

	@Override
	public Response getAllSpeciality() {
		log.info("Inside SpecialityController.getAllSpeciality() method");
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setData(specialityProcessor.getSpeciality()).setMessage("Speciality fetched successfully")
				.create();
	}

	@Override
	public Response addSpeciality(List<SpecialityDto> listOfSpeciality) {
		log.info("Inside SpecialityController.addSpeciality() method");
		specialityProcessor.addSpeciality(listOfSpeciality);
		return new GenericResponseHandlers.Builder().setStatus(Status.OK).setMessage("Speciality added successfully")
				.create();
	}

}
