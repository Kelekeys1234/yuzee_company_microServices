package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyScholarshipDao;
import com.yuzee.company.dto.CompanyScholarshipDto;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyScholarship;
import com.yuzee.company.utills.DTOUtills;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyScholarshipProcessor {

	@Autowired
	private CompanyScholarshipDao companyScholarshipDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private StorageHandler storageHandler;
	
	public CompanyScholarshipDto addCompanyScholarship(String userId, String companyId, CompanyScholarshipDto companyScholarshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyScholarshipProcessor.addCompanyScholarship() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Creating company scholarship model from scholarship dto");
		CompanyScholarship companyScholarship =  DTOUtills.populateCompanyScholarshipFromCompanyScholarshipDto(companyScholarshipDto);
		companyScholarship.setCompany(optionalCompany.get());
		log.info("Adding company scholarship model into db and returning response");
		companyScholarshipDto.setCompanyScholarshipId(companyScholarshipDao.addUpdateCompanyScholarship(companyScholarship).getId());
		return companyScholarshipDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public CompanyScholarshipDto updateCompanyScholarship(String userId, String companyId,String companyScholarshipId , CompanyScholarshipDto companyScholarshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyScholarshipProcessor.updateCompanyScholarship() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		CompanyScholarship companyScholarship = companyScholarshipDao.getCompanyScholarshipByCompanyIdAndId(companyId, companyScholarshipId);
		
		if (ObjectUtils.isEmpty(companyScholarship)) {
			log.error("No company scholarship found for company id {} and scholarship id {}",companyId,companyScholarshipId);
			throw new NotFoundException("No company scholarship found for company id "+companyId+ " and scholarship id "+companyScholarshipId);
		}
		companyScholarship = DTOUtills.updateCompanyScholarshipFromCompanyScholarshipDto(companyScholarshipDto, companyScholarship);
		log.info("updating company scholarship model into db and returning response");
		companyScholarshipDto.setCompanyScholarshipId(companyScholarshipDao.addUpdateCompanyScholarship(companyScholarship).getId());
		return companyScholarshipDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyScholarship(String userId , String companyId ,String companyScholarshipId  ) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.debug("Inside CompanyScholarshipProcessor.deleteCompanyScholarship() method");
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
		
		log.info("Deleting storage for company scholarship id {}",companyScholarshipId);
		storageHandler.deleteStorage(companyScholarshipId);
		log.info("Deleting scholarship for companyId {} and scholarshipId {}",companyId, companyScholarshipId);
		companyScholarshipDao.deleteCompanyScholarshipByCompanyIdAndId(companyId, companyScholarshipId);
	}
	
	@Transactional
	public List<CompanyScholarshipDto> getAllCompanyScholarship (String userId , String companyId) throws NotFoundException {
		List<CompanyScholarshipDto> listOfCompanyScholarshipDto = new ArrayList<>();
		log.debug("Inside CompanyScholarshipProcessor.deleteCompanyScholarship() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		return listOfCompanyScholarshipDto;
	}
}
