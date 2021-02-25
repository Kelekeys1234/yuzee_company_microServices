package com.yuzee.company.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.dao.CompanyPartnerPrimaryDetailDao;
import com.yuzee.company.dto.CompanyPartnerDetailDto;
import com.yuzee.company.dto.CompanyPartnerDetailsResponseDto;
import com.yuzee.company.dto.CompanyPartnerResponseDto;
import com.yuzee.company.dto.InstituteBasicInfoDto;
import com.yuzee.company.dto.StorageDto;
import com.yuzee.company.enumeration.EntitySubTypeEnum;
import com.yuzee.company.enumeration.EntityType;
import com.yuzee.company.enumeration.EntityTypeEnum;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.ServiceInvokeException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.handler.InstituteHandler;
import com.yuzee.company.handler.StorageHandler;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyPartner;
import com.yuzee.company.model.CompanyPartnerPrimaryDetail;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyPartnerProcessor {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CompanyPartnerPrimaryDetailDao companyPartnerPrimaryDetailDao;
	
	@Autowired
	private StorageHandler storageHandler;
	
	@Autowired
	private InstituteHandler instituteHandler;
	
	public void addUpdateCompanyPartner(String userId , String companyId, CompanyPartnerDetailDto companyPartnerDetailDto) throws NotFoundException, UnauthorizeException {
		List<CompanyPartner> listOfCompanyPartnerTobeSaved = new ArrayList<>();
		
		log.debug("Inside CompanyPartnerProcessor.addUpdateCompanyPartner() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);
	
		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}",companyId);
			throw new NotFoundException("No company found for company id "+companyId);
		}
		
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		Company company = optionalCompany.get();
		
		log.info("Getting all exsisting company partner");
		CompanyPartnerPrimaryDetail companyPartnerPrimaryDetail = company
				.getCompanyPartnerPrimaryDetail();
		
		if (!ObjectUtils.isEmpty(companyPartnerPrimaryDetail)) {
			companyPartnerPrimaryDetail
			.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyPartnerDetailDto.getPrivacyLevel()));
			companyPartnerPrimaryDetail.setUpdatedOn(new Date());
			companyPartnerPrimaryDetail.setUpdatedBy("API");
			final CompanyPartnerPrimaryDetail finalCompanyPartnerPrimaryDetail = companyPartnerPrimaryDetail;
			log.info("Getting exsisting company partner form db");
			List<CompanyPartner> listOfExsistingCompanyPartner = companyPartnerPrimaryDetail
					.getListOfCompanyPartner();
			if (!CollectionUtils.isEmpty(listOfExsistingCompanyPartner)) {
				log.info("Adding all institute partner presend in request but not present in db");
				Set<String> setOfInstitutePartnerId = companyPartnerDetailDto.getListOfInstitutePartnerId().stream().filter(instituteId -> !listOfExsistingCompanyPartner.stream().anyMatch(companyPartner -> instituteId.equalsIgnoreCase(companyPartner.getEntityId()))).collect(Collectors.toSet());
				log.info("Adding all company partner presend in request but not present in db");
				Set<String> setOfCompanyPartnerId = companyPartnerDetailDto.getListOfCompanyPartnerId().stream().filter(compId -> !listOfExsistingCompanyPartner.stream().anyMatch(companyPartner -> compId.equalsIgnoreCase(companyPartner.getEntityId()))).collect(Collectors.toSet());
				log.info("Removing all institute partner in db but not passed in request");
				listOfExsistingCompanyPartner.removeIf(comanyParner -> !companyPartnerDetailDto.getListOfInstitutePartnerId().stream().anyMatch(instituteUd -> instituteUd.equalsIgnoreCase(comanyParner.getEntityId())));
				log.info("Removing all company partner in db but not passed in request");
				listOfExsistingCompanyPartner.removeIf(comanyParner -> !companyPartnerDetailDto.getListOfCompanyPartnerId().stream().anyMatch(compId -> compId.equalsIgnoreCase(comanyParner.getEntityId())));	
				log.info("Creating all institute partner id passed in request to model ");
				listOfCompanyPartnerTobeSaved.addAll(setOfInstitutePartnerId.stream().map(instituteId -> new CompanyPartner(instituteId, EntityType.INSTITUTE, new Date(), new Date(), "API", "API",finalCompanyPartnerPrimaryDetail)).collect(Collectors.toList()));
				log.info("Creating all company partner id passed in request to model ");
				listOfCompanyPartnerTobeSaved.addAll(setOfCompanyPartnerId.stream().map(compId -> new CompanyPartner(compId, EntityType.COMPANY, new Date(), new Date(), "API", "API",finalCompanyPartnerPrimaryDetail)).collect(Collectors.toList()));
				finalCompanyPartnerPrimaryDetail.addListOfCompanyPartner(listOfCompanyPartnerTobeSaved);
				log.info("Adding / Updating company partner primary details");
				companyPartnerPrimaryDetailDao.addUpdateCompanyPartnerPrimaryDetail(finalCompanyPartnerPrimaryDetail);
			}
			
		} else {
			log.info("Creating new company preferred course details model");
			CompanyPartnerPrimaryDetail companyPartnerPrimaryDetailToBeAdded = new CompanyPartnerPrimaryDetail(
					PrivacyLevelEnum.valueOf(companyPartnerDetailDto.getPrivacyLevel()), new Date(),
					new Date(), "API", "API", company);
			log.info("Creating all institute partner id passed in request to model ");
			listOfCompanyPartnerTobeSaved.addAll(companyPartnerDetailDto.getListOfInstitutePartnerId().stream().map(instituteId -> new CompanyPartner(instituteId, EntityType.INSTITUTE, new Date(), new Date(), "API", "API",companyPartnerPrimaryDetailToBeAdded)).collect(Collectors.toList()));
			log.info("Creating all company partner id passed in request to model ");
			listOfCompanyPartnerTobeSaved.addAll(companyPartnerDetailDto.getListOfCompanyPartnerId().stream().map(compId -> new CompanyPartner(compId, EntityType.COMPANY, new Date(), new Date(), "API", "API",companyPartnerPrimaryDetailToBeAdded)).collect(Collectors.toList()));
			companyPartnerPrimaryDetailToBeAdded.addListOfCompanyPartner(listOfCompanyPartnerTobeSaved);
			log.info("Adding / Updating company partner primary details");
			companyPartnerPrimaryDetailDao.addUpdateCompanyPartnerPrimaryDetail(companyPartnerPrimaryDetailToBeAdded);
		}
	
	}
	
	public CompanyPartnerDetailsResponseDto getAllCompanyPartner(String userId, String companyId)
			throws NotFoundException, ServiceInvokeException {
		Map<String, List<StorageDto>> mapOfLogo = new HashMap<>();
		CompanyPartnerDetailsResponseDto companyPartnerDetailsResponseDto = new CompanyPartnerDetailsResponseDto();
		List<CompanyPartnerResponseDto> listOfCompanyPartnerResponseDto = new ArrayList<>();
		log.debug("Inside CompanyPartnerProcessor.getAllCompanyPartner() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);

		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}", companyId);
			throw new NotFoundException("No company found for company id " + companyId);
		}
		Company company = optionalCompany.get();
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());
		CompanyPartnerPrimaryDetail companyPartnerPrimaryDetail = company.getCompanyPartnerPrimaryDetail();
			if (!ObjectUtils.isEmpty(companyPartnerPrimaryDetail)) {
				if (isAdmin) {
					companyPartnerDetailsResponseDto.setPrivacyLevel(companyPartnerPrimaryDetail.getPrivacyLevel().name());
					log.info("Segregatting partner based on entity type");
					Map<EntityType, Set<String>> partners = companyPartnerPrimaryDetail.getListOfCompanyPartner().stream()
							.collect(Collectors.groupingBy(CompanyPartner::getEntityType,
									Collectors.mapping(CompanyPartner::getEntityId, Collectors.toSet())));
					log.info("Getting all company details for company ids {}", partners.get(EntityType.COMPANY));
					List<Company> listOfPartnerCompany = companyDao.getAllCompanyByIds(partners.get(EntityType.COMPANY));
					log.info("Getting all company logos for company ids {}", partners.get(EntityType.COMPANY));
					List<StorageDto> listOfComapnyLogo = storageHandler.getStoragesResponse(
							Lists.newArrayList(partners.get(EntityType.COMPANY)), EntityTypeEnum.COMPANY,
							EntitySubTypeEnum.LOGO, Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
					if (!CollectionUtils.isEmpty(listOfComapnyLogo)) {
						log.info("Grouping all company logo based on company id");
						mapOfLogo
								.putAll(listOfComapnyLogo.stream().collect(Collectors.groupingBy(StorageDto::getEntityId)));
					}
					log.info("Creating CompanyPartnerResponseDto for comapnys  ");
					listOfPartnerCompany.stream().forEach(companyPartner -> {
						listOfCompanyPartnerResponseDto.add(new CompanyPartnerResponseDto(companyPartner.getId(),
								EntityType.COMPANY.name(), companyPartner.getCompanyName(),
								(null != mapOfLogo && null != mapOfLogo.get(companyPartner.getId()))
										? mapOfLogo.get(companyPartner.getId()).get(0).getFileURL()
										: null));
					});

					log.info("Getting all institute partner details for institute ids {}",
							partners.get(EntityType.INSTITUTE));
					partners.get(EntityType.INSTITUTE).stream().forEach(instituteId -> {
						try {
							InstituteBasicInfoDto instituteBasicInfoDto = instituteHandler.getInstituteById(instituteId);
							listOfCompanyPartnerResponseDto.add(new CompanyPartnerResponseDto(instituteId,
									EntityType.INSTITUTE.name(), instituteBasicInfoDto.getNameOfUniversity(),
									instituteBasicInfoDto.getInstituteLogoPath()));
						} catch (ServiceInvokeException e) {
							log.error("Exception occured {}", e);
						}
					});
					companyPartnerDetailsResponseDto.setListOfCompanyPartnerResponseDto(listOfCompanyPartnerResponseDto);
				} else {
					if (companyPartnerPrimaryDetail.getPrivacyLevel().equals(PrivacyLevelEnum.PUBLIC)) {
						companyPartnerDetailsResponseDto.setPrivacyLevel(companyPartnerPrimaryDetail.getPrivacyLevel().name());
						log.info("Segregatting partner based on entity type");
						Map<EntityType, Set<String>> partners = companyPartnerPrimaryDetail.getListOfCompanyPartner().stream()
								.collect(Collectors.groupingBy(CompanyPartner::getEntityType,
										Collectors.mapping(CompanyPartner::getEntityId, Collectors.toSet())));
						log.info("Getting all company details for company ids {}", partners.get(EntityType.COMPANY));
						List<Company> listOfPartnerCompany = companyDao.getAllCompanyByIds(partners.get(EntityType.COMPANY));
						log.info("Getting all company logos for company ids {}", partners.get(EntityType.COMPANY));
						List<StorageDto> listOfComapnyLogo = storageHandler.getStoragesResponse(
								Lists.newArrayList(partners.get(EntityType.COMPANY)), EntityTypeEnum.COMPANY,
								EntitySubTypeEnum.LOGO, Arrays.asList(PrivacyLevelEnum.PUBLIC.name()));
						if (!CollectionUtils.isEmpty(listOfComapnyLogo)) {
							log.info("Grouping all company logo based on company id");
							mapOfLogo
									.putAll(listOfComapnyLogo.stream().collect(Collectors.groupingBy(StorageDto::getEntityId)));
						}
						log.info("Creating CompanyPartnerResponseDto for comapnys  ");
						listOfPartnerCompany.stream().forEach(companyPartner -> {
							listOfCompanyPartnerResponseDto.add(new CompanyPartnerResponseDto(companyPartner.getId(),
									EntityType.COMPANY.name(), companyPartner.getCompanyName(),
									(null != mapOfLogo && null != mapOfLogo.get(companyPartner.getId()))
											? mapOfLogo.get(companyPartner.getId()).get(0).getFileURL()
											: null));
						});

						log.info("Getting all institute partner details for institute ids {}",
								partners.get(EntityType.INSTITUTE));
						partners.get(EntityType.INSTITUTE).stream().forEach(instituteId -> {
							try {
								InstituteBasicInfoDto instituteBasicInfoDto = instituteHandler.getInstituteById(instituteId);
								listOfCompanyPartnerResponseDto.add(new CompanyPartnerResponseDto(instituteId,
										EntityType.INSTITUTE.name(), instituteBasicInfoDto.getNameOfUniversity(),
										instituteBasicInfoDto.getInstituteLogoPath()));
							} catch (ServiceInvokeException e) {
								log.error("Exception occured {}", e);
							}
						});
					}
				} 
				
			}
		return companyPartnerDetailsResponseDto;
	}
}
