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

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyStaffInterviewDao;
import com.yuzee.company.dto.CompanyStaffInterviewDto;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyStaffInterview;
import com.yuzee.company.model.InterviewTagedUser;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyStaffInterviewProcessor {

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyStaffInterviewDao companyStaffInterviewDao;
	
	@Autowired
	private StorageHandler storageHandler;

	@Transactional(rollbackOn = Throwable.class)
	public CompanyStaffInterviewDto addCompanyStaffInterview(String userId , String companyId, CompanyStaffInterviewDto companyStaffInterviewDto) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyStaffInterviewProcessor.addCompanyStaffInterview() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		Company company = optionalCompany.get();
		log.info("Creating Company staff interview model from dto");
		CompanyStaffInterview companyStaffInterview = new CompanyStaffInterview(companyStaffInterviewDto.getTitle(), companyStaffInterviewDto.getDiscription(), company, new Date(), new Date(), "API", "API");
		log.info("Creating  InterviewTagedUser model from user id passed in request");
		List<InterviewTagedUser> listOfInterviewTagedUser = companyStaffInterviewDto.getTagedInterviewee().stream().map(tagedUserId -> new InterviewTagedUser(tagedUserId, new Date(),  new Date(), "API", "API")).collect(Collectors.toList());
		companyStaffInterview.addTagedInterviewee(listOfInterviewTagedUser);
		log.info("Saving CompanyStaffInterview into DB ");
		companyStaffInterviewDto.setCompanyStaffInterviewId(companyStaffInterviewDao.addUpdateCompanyStaffInterview(companyStaffInterview).getId());
		return companyStaffInterviewDto;
	}

	@Transactional(rollbackOn = Throwable.class)
	public CompanyStaffInterviewDto updateCompanyStaffInterview(String userId , String companyId,String companyStaffInterviewId,  CompanyStaffInterviewDto companyStaffInterviewDto) throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyStaffInterviewProcessor.updateCompanyStaffInterview() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company staff interview with company id {} and companyStaffInterviewId {}",companyId,companyStaffInterviewId);
		CompanyStaffInterview companyStaffInterview = companyStaffInterviewDao.getCompanyStaffInterviewByCompanyIdAndId(companyId, companyStaffInterviewId);
	
		if (ObjectUtils.isEmpty(companyStaffInterview)) {
			log.error("No Company staff interview with company id {} and companyStaffInterviewId {}",companyId,companyStaffInterviewId);
			throw new NotFoundException("No company staff interview with company id "+companyId+ " and companyStaffInterviewId "+companyStaffInterviewId);
		}
		
		log.info("Updating company staff interview data ");
		companyStaffInterview.setTitle(companyStaffInterviewDto.getTitle());
		companyStaffInterview.setDescription(companyStaffInterviewDto.getDiscription());
		companyStaffInterview.setUpdatedOn(new Date());
		companyStaffInterview.setUpdatedBy("API");
		log.info("Adding all taged interviewee present in request and not in db");
		List<String> listOfTagedUserBeAdded = companyStaffInterviewDto.getTagedInterviewee().stream().filter(tagedUserId -> !companyStaffInterview.getListOfInterviewTagedUser().stream().anyMatch(companyStaffInterviewModel -> companyStaffInterviewModel.getUserId().equalsIgnoreCase(tagedUserId))).collect(Collectors.toList());
		log.info("Removing all taged interviewee present in db and not passed in requet");
		companyStaffInterview.getListOfInterviewTagedUser().removeIf(userInterviewee -> !companyStaffInterviewDto.getTagedInterviewee().stream().anyMatch(tagedUserId -> tagedUserId.equalsIgnoreCase(userInterviewee.getUserId())));
		List<InterviewTagedUser> listOfInterviewTagedUser = listOfTagedUserBeAdded.stream().map(tagedUserId -> new InterviewTagedUser(tagedUserId, new Date(),  new Date(), "API", "API")).collect(Collectors.toList());
		companyStaffInterview.addTagedInterviewee(listOfInterviewTagedUser);
		log.info("Saving CompanyStaffInterview into DB ");
		companyStaffInterviewDto.setCompanyStaffInterviewId(companyStaffInterviewDao.addUpdateCompanyStaffInterview(companyStaffInterview).getId());
		return companyStaffInterviewDto;
	}
	
	@Transactional
	public List<CompanyStaffInterviewDto> getAllCompanyStaffInterview(String userId , String companyId) throws NotFoundException {
		List<CompanyStaffInterviewDto> listOfCompanyStaffInterviewDto = new ArrayList<>();
		log.debug("Inside CompanyStaffInterviewProcessor.getAllCompanyStaffInterview() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		Company company = optionalCompany.get();
		log.info("Getting all company staff interview for company id {}",companyId);
		if (!CollectionUtils.isEmpty(company.getListOfCompanyStaffInterview())) {
			List<StorageDto> listOfStorage = new ArrayList<>();
			try {
				listOfStorage = storageHandler.getStoragesResponse(company.getListOfCompanyStaffInterview().stream().map(CompanyStaffInterview::getId).collect(Collectors.toList()), EntityTypeEnum.COMPANY, EntitySubTypeEnum.COMPANY_STAFF_INTERVIEW, Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
			} catch (NotFoundException | ServiceInvokeException e1) {
				log.error("Exception occured which calling storage servise having exception {}",e1);
			}
			log.info("Grouping storage based on entity id and saving it into map");
			Map<String , List<StorageDto>> mapOfStorage = listOfStorage.stream().collect(Collectors.groupingBy(StorageDto::getEntityId));
			company.getListOfCompanyStaffInterview().stream().forEach(companyStaffInterview -> {
				CompanyStaffInterviewDto companyStaffInterviewDto = new CompanyStaffInterviewDto();
				log.info("Company staff interview found creating response dto");
				companyStaffInterviewDto.setTitle(companyStaffInterview.getTitle());
				companyStaffInterviewDto.setDiscription(companyStaffInterview.getDescription());
				companyStaffInterviewDto.setCompanyStaffInterviewId(companyStaffInterview.getId());
				companyStaffInterviewDto.setTagedInterviewee(companyStaffInterview.getListOfInterviewTagedUser().stream().map(interviewTagedUser -> interviewTagedUser.getUserId()).collect(Collectors.toSet()));
				companyStaffInterviewDto.setListOfStorageDto(mapOfStorage.get(companyStaffInterview.getId()));
				listOfCompanyStaffInterviewDto.add(companyStaffInterviewDto);
			});
		}
		return listOfCompanyStaffInterviewDto;
	}
	
	@Transactional
	public CompanyStaffInterviewDto getCompanyStaffInterview(String userId , String companyId, String staffInterviewId) throws NotFoundException {
		CompanyStaffInterviewDto companyStaffInterviewDto = new CompanyStaffInterviewDto();
		log.debug("Inside CompanyStaffInterviewProcessor.getAllCompanyStaffInterview() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		log.info("Getting company staff interview with company id {} and companyStaffInterviewId {}",companyId,staffInterviewId);
		CompanyStaffInterview companyStaffInterview = companyStaffInterviewDao.getCompanyStaffInterviewByCompanyIdAndId(companyId, staffInterviewId);
	
		if (!ObjectUtils.isEmpty(companyStaffInterview)) {
			log.info("Company staff interview found creating response dto");
			companyStaffInterviewDto.setTitle(companyStaffInterview.getTitle());
			companyStaffInterviewDto.setDiscription(companyStaffInterview.getDescription());
			companyStaffInterviewDto.setCompanyStaffInterviewId(companyStaffInterview.getId());
			companyStaffInterviewDto.setTagedInterviewee(companyStaffInterview.getListOfInterviewTagedUser().stream().map(interviewTagedUser -> interviewTagedUser.getUserId()).collect(Collectors.toSet()));
			log.info("Calling storage service for staff media id {}",staffInterviewId);
			try {
				companyStaffInterviewDto.setListOfStorageDto(storageHandler.getStoragesResponse(staffInterviewId, EntityTypeEnum.COMPANY, EntitySubTypeEnum.COMPANY_STAFF_INTERVIEW,Arrays.asList(PrivacyLevelEnum.PUBLIC.name())));
			} catch (NotFoundException | ServiceInvokeException e) {
				log.error("Exception occured which calling storage serview for entity id {} having exception {}",staffInterviewId,e);
			}
		}
		return companyStaffInterviewDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyStaffinterview(String userId , String companyId, String staffInterviewId) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.debug("Inside CompanyStaffInterviewProcessor.deleteCompanyStaffinterview() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		
		log.info("Getting company staff interview with company id {} and companyStaffInterviewId {}",companyId,staffInterviewId);
		CompanyStaffInterview companyStaffInterview = companyStaffInterviewDao.getCompanyStaffInterviewByCompanyIdAndId(companyId, staffInterviewId);
	
		if (ObjectUtils.isEmpty(companyStaffInterview)) {
			log.error("No Company staff interview with company id {} and companyStaffInterviewId {}",companyId,staffInterviewId);
			throw new NotFoundException("No company staff interview with company id "+companyId+ " and companyStaffInterviewId "+staffInterviewId);
		}
		
		log.info("Deleting storage entity for entity id {}",staffInterviewId);
		storageHandler.deleteStorage(staffInterviewId);
		log.info("Deleting staff interview with id {} and company id {}",staffInterviewId,companyId);
		companyStaffInterviewDao.deleteCompanyStaffInterviewByCompanyIdAndId(companyId, staffInterviewId);
	}
}
