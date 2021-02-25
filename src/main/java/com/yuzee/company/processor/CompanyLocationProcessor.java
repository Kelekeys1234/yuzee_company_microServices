package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyLocationDao;
import com.yuzee.company.dto.CompanyLocationDto;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyLocation;
import com.yuzee.company.utills.DTOUtills;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyLocationProcessor {
	
	@Autowired
	private CompanyLocationDao companyLocationDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private StorageHandler storageHandler;

	public CompanyLocationDto addCompanyLocation(String userId, String companyId, CompanyLocationDto companyLocationDto) throws NotFoundException, UnauthorizeException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		Company company = optionalCompany.get();
		log.info("Populating company location model from company location dto");
		CompanyLocation companyLocation = DTOUtills.populateCompanyLocationModelFromCompanyLocationDto(companyLocationDto);
		companyLocation.setCompany(company);
		log.info("Saving company location into DB");
		companyLocationDto.setCompanyLocationId(companyLocationDao.addUpdateCompanyLocation(companyLocation).getId());
		return companyLocationDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyLocation(String userId , String companyId , String companyLocationId) throws NotFoundException, UnauthorizeException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Deleting company location with company Id {} and location Id {}",companyId,companyLocationId);
		companyLocationDao.deleteCompanyLocationByCompanyIdAndLocationId(companyId, companyLocationId);
	}
	
	@Transactional
	public List<CompanyLocationDto> getAllCompanyLocation(String userId , String companyId) throws NotFoundException {
		List<CompanyLocation> listOfCompanyLocation = new ArrayList<>();
		List<CompanyLocationDto> listOfCompanyLocationDto = new ArrayList<>(); 
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		
		if (isAdmin) {
			listOfCompanyLocation =  optionalCompany.get().getListOfCompanyLocation();
		} else {
			listOfCompanyLocation = companyLocationDao.getCompanyLocationByCompanyIdAndPrivacyLevel(companyId, PrivacyLevelEnum.PUBLIC);
		}
		
		log.info("Creating company location dto from company location model");
		listOfCompanyLocation.stream().forEach(companyLocation -> {
			CompanyLocationDto companyLocationDto = DTOUtills.populateCompanyLocationDtoFromCompanyLocationModel(companyLocation);
			log.info("Calling storage serive to fetch icon");
			try {
				List<StorageDto> storages = storageHandler.getStoragesResponse(companyLocationDto.getCompanyLocationId(), EntityTypeEnum.COMPANY, EntitySubTypeEnum.LOGO, Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
				companyLocationDto.setIcon( !CollectionUtils.isEmpty(storages)  ? storages.get(0): null);
			} catch (NotFoundException | ServiceInvokeException e) {
				log.error("Exception occured while calling storage service for location id {} exception {}",companyLocationDto.getCompanyLocationId(),e);
			}
			listOfCompanyLocationDto.add(companyLocationDto);
		});
		
		return listOfCompanyLocationDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public CompanyLocationDto updateCompanyLocation (String userId , String companyId , String companyLocationId, CompanyLocationDto companyLocationDto ) throws NotFoundException, UnauthorizeException {
		
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		
		log.info("Getting company location for company id {} and location id {}",companyId, companyLocationId);
		CompanyLocation companyLocation = companyLocationDao.getCompanyLocationByCompanyIdAndId(companyId, companyLocationId);
		if (ObjectUtils.isEmpty(companyLocation)) {
			log.error("No ccompany location found for company id {} and location id {}",companyId, companyLocationId);
			throw new NotFoundException("No company location found for company id "+companyId+" and location id "+companyLocationId);
		}
		
		log.info("Updating exsisting Company location with new company location dto");
		companyLocation = DTOUtills.updateCompanyLocationModelFromCompanyLocationDto(companyLocation, companyLocationDto);
		log.info("Saving company location into DB");
		companyLocationDto.setCompanyLocationId(companyLocationDao.addUpdateCompanyLocation(companyLocation).getId());
		return companyLocationDto;
	}
}