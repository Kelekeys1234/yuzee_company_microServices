package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.SpecialityDao;
import com.yuzee.company.dto.CompanyAboutUsDto;
import com.yuzee.company.dto.CompanySpecialityDto;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanySpeciality;
import com.yuzee.company.model.Speciality;
import com.yuzee.company.utills.DTOUtills;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyAboutUsProcessor {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private SpecialityDao specialityDao;
	
	@Autowired
	private StorageHandler storageHandler;

	@Transactional(rollbackOn = Throwable.class)
	public CompanyAboutUsDto addUpdateCompanyAboutUsInfo (String userId, String companyId , CompanyAboutUsDto companyAboutUsDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		Company company = optionalCompany.get();
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, company);
		log.info("Populate fields related to about us in company model");
		company = DTOUtills.populateCompanyModelFromCompanyAboutUsDto(company, companyAboutUsDto);
		log.info("Fetching list of exsisting company speciality");
		List<CompanySpeciality> listOfExsistingCompanySpeciality = company.getListOfCompanySpeciality();
		if (!CollectionUtils.isEmpty(listOfExsistingCompanySpeciality)) {
			log.info("Removing all speciality which is not present in lastest list passed in request");
			listOfExsistingCompanySpeciality.removeIf(companySpeciality -> !companyAboutUsDto.getSpeciality().stream().anyMatch(companySpecialityDto -> companySpecialityDto.getSpecialityId().equals(companySpeciality.getSpeciality().getId())));
			log.info("Adding additional speciality found in request but not present in exsiting company speciality");
			List<CompanySpecialityDto> companySpecialityTobeAdded = companyAboutUsDto.getSpeciality().stream().filter(companySpecialityDto -> listOfExsistingCompanySpeciality.stream().anyMatch(companySpeciality -> !companySpeciality.getSpeciality().getId().equalsIgnoreCase(companySpecialityDto.getSpecialityId()))).collect(Collectors.toList());
			company = this.populateCompanySpecialityIntoComapny(companySpecialityTobeAdded, company);
		} else {
			log.info("list of exsisting company speciality dto is empty adding all pass in request");
			company = this.populateCompanySpecialityIntoComapny(companyAboutUsDto.getSpeciality(), company);
		}
		companyDao.addCompany(company);
		return companyAboutUsDto;
	}
	
	private Company populateCompanySpecialityIntoComapny (List<CompanySpecialityDto> listOfCompanySpecialityDto, Company company) {
		List<Speciality> listOfSpeciality  = new ArrayList<>();
		log.info("list of exsisting company speciality dto is empty adding all pass in request");
		Set<String> specialityIds = listOfCompanySpecialityDto.stream().map(CompanySpecialityDto::getSpecialityId).collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(specialityIds)) {
			listOfSpeciality = specialityDao.getSpecialityByIds(specialityIds);
			listOfSpeciality.stream().map(speciality -> new CompanySpeciality( speciality, new Date (),  new Date (), "API", "API")).forEach(company::addCompanySpeciality);
		}
		return company;
	}
	
	@Transactional
	public CompanyAboutUsDto getCompanyAboutUsInfo (String userId, String companyId) throws NotFoundException {
		List<StorageDto> videos = new ArrayList<>();
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Populating company about us info");
		CompanyAboutUsDto companyAboutUsDto = DTOUtills.populateCompanyAboutUsDtoFromCompanyModel(optionalCompany.get());
		log.info("Calling storeage service to fetch videos for company id {}",companyId);
		try {
			 videos = storageHandler.getStoragesResponse(companyId, EntityTypeEnum.COMPANY, EntitySubTypeEnum.VIDEO, Arrays.asList(PrivacyLevelEnum.PUBLIC.name() ));
			 
		} catch (NotFoundException | ServiceInvokeException e) {
			log.error("Exception occured while calling storage for fetching videos for company id {}",companyId);
		}
		companyAboutUsDto.setVideos(videos);
		return companyAboutUsDto;
	}
}
