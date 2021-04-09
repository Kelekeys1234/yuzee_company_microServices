package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.SpecialityDao;
import com.yuzee.company.dto.CompanyDto;
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
public class CompanyProcessor {
	
	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private SpecialityDao specialityDao;
	
	public CompanyDto addCompanyInfo (String userId , CompanyDto companyDto) throws BadRequestException {
		log.info("Creating company model by name {} by userId {}", companyDto.getCompanyName(),userId);
		Company company = DTOUtills.initiateCompanyModelFromCompanyDto(companyDto);
		log.info("Adding speciality into com");
		Set<String> specialityIds = companyDto.getSpecialitys().stream().map(CompanySpecialityDto::getSpecialityId).collect(Collectors.toSet());
		if (!CollectionUtils.isEmpty(specialityIds)) {
			List<Speciality> listOfSpeciality = specialityDao.getSpecialityByIds(specialityIds);
			listOfSpeciality.stream().map(speciality -> new CompanySpeciality( speciality, new Date (),  new Date (), "API", "API")).forEach(company::addCompanySpeciality);
		}
		log.info("Setting user id {}",userId);
		company.setCreatedBy(userId);
		log.info("Saving company model into DB");
		String companyId = companyDao.addCompany(company).getId();
		log.info("setting companyId {} in company response",companyId);
		companyDto.setId(companyId);
		return companyDto;
	}
	
	public CompanyDto updateCompanyInfo (String userId, String companyId , CompanyDto companyDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		Company company = DTOUtills.updateCompanyModelFromCompanyDto(optionalCompany.get(), companyDto);
		log.info("Saving company model into DB");
		companyDao.addCompany(company);
		log.info("setting companyId {} in company response",companyId);
		companyDto.setId(companyId);
		return companyDto;
	}
	
	public CompanyDto getCompanyInfo (String userId , String companyId) throws NotFoundException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		CompanyDto companyDto = DTOUtills.initiateCompanyDtoFromCompanyModel(optionalCompany.get());
		log.info("Calling storage service to fetch storage for company id {}",companyId);
		List<StorageDto> listOfStorage = new ArrayList<>();
		try {
			listOfStorage = storageHandler.getStoragesResponse(Arrays.asList(companyId), EntityTypeEnum.COMPANY, Arrays.asList(EntitySubTypeEnum.LOGO, EntitySubTypeEnum.COVER_PHOTO) , Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
		} catch (NotFoundException | ServiceInvokeException e) {
			log.error("Exception occured while calling storage service for company id {}",companyId);
		}
		listOfStorage.stream().forEach(storageDto -> {
			if (storageDto.getEntitySubType().equals(EntitySubTypeEnum.LOGO)) {
				companyDto.setProfilePicUrl(storageDto.getFileURL());
			} else if (storageDto.getEntitySubType().equals(EntitySubTypeEnum.COVER_PHOTO)) {
				companyDto.setCoverPhotoUrl(storageDto.getFileURL());
			}
		});
		return companyDto;
	}
}
