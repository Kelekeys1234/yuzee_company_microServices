package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyAwardAndCertificationDao;
import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dto.CompanyAwardAndCertificationDto;
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
import com.yuzee.company.model.CompanyAwardAndCertification;
import com.yuzee.company.utills.DTOUtills;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyAwardAndCertificationProcessor {
	
	@Autowired
	private CompanyAwardAndCertificationDao companyAwardAndCertificationDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private StorageHandler storageHandler;
	
	public CompanyAwardAndCertificationDto addCompanyAwardAndCertification (String userId , String companyId , CompanyAwardAndCertificationDto companyAwardAndCertificationDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyAwardAndCertificationProcessor.addCompanyAwardAndCertification () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Creating CompanyAwardAndCertification model from CompanyAwardAndCertificationDto");
		CompanyAwardAndCertification companyAwardAndCertification =  DTOUtills.populateCompanyAwardAndCertificationFromCompanyAwardAndCertificationDto(companyAwardAndCertificationDto);
		companyAwardAndCertification.setCompany(optionalCompany.get());
		log.info("Saving Company award and certification for company id {}",companyId);
		companyAwardAndCertificationDto.setAwardCertificationId(companyAwardAndCertificationDao.addUpdateCompanyAwardAndCertification(companyAwardAndCertification).getId());
		return companyAwardAndCertificationDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyAwardAndCertification(String userId , String companyId , String awardCertificationId) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.debug("Inside CompanyAwardAndCertificationProcessor.deleteCompanyAwardAndCertification() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company award and certification having company id {} and cerification id {}", companyId, awardCertificationId);
		CompanyAwardAndCertification companyAwardAndCertification = companyAwardAndCertificationDao.getCompanyAwardAndCertificationByCompanyIdAndId(companyId, awardCertificationId);
		if (ObjectUtils.isEmpty(companyAwardAndCertification)) {
			log.error("No company award and certification found for company id {} and award certification id {}",companyId,awardCertificationId);
			throw new NotFoundException("No company award and certification found for company id " + companyId + " and award certification id " +awardCertificationId);
		}
		log.info("Deleting storage for company id {} and certification id {}",companyId, awardCertificationId);
		storageHandler.deleteStorage(awardCertificationId);
		log.info("Deleting company award and certification with company id {} and certification id {}",companyId, awardCertificationId);
		companyAwardAndCertificationDao.deleteByCompanyIdAndId(companyId, awardCertificationId);
	}
	
	@Transactional
	public List<CompanyAwardAndCertificationDto> getAllCompanyAwardAndCertification(String userId , String companyId) throws NotFoundException {
		List<CompanyAwardAndCertification> listOfCompanyAwardAndCertification = new ArrayList<>();
		List<CompanyAwardAndCertificationDto> listOfCompanyAwardAndCertificationDto = new ArrayList<>();
		log.debug("Inside CompanyAwardAndCertificationProcessor.getAllCompanyAwardAndCertification () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		
		if (isAdmin) {
			listOfCompanyAwardAndCertification =  optionalCompany.get().getListOfCompanyAwardAndCertification();
		} else {
			listOfCompanyAwardAndCertification = companyAwardAndCertificationDao.getCompanyAwardAndCertificationByCompanyIdAndPrivacyLevel(companyId, PrivacyLevelEnum.PUBLIC);
		}
		List<StorageDto> listOfStorages = new ArrayList<>();
		Map<String, List<StorageDto>> mapOfStorage = null;
		List<String> awardAndCertIds = listOfCompanyAwardAndCertification.stream().map(CompanyAwardAndCertification::getId).collect(Collectors.toList());
		try {
			listOfStorages = storageHandler.getStoragesResponse(awardAndCertIds, EntityTypeEnum.COMPANY, EntitySubTypeEnum.LOGO, Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
			mapOfStorage = listOfStorages.stream().collect(Collectors.groupingBy(StorageDto::getEntityId, Collectors.toList()));
		
		} catch (NotFoundException | ServiceInvokeException e1) {
			log.error("Exception occured which calling storage for having exception {}",e1);
		}
		final Map<String, List<StorageDto>> finalMapOfStorage = mapOfStorage;
		listOfCompanyAwardAndCertification.stream().forEach(companyAwardAndCertification -> {
				CompanyAwardAndCertificationDto companyAwardAndCertificationDto =  DTOUtills.populateCompanyAwardAndCertificationDtoFromCompanyAwardAndCertificationModel(companyAwardAndCertification);
				companyAwardAndCertificationDto.setListOfStorageDto(finalMapOfStorage.get(companyAwardAndCertification.getId()));
				listOfCompanyAwardAndCertificationDto.add(companyAwardAndCertificationDto);
			});
		
		return listOfCompanyAwardAndCertificationDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public CompanyAwardAndCertificationDto updateCompanyAwardAndCertification (String userId , String companyId , String awardCertificationId , CompanyAwardAndCertificationDto companyAwardAndCertificationDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyAwardAndCertificationProcessor.addCompanyAwardAndCertification () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		
		log.info("Getting company award and certification having company id {} and cerification id {}", companyId, awardCertificationId);
		CompanyAwardAndCertification companyAwardAndCertification = companyAwardAndCertificationDao.getCompanyAwardAndCertificationByCompanyIdAndId(companyId, awardCertificationId);
		if (ObjectUtils.isEmpty(companyAwardAndCertification)) {
			log.error("No company award and certification found for company id {} and award certification id {}",companyId,awardCertificationId);
			throw new NotFoundException("No company award and certification found for company id " + companyId + " and award certification id " +awardCertificationId);
		}
		
		log.info("updating CompanyAwardAndCertification model from CompanyAwardAndCertificationDto");
		companyAwardAndCertification.setUpdatedOn(new Date());
		companyAwardAndCertification.setUpdatedBy("API");
		companyAwardAndCertification.setTitle(companyAwardAndCertificationDto.getTitle());
		companyAwardAndCertification.setDescription(companyAwardAndCertificationDto.getDescription());
		companyAwardAndCertification.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyAwardAndCertificationDto.getPrivacyLevel()));
		log.info("Saving Company award and certification for company id {}",companyId);
		companyAwardAndCertificationDto.setAwardCertificationId(companyAwardAndCertificationDao.addUpdateCompanyAwardAndCertification(companyAwardAndCertification).getId());
		try {
			companyAwardAndCertificationDto.setListOfStorageDto(storageHandler.getStoragesResponse(companyAwardAndCertification.getId(), EntityTypeEnum.COMPANY, EntitySubTypeEnum.LOGO , Arrays.asList(PrivacyLevelEnum.PUBLIC.name())));
		} catch (NotFoundException | ServiceInvokeException e) {
			log.error("Exception occured which calling storage for entity id {} having exception {}",companyAwardAndCertification.getId(),e);
		}
		return companyAwardAndCertificationDto;
	}
	
	

}
