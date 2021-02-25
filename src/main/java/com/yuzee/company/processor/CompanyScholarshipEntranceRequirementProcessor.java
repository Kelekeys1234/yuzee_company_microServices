package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyScholarshipDao;
import com.yuzee.company.dto.CompanyScholarshipEntranceRequirementDto;
import com.yuzee.company.enumeration.Gender;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyScholarship;
import com.yuzee.company.model.ScholarshipRequiredLanguage;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyScholarshipEntranceRequirementProcessor {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyScholarshipDao companyScholarshipDao;
	
	@Transactional
	public CompanyScholarshipEntranceRequirementDto addUpdateCompanyScholarshipEntranceRequirement (String userId , String companyId ,String companyScholarshipId , CompanyScholarshipEntranceRequirementDto companyScholarshipEntranceRequirementDto) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyScholarshipEntranceRequirementProcessor.addUpdateCompanyScholarshipEntranceRequirement() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting scholarship for companyId {} and scholarshipId {}",companyId, companyScholarshipId);
		CompanyScholarship companyScholarship = companyScholarshipDao.getCompanyScholarshipByCompanyIdAndId(companyId, companyScholarshipId);
		
		if (ObjectUtils.isEmpty(companyScholarship)) {
			log.error("No company scholarship found for company id {} and scholarship id {}",companyId,companyScholarshipId);
			throw new NotFoundException("No company scholarship found for company id "+companyId+ " and scholarship id "+companyScholarshipId);
		}
		
		log.info("Adding / uploading CompanyScholarshipEntranceRequirement ");
		companyScholarship.setEligbleNationality(companyScholarshipEntranceRequirementDto.getEligibleNationality());
		companyScholarship.setGender(Gender.valueOf(companyScholarshipEntranceRequirementDto.getGender()));
		companyScholarship.setGeneralRequirements(companyScholarshipEntranceRequirementDto.getGeneralRequirements());
		companyScholarship.setUpdatedBy("API");
		companyScholarship.setUpdatedOn(new Date());
		if (CollectionUtils.isEmpty( companyScholarship.getListOfScholarshipRequiredLanguage())) {
			log.info("Adding all language passed in request to db");
			companyScholarshipEntranceRequirementDto.getLanguages().stream().map(language -> new ScholarshipRequiredLanguage(language, new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleLanguage);
		} else {
			List<String> listOfLanguageToBeAdded = new ArrayList<>();
			log.info("Adding eligible language present in request and not in db");
			listOfLanguageToBeAdded = companyScholarshipEntranceRequirementDto.getLanguages().stream().filter(languageName -> !companyScholarship.getListOfScholarshipRequiredLanguage().stream().anyMatch(scholarshipRequiredLanguage -> scholarshipRequiredLanguage.getLanguage().equalsIgnoreCase(languageName))).collect(Collectors.toList());
			log.info("Remove eligible language present in db and not in request");
			companyScholarship.getListOfScholarshipRequiredLanguage().removeIf(eligibleLanguage -> !companyScholarshipEntranceRequirementDto.getLanguages().stream().anyMatch(languageName -> languageName.equalsIgnoreCase(eligibleLanguage.getLanguage())));
			log.info("Creating eligible level model");
			listOfLanguageToBeAdded.stream().map(language -> new ScholarshipRequiredLanguage(language, new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleLanguage);
		}
		log.info("updating company scholarship  model into db and returning response");
		companyScholarshipDao.addUpdateCompanyScholarship(companyScholarship);
		return companyScholarshipEntranceRequirementDto;
	}
	
	
	@Transactional
	public CompanyScholarshipEntranceRequirementDto getCompanyScholarshipEntranceRequirement(String userId , String companyId ,String companyScholarshipId) throws NotFoundException {
		log.debug("Inside CompanyScholarshipEntranceRequirementProcessor.addUpdateCompanyScholarshipEntranceRequirement() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		log.info("Getting scholarship for companyId {} and scholarshipId {}",companyId, companyScholarshipId);
		CompanyScholarship companyScholarship = companyScholarshipDao.getCompanyScholarshipByCompanyIdAndId(companyId, companyScholarshipId);
		
		if (ObjectUtils.isEmpty(companyScholarship)) {
			log.error("No company scholarship found for company id {} and scholarship id {}",companyId,companyScholarshipId);
			throw new NotFoundException("No company scholarship found for company id "+companyId+ " and scholarship id "+companyScholarshipId);
		}
		
		log.info("Generating CompanyScholarshipEntranceRequirementDto from model");
		return new CompanyScholarshipEntranceRequirementDto(companyScholarship.getEligbleNationality(), (ObjectUtils.isEmpty(companyScholarship.getGender()) ? null :  companyScholarship.getGender().name()) , companyScholarship.getGeneralRequirements(), companyScholarship.getListOfScholarshipRequiredLanguage().stream().map(ScholarshipRequiredLanguage::getLanguage).collect(Collectors.toSet()));
	}
}
