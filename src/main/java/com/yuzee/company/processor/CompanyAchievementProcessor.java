package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyAchievementDao;
import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dto.CompanyAchievementDto;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.NotificationHandler;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.AchievementTagedUser;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyAchievement;
import com.yuzee.company.utills.DTOUtills;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyAchievementProcessor {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyAchievementDao companyAchievementDao;
	
	@Autowired
	private StorageHandler storageHandler;
	
	@Autowired
	private NotificationHandler notificationHandler;

	@Transactional(rollbackOn = Throwable.class)
	public CompanyAchievementDto addCompanyAchievement(String userId, String companyId , CompanyAchievementDto companyAchievementDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Creating company achievement model from company achievement dto");
		CompanyAchievement companyAchievement = DTOUtills.populateCompanyAchievementModelFromCompanyAchievementDto(companyAchievementDto);
		companyAchievement.setCompany(optionalCompany.get());
		log.info("Saving company achievement into DB");
		companyAchievementDto.setCompanyAchievementId(companyAchievementDao.addUpdateCompanyAchievement(companyAchievement).getId());
		// trigeer notification to all taged user
		return companyAchievementDto;
	}	
	
	@Transactional(rollbackOn = Throwable.class)
	public void deleteCompanyAchievement(String userId , String companyId, String achievementId) throws NotFoundException, ServiceInvokeException, UnauthorizeException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company achievement found for company id {} and achievement id {}",companyId,achievementId);
		CompanyAchievement  companyAchievement =	companyAchievementDao.getCompanyAchievementByCompanyIdAndAchievementId(companyId, achievementId);
		if (ObjectUtils.isEmpty(companyAchievement)) {
			log.error("No company achievement found for company id {} and achievement id {}",companyId,achievementId);
			throw new NotFoundException("No company achievement found for company id "+companyId+ " and achievement id "+achievementId);
		}
		
		log.info("Deleting storage for company id {} and achievement id {}",companyId, achievementId);
		storageHandler.deleteStorage(achievementId);
		log.info("Deleting company achievement with company id {} and achievement id {}",companyId, achievementId);
		companyAchievementDao.deleteCompanyAchievementByCompanyIdAndAchievementId(companyId, achievementId);
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public CompanyAchievementDto updateCompanyAchievement(String userId, String companyId , String achievementId , CompanyAchievementDto companyAchievementDto) throws NotFoundException, UnauthorizeException, BadRequestException {
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		log.info("Getting company achievement found for company id {} and achievement id {}",companyId,achievementId);
		CompanyAchievement  companyAchievement = companyAchievementDao.getCompanyAchievementByCompanyIdAndAchievementId(companyId, achievementId);
		if (ObjectUtils.isEmpty(companyAchievement)) {
			log.error("No company achievement found for company id {} and achievement id {}",companyId,achievementId);
			throw new NotFoundException("No company achievement found for company id "+companyId+ " and achievement id "+achievementId);
		}
		companyAchievement = DTOUtills.updateCompanyAchievementModelFromCompanyAchievementDto(companyAchievementDto, companyAchievement);
		log.info("Saving company achievement into DB");
		companyAchievementDto.setCompanyAchievementId(companyAchievementDao.addUpdateCompanyAchievement(companyAchievement).getId());
		// trigeer notification to all taged user
		return companyAchievementDto;
	} 
	
	@Transactional(rollbackOn = Throwable.class)
	public List<CompanyAchievementDto> getAllCompanyAchievements(String userId , String companyId) throws NotFoundException {
		List<CompanyAchievement> listOfCompanyAchievement = null;
		List<CompanyAchievementDto> listOfCompanyAchievementDto = new ArrayList<>();
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		
		if (isAdmin) {
			listOfCompanyAchievement =  optionalCompany.get().getListOfCompanyAchievement();
		} else {
			listOfCompanyAchievement = companyAchievementDao.getCompanyAchievementByCompanyIdAndPrivacyLevel(companyId, PrivacyLevelEnum.PUBLIC);
		}
	
		if (!CollectionUtils.isEmpty(listOfCompanyAchievement)) {
			List<StorageDto> listOfStorages = new ArrayList<>();
			Map<String, List<StorageDto>> mapOfStorage = null;
			List<String> achievementIds = listOfCompanyAchievement.stream().map(CompanyAchievement::getId).collect(Collectors.toList());
			try {
				listOfStorages = storageHandler.getStoragesResponse(achievementIds, EntityTypeEnum.COMPANY, EntitySubTypeEnum.COMPANY_ACHIEVEMENT_CERTIFICATES, isAdmin ? Arrays.asList(PrivacyLevelEnum.PUBLIC.name() , PrivacyLevelEnum.PRIVATE.name()) : Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
				mapOfStorage = listOfStorages.stream().collect(Collectors.groupingBy(StorageDto::getEntityId, Collectors.toList()));
			
			} catch (NotFoundException | ServiceInvokeException e1) {
				log.error("Exception occured which calling storage for having exception {}",e1);
			}
			final Map<String, List<StorageDto>> finalMapOfStorage = mapOfStorage;
			listOfCompanyAchievement.stream().forEach(companyAchievement -> {
				CompanyAchievementDto companyAchievementDto = DTOUtills.populateCompanyAchievementDtoFromCompanyModel(companyAchievement);
				companyAchievementDto.setListOfStorageDto(finalMapOfStorage.get(companyAchievement.getId()) );
				companyAchievementDto.setTagedUser(companyAchievement.getListOfAchievementTagedUser().stream().map(AchievementTagedUser::getUserId).collect(Collectors.toSet()));
				listOfCompanyAchievementDto.add(companyAchievementDto);
			});
		}
		
		return listOfCompanyAchievementDto;
	}
	
	@Transactional(rollbackOn = Throwable.class)
	public CompanyAchievementDto getCompanyAchievementById(String userId , String companyId , String achievementId) {
		CompanyAchievementDto companyAchievementDto = new CompanyAchievementDto ();
		log.info("Getting company achievement found for company id {} and achievement id {}",companyId,achievementId);
		CompanyAchievement  companyAchievement = companyAchievementDao.getCompanyAchievementByCompanyIdAndAchievementId(companyId, achievementId);
		if (!ObjectUtils.isEmpty(companyAchievement)) {
			log.info("Company achievement found in request creating response DTO");
			companyAchievementDto = DTOUtills.populateCompanyAchievementDtoFromCompanyModel(companyAchievement);
			try {
				companyAchievementDto.setListOfStorageDto(storageHandler.getStoragesResponse(companyAchievementDto.getCompanyAchievementId(), EntityTypeEnum.COMPANY, EntitySubTypeEnum.COMPANY_ACHIEVEMENT_CERTIFICATES, Arrays.asList(PrivacyLevelEnum.PUBLIC.name() , PrivacyLevelEnum.PRIVATE.name())));
			} catch (NotFoundException | ServiceInvokeException e) {
				log.error("Exception occured which calling storage for entity id {} having exception {}",companyAchievementDto.getCompanyAchievementId(),e);
			}
		}
		return companyAchievementDto;
	}
}