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
import com.yuzee.company.dao.CompanyInternshipDao;
import com.yuzee.company.dto.CompanyInternshipDto;
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
import com.yuzee.company.model.CompanyInternship;
import com.yuzee.company.model.InternshipMember;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyInternshipProcessor {

	@Autowired
	private CompanyInternshipDao companyInternshipDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private StorageHandler storageHandler;
	
	public CompanyInternshipDto addCompanyInternship (String userId , String companyId, CompanyInternshipDto companyInternshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyInternshipProcessor.addCompanyInternship () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Creating company internship model from company internship dto");
		CompanyInternship companyInternship = new CompanyInternship(companyInternshipDto.getTitle(), companyInternshipDto.getDescription(), optionalCompany.get(), new Date(), new Date(), "API", "API");
		log.info("Creating  InternshipMember model from user id passed in request");
		List<InternshipMember> listOfInternshipMember = companyInternshipDto.getÌnternshipMembers().stream().map(internshipMemberUserid -> new InternshipMember(internshipMemberUserid, new Date(),  new Date(), "API", "API")).collect(Collectors.toList());
		companyInternship.addInternshipMember(listOfInternshipMember);
		log.info("Saving CompanyInternship into DB ");
		companyInternshipDto.setCompanyInternshipId(companyInternshipDao.addUpdateCompanyInternship(companyInternship).getId());
		return companyInternshipDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyInternship (String userId , String companyId , String companyInternshipId) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.debug("Inside CompanyInternshipProcessor.deleteCompanyInternship () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company internship for company id {} and company internship id {}",companyId,companyInternshipId);
		CompanyInternship companyInternship = companyInternshipDao.getCompanyInternshipByCompanyIdAndId(companyId, companyInternshipId);
		if (ObjectUtils.isEmpty(companyInternship)) {
			log.error("No company internship for company id {} and company internship id {}",companyId,companyInternshipId);
			throw new NotFoundException("No company internship for company id "+companyId+ " and company internship id "+companyInternshipId);
		}
		
		log.info("Deleting storage for entity id {}",companyInternshipId);
		storageHandler.deleteStorage(companyInternshipId);
		log.info("Deleting company internship for company id {} and id {}",companyId,companyInternshipId);
		companyInternshipDao.deleteCompanyInternshipByCompanyIdAndId(companyId, companyInternshipId);
	}
	
	@Transactional
	public List<CompanyInternshipDto> getAllCompanyInternships(String userId , String companyId) throws NotFoundException {
		List<CompanyInternshipDto> listOfCompanyInternshipDto = new ArrayList<>();
		log.debug("Inside CompanyInternshipProcessor.deleteCompanyInternship () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		log.info("Getting all internship for company id {}",companyId);
		List<CompanyInternship> listOfCompanyInternship = optionalCompany.get().getListOfCompanyInternship();
		if (!CollectionUtils.isEmpty(listOfCompanyInternship)) { 
			log.info("Getting all company internship ids");
			List<String> companyInternshipIds = listOfCompanyInternship.stream().map(CompanyInternship::getId).collect(Collectors.toList());
			log.info("Getting storage entity");
			List<StorageDto> listOfStorageDto = new ArrayList<>();
			try {
				listOfStorageDto =	storageHandler.getStoragesResponse(companyInternshipIds, EntityTypeEnum.COMPANY, Arrays.asList(EntitySubTypeEnum.COMPANY_INTERNSHIP_MEDIA, EntitySubTypeEnum.COMPANY_INTERNSHIP_ATTACHMENT,EntitySubTypeEnum.COVER_PHOTO), isAdmin ? Arrays.asList(PrivacyLevelEnum.PUBLIC.name(), PrivacyLevelEnum.PRIVATE.name()) : Arrays.asList(PrivacyLevelEnum.PUBLIC.name()) );
			} catch (NotFoundException | ServiceInvokeException e) {
				log.error("Exception ocuured while calling storage service {}",e);
			}
			log.info("Segregatting list of storage based on entity id");
			Map<String, List<StorageDto>> mapOfStorageDto = listOfStorageDto.stream().collect(Collectors.groupingBy(StorageDto::getEntityId));
			log.info("Creating company career advice response from model");
			listOfCompanyInternship.stream().forEach(companyInternship -> {
				CompanyInternshipDto companyInternshipDto = new CompanyInternshipDto(companyInternship.getId(), companyInternship.getTitle(), companyInternship.getDescription(), companyInternship.getListOfInternshipMember().stream().map(internshipMember -> internshipMember.getUserId()).collect(Collectors.toSet()),companyInternship.getCreatedOn());
				log.info("Getting storage dto for entity id {}",companyInternship.getId());
				companyInternshipDto.setListOfStorageDto(null != mapOfStorageDto ? mapOfStorageDto.get(companyInternship.getId()) : null);
				listOfCompanyInternshipDto.add(companyInternshipDto);
			});
		}
		return listOfCompanyInternshipDto;
	}
	
	@Transactional
	public CompanyInternshipDto getCompanyInternshipById(String userId, String companyId, String internshipId)
			throws NotFoundException {
		CompanyInternshipDto companyInternshipDto = new CompanyInternshipDto();
		log.debug("Inside CompanyInternshipProcessor.deleteCompanyInternship () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}", companyId);
			throw new NotFoundException("No company found for company id " + companyId);
		}
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		log.info("Getting all internship for company id {}", companyId);
		CompanyInternship companyInternship = companyInternshipDao.getCompanyInternshipByCompanyIdAndId(companyId,
				internshipId);
		if (!ObjectUtils.isEmpty(companyInternship)) {

			log.info("Getting storage entity");
			List<StorageDto> listOfStorageDto = new ArrayList<>();
			try {
				listOfStorageDto = storageHandler.getStoragesResponse(Arrays.asList(internshipId),
						EntityTypeEnum.COMPANY, Arrays.asList(EntitySubTypeEnum.COMPANY_INTERNSHIP_MEDIA,
								EntitySubTypeEnum.COMPANY_INTERNSHIP_ATTACHMENT,EntitySubTypeEnum.COVER_PHOTO),isAdmin ? Arrays.asList(PrivacyLevelEnum.PUBLIC.name(), PrivacyLevelEnum.PRIVATE.name()) : Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
			} catch (NotFoundException | ServiceInvokeException e) {
				log.error("Exception ocuured while calling storage service {}", e);
			}
			companyInternshipDto = new CompanyInternshipDto(companyInternship.getId(), companyInternship.getTitle(),
					companyInternship.getDescription(), companyInternship.getListOfInternshipMember().stream()
							.map(internshipMember -> internshipMember.getUserId()).collect(Collectors.toSet()),companyInternship.getCreatedOn());
			log.info("Getting storage dto for entity id {}", companyInternship.getId());
			companyInternshipDto.setListOfStorageDto(listOfStorageDto);
		}
		return companyInternshipDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public CompanyInternshipDto updateCompanyInternship (String userId , String companyId, String internshipId, CompanyInternshipDto companyInternshipDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.debug("Inside CompanyInternshipProcessor.deleteCompanyInternship () method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}", companyId);
			throw new NotFoundException("No company found for company id " + companyId);
		}

		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting all internship for company id {}", companyId);
		CompanyInternship companyInternship = companyInternshipDao.getCompanyInternshipByCompanyIdAndId(companyId,
				internshipId);
		if (ObjectUtils.isEmpty(companyInternship)) {
			log.error("No company internship found for company id {} and internship id {}",companyId, internshipId);
			throw new NotFoundException("No company internship found for company id "+companyId+  " and internship id " + internshipId);
		}
		
		log.info("Updating company internship model based on dto");
		companyInternship.setTitle(companyInternshipDto.getTitle());
		companyInternship.setDescription(companyInternshipDto.getDescription());
		companyInternship.setUpdatedBy("API");
		companyInternship.setUpdatedOn(new Date());
		log.info("Adding all company internship member present in request and not in db");
		List<String> listOfCompanyUserBeAdded = companyInternshipDto.getÌnternshipMembers().stream().filter(memberUserId -> !companyInternship.getListOfInternshipMember().stream().anyMatch(internshipModel -> internshipModel.getUserId().equalsIgnoreCase(memberUserId))).collect(Collectors.toList());
		log.info("Removing all company internship member in db and not passed in requet");
		companyInternship.getListOfInternshipMember().removeIf(internshipMember -> !companyInternshipDto.getÌnternshipMembers().stream().anyMatch(memberUserId -> memberUserId.equalsIgnoreCase(internshipMember.getUserId())));
		log.info("Creating  InternshipMember model from user id passed in request");
		List<InternshipMember> listOfInternshipMember = listOfCompanyUserBeAdded.stream().map(internshipMemberUserid -> new InternshipMember(internshipMemberUserid, new Date(),  new Date(), "API", "API")).collect(Collectors.toList());
		companyInternship.addInternshipMember(listOfInternshipMember);
		log.info("Saving CompanyInternship into DB ");
		companyInternshipDto.setCompanyInternshipId(companyInternshipDao.addUpdateCompanyInternship(companyInternship).getId());
		return companyInternshipDto;
	}
}
