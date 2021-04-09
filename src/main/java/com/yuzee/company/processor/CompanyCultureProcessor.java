package com.yuzee.company.processor;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyCultureDao;
import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dto.CompanyCultureMissionDto;
import com.yuzee.company.dto.CompanyCultureVisionDto;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyCulture;
import com.yuzee.company.model.CompanyMission;
import com.yuzee.company.model.CompanyVision;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyCultureProcessor {

	@Autowired
	private CompanyCultureDao companyCultureDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateCompanyCultureMission(String userId , String companyId , CompanyCultureMissionDto companyCultureMissionDto) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCultureProcessor.addUpdateCompanyCultureMission() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company culture by company id {}",companyId);
		CompanyCulture companyCulture = companyCultureDao.getCompanyCultureByCompanyId(companyId);
		if (ObjectUtils.isEmpty(companyCulture)) {
			log.info("Creating new company culture model");
			companyCulture = new CompanyCulture(new Date(), new Date(), "API", "API");
			log.info("Adding new company culture mission");
			CompanyMission companyMission = new CompanyMission(PrivacyLevelEnum.valueOf(companyCultureMissionDto.getPrivacyLevel()), companyCultureMissionDto.getMission(), new Date(), new Date(), "API", "API");
			companyCulture.addCompanyMission(companyMission);
			companyCulture.setCompany(optionalCompany.get());
		} else {
			log.info("updaying company culture mission model");
			CompanyMission companyMissionFromDb = companyCulture.getCompanyMission();
			if (ObjectUtils.isEmpty(companyMissionFromDb)) {
				log.info("Adding new company culture mission");
				CompanyMission companyMission = new CompanyMission(PrivacyLevelEnum.valueOf(companyCultureMissionDto.getPrivacyLevel()), companyCultureMissionDto.getMission(), new Date(), new Date(), "API", "API");
				companyCulture.addCompanyMission(companyMission);
			} else {
				companyMissionFromDb.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyCultureMissionDto.getPrivacyLevel()));
				companyMissionFromDb.setMission(companyCultureMissionDto.getMission());
				companyMissionFromDb.setUpdatedBy("API");
				companyMissionFromDb.setUpdatedOn(new Date());
			}
		}
		companyCultureDao.addUpdateCompanyCulture(companyCulture);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateCompanyCultureVision(String userId , String companyId , CompanyCultureVisionDto companyCultureVisionDto) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCultureProcessor.addUpdateCompanyCultureVision() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company culture by company id {}",companyId);
		CompanyCulture companyCulture = companyCultureDao.getCompanyCultureByCompanyId(companyId);
		if (ObjectUtils.isEmpty(companyCulture)) {
			log.info("Creating new company culture model");
			companyCulture = new CompanyCulture(new Date(), new Date(), "API", "API");
			log.info("Adding new company culture mission");
			CompanyVision companyVision = new CompanyVision(PrivacyLevelEnum.valueOf(companyCultureVisionDto.getPrivacyLevel()), companyCultureVisionDto.getVision(), new Date(), new Date(), "API", "API");
			companyCulture.addCompanyVision(companyVision);
			companyCulture.setCompany(optionalCompany.get());
		} else {
			log.info("updaying company culture vision model");
			CompanyVision companyVisionFromDB = companyCulture.getCompanyVision();
			if (ObjectUtils.isEmpty(companyVisionFromDB)) {
				log.info("Adding new company culture vision");
				CompanyVision companyVision = new CompanyVision(PrivacyLevelEnum.valueOf(companyCultureVisionDto.getPrivacyLevel()), companyCultureVisionDto.getVision(), new Date(), new Date(), "API", "API");
				companyCulture.addCompanyVision(companyVision);
			} else {
				companyVisionFromDB.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyCultureVisionDto.getPrivacyLevel()));
				companyVisionFromDB.setVision(companyCultureVisionDto.getVision());
				companyVisionFromDB.setUpdatedBy("API");
				companyVisionFromDB.setUpdatedOn(new Date());
			}
		}
		companyCultureDao.addUpdateCompanyCulture(companyCulture);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyCultureMission(String userId , String companyId) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCultureProcessor.deleteCompanyCultureMission() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company culture by company id {}",companyId);
		CompanyCulture companyCulture = companyCultureDao.getCompanyCultureByCompanyId(companyId);
		if (ObjectUtils.isEmpty(companyCulture)) {
			log.error("No company culture found for company id {}",companyId);
			throw new NotFoundException("No company culture found for company id "+companyId);	
		}
		companyCulture.setCompanyMission(null);
		companyCultureDao.addUpdateCompanyCulture(companyCulture);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyCultureVision(String userId , String companyId) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCultureProcessor.deleteCompanyCultureVision() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company culture by company id {}",companyId);
		CompanyCulture companyCulture = companyCultureDao.getCompanyCultureByCompanyId(companyId);
		if (ObjectUtils.isEmpty(companyCulture)) {
			log.error("No company culture found for company id {}",companyId);
			throw new NotFoundException("No company culture found for company id "+companyId);	
		}
		companyCulture.setCompanyVision(null);
		companyCultureDao.addUpdateCompanyCulture(companyCulture);
	}
	
	public void addUpdateCompanyCultureMembers(String userId , String companyId) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCultureProcessor.addUpdateCompanyCultureMembers() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
	}
}
