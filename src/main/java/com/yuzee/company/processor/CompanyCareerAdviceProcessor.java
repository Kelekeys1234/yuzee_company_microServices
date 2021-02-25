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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyCareerAdviceDao;
import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dto.CompanyCareerAdviceDto;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyCareerAdvice;
import com.yuzee.company.model.CompanyEmployee;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyCareerAdviceProcessor {

	@Autowired
	private CompanyCareerAdviceDao companyCareerAdviceDao;

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private StorageHandler storageHandler;

	public CompanyCareerAdviceDto addCompanyCareerAdvice(String userId , String companyId, CompanyCareerAdviceDto companyCareerAdviceDto ) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCareerAdviceProcessor.addCompanyCareerAdvice () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Creating career advice model from dto");
		CompanyCareerAdvice companyCareerAdvice = new CompanyCareerAdvice(companyCareerAdviceDto.getTitle(), companyCareerAdviceDto.getDescription(), optionalCompany.get(), new Date(), new Date(), "API", "API");
		log.info("Creating model for company employee");
		List<CompanyEmployee> listOfCompanyEmployee = companyCareerAdviceDto.getUserInEmployment().stream().map(employeeUserId -> new CompanyEmployee(employeeUserId, new Date(), new Date(), "API", "API")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(listOfCompanyEmployee)) {
			log.info("Adding company employee with company career advice");
			companyCareerAdvice.addCompanyEmployee(listOfCompanyEmployee);
		}
		log.info("Saving Company career advice into DB");
		companyCareerAdviceDto.setCompanyCareerAdviceId(companyCareerAdviceDao.addUpdateCompanyCareerAdvice(companyCareerAdvice).getId());
		return companyCareerAdviceDto;
	}
	
	@Transactional
	public List<CompanyCareerAdviceDto> getAllCompanyCareerAdvice(String userId , String companyId) throws NotFoundException {
		List<CompanyCareerAdviceDto> listOfCompanyCareerAdviceDto = new ArrayList<>();
		log.debug("Inside CompanyCareerAdviceProcessor.getAllCompanyCareerAdvice () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		log.info("Getting all company career advice for company id {}",companyId);
		List<CompanyCareerAdvice> listOfCompanyCareerAdvice = optionalCompany.get().getListOfCompanyCareerAdvice();
		if (!CollectionUtils.isEmpty(listOfCompanyCareerAdvice)) {
			log.info("Getting all company career advice ids");
			List<String> companyCareerIds = listOfCompanyCareerAdvice.stream().map(CompanyCareerAdvice::getId).collect(Collectors.toList());
			log.info("Getting storage entity");
			List<StorageDto> listOfStorageDto = new ArrayList<>();
			try {
				listOfStorageDto =	storageHandler.getStoragesResponse(companyCareerIds, EntityTypeEnum.COMPANY, Arrays.asList(EntitySubTypeEnum.COMPANY_CAREER_ADVICE, EntitySubTypeEnum.VIDEO, EntitySubTypeEnum.COVER_PHOTO), isAdmin ? Arrays.asList(PrivacyLevelEnum.PUBLIC.name(), PrivacyLevelEnum.PRIVATE.name()) : Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
			} catch (NotFoundException | ServiceInvokeException e) {
				log.error("Exception ocuured while calling storage service {}",e);
			}
			log.info("Segregatting list of storage based on entity id");
			Map<String, List<StorageDto>> mapOfStorageDto = listOfStorageDto.stream().collect(Collectors.groupingBy(StorageDto::getEntityId));
			log.info("Creating company career advice response from model");
			listOfCompanyCareerAdvice.stream().forEach(companyCareerAdvice -> {
				CompanyCareerAdviceDto companyCareerAdviceDto = new CompanyCareerAdviceDto();
				companyCareerAdviceDto.setCompanyCareerAdviceId(companyCareerAdvice.getId());
				companyCareerAdviceDto.setTitle(companyCareerAdvice.getTitle());
				companyCareerAdviceDto.setDescription(companyCareerAdvice.getDescription());
				companyCareerAdviceDto.setListOfStorageDto(null != mapOfStorageDto ? mapOfStorageDto.get(companyCareerAdvice.getId()) : null);
				companyCareerAdviceDto.setUserInEmployment(companyCareerAdvice.getListOfCompanyEmployee().stream().map(companyEmployee -> companyEmployee.getUserId()).collect(Collectors.toSet()));
				companyCareerAdviceDto.setCreatedDate(companyCareerAdvice.getCreatedOn());
				listOfCompanyCareerAdviceDto.add(companyCareerAdviceDto);
			});
		}
		
		return listOfCompanyCareerAdviceDto;
	}
	
	@Transactional(rollbackOn =  Throwable.class)
	public void deleteCompanyCareerAdvice(String userId , String companyId, String companyCareerAdviceId) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.debug("Inside CompanyCareerAdviceProcessor.deleteCompanyCareerAdvice () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company career advice for company id {} and id {}",companyId,companyCareerAdviceId);
		CompanyCareerAdvice companyCareerAdvice = companyCareerAdviceDao.getCompanyCareerAdviceByCompanyIdAndId(companyId, companyCareerAdviceId);
		if (ObjectUtils.isEmpty(companyCareerAdvice)) {
			log.error("No company career advice for company id {} and id {}",companyId,companyCareerAdviceId);
			throw new NotFoundException("No company career advice for company id "+companyId +" and id "+companyCareerAdviceId);
		}
		log.info("Deleting storage for entity id {}",companyCareerAdviceId);
		storageHandler.deleteStorage(companyCareerAdviceId);
		log.info("Deleting company career advice for company id {} and id {}",companyId,companyCareerAdviceId);
		companyCareerAdviceDao.deleteCompanyCareerAdviceByCompanyIdAndId(companyId, companyCareerAdviceId);
	}
	
	@Transactional(rollbackOn =  Throwable.class)
	public CompanyCareerAdviceDto updateCompanyCareerAdvice(String userId , String companyId, String companyCareerAdviceId, CompanyCareerAdviceDto companyCareerAdviceDto ) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyCareerAdviceProcessor.updateCompanyCareerAdvice () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company career advice for company id {} and id {}",companyId,companyCareerAdviceId);
		CompanyCareerAdvice companyCareerAdvice = companyCareerAdviceDao.getCompanyCareerAdviceByCompanyIdAndId(companyId, companyCareerAdviceId);
		if (ObjectUtils.isEmpty(companyCareerAdvice)) {
			log.error("No company career advice for company id {} and id {}",companyId,companyCareerAdviceId);
			throw new NotFoundException("No company career advice for company id "+companyId +" and id "+companyCareerAdviceId);
		}
		log.info("Updating company career advice based on new data");
		companyCareerAdvice.setTitle(companyCareerAdviceDto.getTitle());
		companyCareerAdvice.setDescription(companyCareerAdviceDto.getDescription());
		companyCareerAdvice.setUpdatedBy("API");
		companyCareerAdvice.setUpdatedOn(new Date());
		log.info("Removing all company employee in db and not passed in requet");
		log.info("Adding all company employee present in request and not in db");
		List<String> listOfCompanyUserBeAdded = companyCareerAdviceDto.getUserInEmployment().stream().filter(employeeUserId -> !companyCareerAdvice.getListOfCompanyEmployee().stream().anyMatch(companyCareerAdviceModel -> companyCareerAdviceModel.getUserId().equalsIgnoreCase(employeeUserId))).collect(Collectors.toList());
		companyCareerAdvice.getListOfCompanyEmployee().removeIf(companyEmployee -> !companyCareerAdviceDto.getUserInEmployment().stream().anyMatch(employeeUserId -> employeeUserId.equalsIgnoreCase(companyEmployee.getUserId())));
		List<CompanyEmployee> listOfCompanyEmployee = listOfCompanyUserBeAdded.stream().map(employeeUserId -> new CompanyEmployee(employeeUserId, new Date(), new Date(), "API", "API")).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(listOfCompanyEmployee)) {
			log.info("Adding company employee with company career advice");
			companyCareerAdvice.addCompanyEmployee(listOfCompanyEmployee);
		}
		log.info("Saving Company career advice into DB");
		companyCareerAdviceDto.setCompanyCareerAdviceId(companyCareerAdviceDao.addUpdateCompanyCareerAdvice(companyCareerAdvice).getId());
		return companyCareerAdviceDto;
	}
	
}