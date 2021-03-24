package com.yuzee.company.processor;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyScholarshipDao;
import com.yuzee.company.dto.CompanyScholarshipGenericDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyScholarship;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyScholarshipConditionProcessor {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyScholarshipDao companyScholarshipDao;
	
	@Transactional
	public CompanyScholarshipGenericDto addUpdateCompanyScholarshipCondition(String userId , String companyId, String companyScholarshipId, CompanyScholarshipGenericDto companyScholarshipGenericDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyScholarshipConditionProcessor.addUpdateCompanyScholarshipCondition() method");
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
		
		log.info("Adding conditions description for company scholarship id {}",companyScholarshipId);
		companyScholarship.setConditions(companyScholarshipGenericDto.getDescription());
		companyScholarship.setUpdatedBy("API");
		companyScholarship.setUpdatedOn(new Date());
		log.info("updating company scholarship  model into db and returning response");
		companyScholarshipDao.addUpdateCompanyScholarship(companyScholarship);
		return companyScholarshipGenericDto;
	}
	
	@Transactional
	public CompanyScholarshipGenericDto getCompanyScholarshipCondition(String userId , String companyId ,String companyScholarshipId) throws NotFoundException {
		log.debug("Inside CompanyScholarshipConditionProcessor.getCompanyScholarshipCondition() method");
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
		
		log.info("Generating CompanyScholarshipConsition from model");
		return new CompanyScholarshipGenericDto(companyScholarship.getConditions());
	}
}
