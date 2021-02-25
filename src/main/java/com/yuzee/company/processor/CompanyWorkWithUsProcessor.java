package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyWorkWithUsDao;
import com.yuzee.company.dto.CompanyWorkWithUsDto;
import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyWorkWithUs;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyWorkWithUsProcessor {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyWorkWithUsDao companyWorkWithUsDao;

	@Transactional(rollbackOn = Throwable.class)
	public CompanyWorkWithUsDto addUpdateCompanyWorkWithUs(String userId, String companyId, CompanyWorkWithUsDto companyWorkWithUsDto) throws NotFoundException, UnauthorizeException {
		CompanyWorkWithUs companyWorkWithUs = null;
		log.debug("Inside CompanyWorkWithUsProcessor.addUpdateCompanyWorkWithUs() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		Company company = optionalCompany.get();
		log.info("Checking if company work with us present for company id {} and work with us {}",companyId,companyWorkWithUsDto.getTitleName());
		Optional<CompanyWorkWithUs> optionalCompanyWorkWithUs = companyWorkWithUsDao.getCompanyWorkWithUsByCompanyIdAndWorkWithUsValue(companyId, WorkWithUs.valueOf(companyWorkWithUsDto.getTitleName()));
		if (optionalCompanyWorkWithUs.isEmpty()) {
			log.info("No work with us found for company id and work with us {} so adding new entry",companyId, companyWorkWithUsDto.getTitleName());
			companyWorkWithUs = new CompanyWorkWithUs( WorkWithUs.valueOf(companyWorkWithUsDto.getTitleName()), company, companyWorkWithUsDto.getDescription(), new Date(), new Date(), "API", "API");
			
		} else {
			log.info("Work with us found for company id and work with us {} so updating entry",companyId, companyWorkWithUsDto.getTitleName());
			companyWorkWithUs = optionalCompanyWorkWithUs.get();
			companyWorkWithUs.setDescription(companyWorkWithUsDto.getDescription());
			companyWorkWithUs.setUpdatedBy("API");
			companyWorkWithUs.setUpdatedOn(new Date());
		}
		log.info("Saving company work with us into DB");
		companyWorkWithUsDto.setCompanyWorkWithUsId(companyWorkWithUsDao.addUpdateCompanyWorkWithUs(companyWorkWithUs).getId());
		companyWorkWithUsDto.setTitleValue(companyWorkWithUs.getWorkWithUs().getValue());
		return companyWorkWithUsDto;
	}
	
	@Transactional
	public List<CompanyWorkWithUsDto> getAllWorkWithUsInfo(String userId, String companyId) throws NotFoundException {
		List<CompanyWorkWithUsDto> listOfCompanyWorkWithUsDto = new ArrayList<>();
		log.debug("Inside CompanyWorkWithUsProcessor.getAllWorkWithUsInfo() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Getting all company work with us for company id {}",companyId);
		listOfCompanyWorkWithUsDto = optionalCompany.get().getListOfCompanyWorkWithUs().stream().map(companyWorkWithUs -> new CompanyWorkWithUsDto(companyWorkWithUs.getId(), companyWorkWithUs.getWorkWithUs().getValue(),companyWorkWithUs.getWorkWithUs().name(), companyWorkWithUs.getDescription())).collect(Collectors.toList());
		return listOfCompanyWorkWithUsDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyWorkWithUsInfo (String userId , String companyId, String companyWorkWithUsId) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyWorkWithUsProcessor.deleteCompanyWorkWithUsInfo() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Deleting company work with us for company id {} and company work with us id {}",companyId,companyWorkWithUsId);
		companyWorkWithUsDao.deleteCompanyWorkWithUsByCompanyIdAndCompanyWorkWithUsId(companyId, companyWorkWithUsId);
	}
}