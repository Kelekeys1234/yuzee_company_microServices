package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuzee.company.dao.SpecialityDao;
import com.yuzee.company.dto.SpecialityDto;
import com.yuzee.company.model.Speciality;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpecialityProcessor {
	
	@Autowired
	private SpecialityDao specialityDao;

	public void addSpeciality(List<SpecialityDto> listOfSpecialityDto) {
		List<SpecialityDto> listOfSpecialityDtoToBeAdded = new ArrayList<>();
		log.info("Getting all speciality from db");
		List<Speciality> listOfSpeciality = specialityDao.getAllSpeciality();
		if (!CollectionUtils.isEmpty(listOfSpeciality)) {
			log.info("checking list of speciality dto to add all non exsisting dto");
			listOfSpecialityDtoToBeAdded = listOfSpecialityDto.stream().filter(specialityDto -> listOfSpeciality.stream().anyMatch(speciality -> !specialityDto.getSpecialityName().equalsIgnoreCase(speciality.getSpecialityName()))).collect(Collectors.toList());
			log.info("Adding speciality in to db");
			specialityDao.addAllSpeciality(listOfSpecialityDtoToBeAdded.stream().map(specialityDto -> new Speciality(specialityDto.getSpecialityName(), new Date(), new Date(), "API", "API")).collect(Collectors.toList()));
		} else {
			log.info("list of speciality from db is empty adding all");
			specialityDao.addAllSpeciality(listOfSpecialityDto.stream().map(specialityDto -> new Speciality(specialityDto.getSpecialityName(), new Date(), new Date(), "API", "API")).collect(Collectors.toList()));
		}
	}
	
	public List<SpecialityDto> getSpeciality() {
		List<Speciality> listOfSpeciality = specialityDao.getAllSpeciality();
		return listOfSpeciality.stream().map(specialty -> new SpecialityDto(specialty.getId(), specialty.getSpecialityName())).collect(Collectors.toList());
	}
}
