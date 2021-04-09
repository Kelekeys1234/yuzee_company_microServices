package com.yuzee.company.utills;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.yuzee.company.dto.CompanyAboutUsDto;
import com.yuzee.company.dto.CompanyAchievementDto;
import com.yuzee.company.dto.CompanyAwardAndCertificationDto;
import com.yuzee.company.dto.CompanyContactDetailsDto;
import com.yuzee.company.dto.CompanyDto;
import com.yuzee.company.dto.CompanyLocationDto;
import com.yuzee.company.dto.CompanyScholarshipDto;
import com.yuzee.company.dto.CompanySpecialityDto;
import com.yuzee.company.dto.CompanyWorkingHoursDto;
import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;
import com.yuzee.company.enumeration.CompanyType;
import com.yuzee.company.enumeration.EntityType;
import com.yuzee.company.enumeration.IndustryType;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.enumeration.ScholarshipCoverage;
import com.yuzee.company.enumeration.ScholarshipValidity;
import com.yuzee.company.model.AchievementTagedUser;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyAchievement;
import com.yuzee.company.model.CompanyAwardAndCertification;
import com.yuzee.company.model.CompanyContactDetail;
import com.yuzee.company.model.CompanyLocation;
import com.yuzee.company.model.CompanyScholarship;
import com.yuzee.company.model.CompanyWorkingHours;
import com.yuzee.company.model.ScholarshipEligibleCountry;
import com.yuzee.company.model.ScholarshipEligibleEntity;
import com.yuzee.company.model.ScholarshipEligibleLevel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DTOUtills {
	
	private DTOUtills () {}

	private static  DateFormat formatter = new SimpleDateFormat("hh:mm a");
	
	public static Company initiateCompanyModelFromCompanyDto(CompanyDto companyDto) {
		Company company = new Company();
		company.setCompanyName(companyDto.getCompanyName());
		company.setAboutUs(companyDto.getDescription());
		company.setTagLine(companyDto.getTagLine());
		company.setIndustry(companyDto.getIndustry());
		company.setIndustryType(IndustryType.valueOf(companyDto.getIndustryType()));
		company.setYearFounder(companyDto.getYearFounded());
		company.setCompanyType(CompanyType.valueOf(companyDto.getCompanyType()));
		companyDto.getLocations().stream().forEach(companyLocationDto -> {
			CompanyLocation companyLocation = new CompanyLocation(PrivacyLevelEnum.valueOf(companyLocationDto.getPrivacyLevel()) ,companyLocationDto.getCampusName(), companyLocationDto.getAddress(), companyLocationDto.getCityName(), companyLocationDto.getStateName(), companyLocationDto.getCountryName(), companyLocationDto.getPostalCode(), companyLocationDto.getLatitude(), companyLocationDto.getLongitude(), companyLocationDto.getIsPrimary(), new Date(), new Date(), "API", "API");
			companyLocationDto.getListOfCompanyContactDetailsDto().stream().forEach(companyContactDetailsDto -> {
				companyLocation.addContactDetails(new CompanyContactDetail(companyContactDetailsDto.getKey(), companyContactDetailsDto.getValue(), new Date(), new Date(), "API", "API"));
			});
			companyLocationDto.getListOfCompanyWorkingHoursDto().stream().forEach(companyWorkingHoursDto -> {
				try {
					companyLocation.addCompanyWorkingHours(new CompanyWorkingHours(companyWorkingHoursDto.getDayOfWeek(), new Time(formatter.parse(companyWorkingHoursDto.getOpenAt()).getTime()) ,new Time(formatter.parse(companyWorkingHoursDto.getCloseAt()).getTime()),companyWorkingHoursDto.getIsOffDay() ,new Date(), new Date(), "API", "API"));
				} catch (ParseException e) {
					log.error("Excaption occured {}",e);
				}
			});
			company.addCompanyLocation(companyLocation);
		});
		
		company.setUpdatedBy("API");
		company.setUpdatedOn(new Date());
		company.setCreatedOn(new Date());
		return company;
	}
	
	public static Company updateCompanyModelFromCompanyDto (Company company, CompanyDto companyDto) {
		company.setCompanyName(companyDto.getCompanyName());
		company.setAboutUs(companyDto.getDescription());
		company.setTagLine(companyDto.getTagLine());
		company.setIndustry(companyDto.getIndustry());
		company.setIndustryType(IndustryType.valueOf(companyDto.getIndustryType()));
		company.setYearFounder(companyDto.getYearFounded());
		company.setCompanyType(CompanyType.valueOf(companyDto.getCompanyType()));
		company.setUpdatedBy("API");
		company.setUpdatedOn(new Date());
		return company;
	}
	
	public static CompanyDto initiateCompanyDtoFromCompanyModel (Company company) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setId(company.getId());
		companyDto.setCompanyName(company.getCompanyName());
		companyDto.setDescription(company.getAboutUs());
		companyDto.setTagLine(company.getTagLine());
		companyDto.setIndustry(company.getIndustry());
		companyDto.setIndustryType(company.getIndustryType().name());
		companyDto.setYearFounded(company.getYearFounder());
		company.getListOfCompanyLocation().stream().forEach(companyLocation -> {
			CompanyLocationDto companyLocationDto = new CompanyLocationDto(companyLocation.getId(), companyLocation.getName(), companyLocation.getCity(), companyLocation.getState(), companyLocation.getCountry(), companyLocation.getPostalCode(), companyLocation.getAddress(), companyLocation.getLatitude(), companyLocation.getLongitude(), companyLocation.getIsPrimary(), companyLocation.getPrivacyLevel().name());
			companyLocationDto.setListOfCompanyContactDetailsDto(companyLocation.getListOfCompanyContactDetail().stream().map(companyContactDetail -> new CompanyContactDetailsDto(companyContactDetail.getId(), companyContactDetail.getKey(), companyContactDetail.getValue())).collect(Collectors.toList()));
			companyLocationDto.setListOfCompanyWorkingHoursDto(companyLocation.getListOfCompanyWorkingHours().stream().map(companyWorkingHour -> new CompanyWorkingHoursDto(companyWorkingHour.getId(), companyWorkingHour.getWeekDay(), companyWorkingHour.getIsOffDay(), companyWorkingHour.getOpeningAt().toString(), companyWorkingHour.getClosingAt().toString())).collect(Collectors.toList()));
			companyDto.getLocations().add(companyLocationDto);
		});
		companyDto.setSpecialitys(company.getListOfCompanySpeciality().stream().map(companySpeciality -> new CompanySpecialityDto(companySpeciality.getId(), companySpeciality.getSpeciality().getId(),companySpeciality.getSpeciality().getSpecialityName())).collect(Collectors.toList()));
		return companyDto;
	}
	
	public static Company populateCompanyModelFromCompanyAboutUsDto (Company company , CompanyAboutUsDto companyAboutUsDto) {
		company.setAboutUs(companyAboutUsDto.getDescription());
		company.setIndustry(companyAboutUsDto.getIndustry());
		company.setIndustryType(IndustryType.valueOf(companyAboutUsDto.getIndustryType()));
		company.setYearFounder(companyAboutUsDto.getYearFounded());
		company.setUpdatedBy("API");
		company.setUpdatedOn(new Date());
		return company;
	}
	
	public static CompanyAboutUsDto populateCompanyAboutUsDtoFromCompanyModel(Company company) {
		CompanyAboutUsDto companyAboutUsDto = new CompanyAboutUsDto();
		companyAboutUsDto.setDescription(company.getAboutUs());
		companyAboutUsDto.setIndustry(company.getIndustry());
		companyAboutUsDto.setIndustryType(company.getIndustryType().toString());
		companyAboutUsDto.setYearFounded(company.getYearFounder());
		companyAboutUsDto.setSpeciality(company.getListOfCompanySpeciality().stream().map(companySpeciality -> new CompanySpecialityDto(companySpeciality.getId(), companySpeciality.getSpeciality().getId(),companySpeciality.getSpeciality().getSpecialityName())).collect(Collectors.toList()));
		return companyAboutUsDto;
		
	}
	
	public static CompanyLocation populateCompanyLocationModelFromCompanyLocationDto(CompanyLocationDto companyLocationDto) {
		CompanyLocation companyLocation = new CompanyLocation(PrivacyLevelEnum.valueOf(companyLocationDto.getPrivacyLevel()) ,companyLocationDto.getCampusName(), companyLocationDto.getAddress(), companyLocationDto.getCityName(), companyLocationDto.getStateName(), companyLocationDto.getCountryName(), companyLocationDto.getPostalCode(), companyLocationDto.getLatitude(), companyLocationDto.getLongitude(), companyLocationDto.getIsPrimary(), new Date(), new Date(), "API", "API");
		companyLocationDto.getListOfCompanyContactDetailsDto().stream().forEach(companyContactDetailsDto -> {
			companyLocation.addContactDetails(new CompanyContactDetail(companyContactDetailsDto.getKey(), companyContactDetailsDto.getValue(), new Date(), new Date(), "API", "API"));
		});
		companyLocationDto.getListOfCompanyWorkingHoursDto().stream().forEach(companyWorkingHoursDto -> {
			try {
				companyLocation.addCompanyWorkingHours(new CompanyWorkingHours(companyWorkingHoursDto.getDayOfWeek(), new Time(formatter.parse(companyWorkingHoursDto.getOpenAt()).getTime()) ,new Time(formatter.parse(companyWorkingHoursDto.getCloseAt()).getTime()),companyWorkingHoursDto.getIsOffDay() ,new Date(), new Date(), "API", "API"));
			} catch (ParseException e) {
				log.error("Excaption occured {}",e);
			}
		});
		return companyLocation;
	}
	
	
	public static CompanyLocationDto populateCompanyLocationDtoFromCompanyLocationModel (CompanyLocation companyLocation) {
		CompanyLocationDto companyLocationDto = new CompanyLocationDto(companyLocation.getId(), companyLocation.getName(), companyLocation.getCity(), companyLocation.getState(), companyLocation.getCountry(), companyLocation.getPostalCode(), companyLocation.getAddress(), companyLocation.getLatitude(), companyLocation.getLongitude(), companyLocation.getIsPrimary(), companyLocation.getPrivacyLevel().name());
		companyLocationDto.setListOfCompanyContactDetailsDto(companyLocation.getListOfCompanyContactDetail().stream().map(companyContactDetail -> new CompanyContactDetailsDto(companyContactDetail.getId(), companyContactDetail.getKey(), companyContactDetail.getValue())).collect(Collectors.toList()));
		companyLocationDto.setListOfCompanyWorkingHoursDto(companyLocation.getListOfCompanyWorkingHours().stream().map(companyWorkingHour -> new CompanyWorkingHoursDto(companyWorkingHour.getId(), companyWorkingHour.getWeekDay(), companyWorkingHour.getIsOffDay(), companyWorkingHour.getOpeningAt().toString(), companyWorkingHour.getClosingAt().toString())).collect(Collectors.toList()));
		return companyLocationDto;
	}
	
	public static CompanyLocation updateCompanyLocationModelFromCompanyLocationDto (CompanyLocation companyLocation, CompanyLocationDto companyLocationDto) {
		List<CompanyContactDetailsDto> companyContactDetailsDtos = new ArrayList<>();
		List<CompanyWorkingHoursDto> companyWorkingHoursDtos = new ArrayList<>();
		companyLocation.setName(companyLocationDto.getCampusName());
		companyLocation.setAddress(companyLocationDto.getAddress());
		companyLocation.setCity(companyLocationDto.getCityName());
		companyLocation.setState(companyLocationDto.getStateName());
		companyLocation.setCountry(companyLocationDto.getCountryName());
		companyLocation.setPostalCode(companyLocationDto.getPostalCode());
		companyLocation.setLatitude(companyLocationDto.getLatitude());
		companyLocation.setLongitude(companyLocationDto.getLongitude());
		companyLocation.setIsPrimary(companyLocationDto.getIsPrimary());
		companyLocation.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyLocationDto.getPrivacyLevel()));
		companyLocation.setUpdatedBy("API");
		companyLocation.setUpdatedOn(new Date());
		if (!CollectionUtils.isEmpty(companyLocation.getListOfCompanyContactDetail())) {
			Map<String, CompanyContactDetailsDto> mapOfCompanyContactDeatils = companyLocationDto.getListOfCompanyContactDetailsDto().stream().collect(Collectors.toMap(CompanyContactDetailsDto::getKey, e->e));
			log.info("Removing all company contact details which is not present in lastest list passed in request");
			companyLocation.getListOfCompanyContactDetail().removeIf(companyContactDetails -> !companyLocationDto.getListOfCompanyContactDetailsDto().stream().anyMatch(companyContactDetailsDto -> companyContactDetails.getKey().equals(companyContactDetailsDto.getKey())));
			log.info("Adding additional company contact details found in request but not present in exsiting company contact details");
			companyContactDetailsDtos = companyLocationDto.getListOfCompanyContactDetailsDto().stream().filter(companyContactDetailsDto -> companyLocation.getListOfCompanyContactDetail().stream().anyMatch(companyContactDetails -> !companyContactDetails.getKey().equalsIgnoreCase(companyContactDetailsDto.getKey()))).collect(Collectors.toList());
			log.info("Updating value of already added company contact details");
			List<CompanyContactDetail> contactDetailsToBeUpdated =  companyLocation.getListOfCompanyContactDetail().stream().filter(companyContactDetails -> companyLocationDto.getListOfCompanyContactDetailsDto().stream().anyMatch(companyContactDetailsDto -> companyContactDetails.getKey().equalsIgnoreCase(companyContactDetailsDto.getKey()))).collect(Collectors.toList());
			contactDetailsToBeUpdated.stream().forEach(companyContactDetail -> {
				companyContactDetail.setValue(mapOfCompanyContactDeatils.get(companyContactDetail.getKey()).getValue());
				companyContactDetail.setUpdatedOn(new Date());
			});
		} else {
			log.info("Adding all company contact details");
			companyContactDetailsDtos = companyLocationDto.getListOfCompanyContactDetailsDto();
		}
	
		if (!CollectionUtils.isEmpty(companyLocation.getListOfCompanyWorkingHours())) {
			Map<String, CompanyWorkingHoursDto> mapOfCompanyWorkingHoursDto = companyLocationDto.getListOfCompanyWorkingHoursDto().stream().collect(Collectors.toMap(CompanyWorkingHoursDto::getDayOfWeek, e->e));
			log.info("Removing all company working hours details which is not present in lastest request");
			companyLocation.getListOfCompanyWorkingHours().removeIf(companyWorkingHour -> !companyLocationDto.getListOfCompanyWorkingHoursDto().stream().anyMatch(companyWorkingHoursDto -> companyWorkingHour.getWeekDay().equals(companyWorkingHoursDto.getDayOfWeek())));
			log.info("Adding additional company working hours found in request but not present in exsiting request");
			companyWorkingHoursDtos = companyLocationDto.getListOfCompanyWorkingHoursDto().stream().filter(companyWorkingHoursDto -> companyLocation.getListOfCompanyWorkingHours().stream().anyMatch(companyWorkingHour -> !companyWorkingHour.getWeekDay().equalsIgnoreCase(companyWorkingHoursDto.getDayOfWeek()))).collect(Collectors.toList());
			log.info("Updating value of already added company working hours");
			List<CompanyWorkingHours> companyWorkingHoursToBeUpdated =  companyLocation.getListOfCompanyWorkingHours().stream().filter(companyWorkingHours -> companyLocationDto.getListOfCompanyWorkingHoursDto().stream().anyMatch(companyWorkingHoursDto -> companyWorkingHours.getWeekDay().equalsIgnoreCase(companyWorkingHoursDto.getDayOfWeek()))).collect(Collectors.toList());
			companyWorkingHoursToBeUpdated.stream().forEach(companyWorkingHour -> {
				CompanyWorkingHoursDto companyWorkingHoursDto =mapOfCompanyWorkingHoursDto.get(companyWorkingHour.getWeekDay());
				try {
					companyWorkingHour.setOpeningAt(new Time(formatter.parse(companyWorkingHoursDto.getOpenAt()).getTime()));
					companyWorkingHour.setClosingAt(new Time(formatter.parse(companyWorkingHoursDto.getCloseAt()).getTime()));
				} catch (ParseException e1) {
					log.error("Exception occured",e1);
				}
				
				companyWorkingHour.setIsOffDay(companyWorkingHoursDto.getIsOffDay());
			});
		} else {
			log.info("Adding all company working hours dto");
			companyWorkingHoursDtos = companyLocationDto.getListOfCompanyWorkingHoursDto();
		}
		
		companyContactDetailsDtos.stream().forEach(companyContactDetailsDto -> {
			companyLocation.addContactDetails(new CompanyContactDetail(companyContactDetailsDto.getKey(), companyContactDetailsDto.getValue(), new Date(), new Date(), "API", "API"));
		});
		companyWorkingHoursDtos.stream().forEach(companyWorkingHoursDto -> {
			try {
				companyLocation.addCompanyWorkingHours(new CompanyWorkingHours(companyWorkingHoursDto.getDayOfWeek(), new Time(formatter.parse(companyWorkingHoursDto.getOpenAt()).getTime()) ,new Time(formatter.parse(companyWorkingHoursDto.getCloseAt()).getTime()),companyWorkingHoursDto.getIsOffDay() ,new Date(), new Date(), "API", "API"));
			} catch (ParseException e) {
				
			}
		});
		return companyLocation;
	}
	
	
	public static CompanyAchievement populateCompanyAchievementModelFromCompanyAchievementDto(CompanyAchievementDto companyAchievementDto) {
		CompanyAchievement companyAchievement = new CompanyAchievement(PrivacyLevelEnum.valueOf(companyAchievementDto.getPrivacyLevel()) ,companyAchievementDto.getAchievementName(), companyAchievementDto.getAchievementDescription(), companyAchievementDto.getAchievementStartDate(), new Date(),  new Date(), "API", "API");
		if (!CollectionUtils.isEmpty(companyAchievementDto.getTagedUser()) ) {
			log.info("Adding achievement taged user to comany achievement");
			companyAchievementDto.getTagedUser().forEach(tagedUser -> {
				AchievementTagedUser achievementTagedUser = new AchievementTagedUser(tagedUser, new Date(), new Date(), "API", "API");
				companyAchievement.addAchievementTagedUser(achievementTagedUser);
			});
		}
		return companyAchievement;
	}
	
	public static CompanyAchievement updateCompanyAchievementModelFromCompanyAchievementDto(CompanyAchievementDto companyAchievementDto, CompanyAchievement companyAchievement) {
		companyAchievement.setTitle(companyAchievementDto.getAchievementName());
		companyAchievement.setDescription(companyAchievementDto.getAchievementDescription());
		companyAchievement.setAchievedDate(companyAchievementDto.getAchievementStartDate());
		companyAchievement.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyAchievementDto.getPrivacyLevel()));
		companyAchievement.setUpdatedBy("API");
		companyAchievement.setUpdatedOn(new Date ());
		if (!CollectionUtils.isEmpty(companyAchievement.getListOfAchievementTagedUser())) {
			Set<String> userIdTobeAdded = companyAchievementDto.getTagedUser().stream().filter(mentionedUserId -> companyAchievement.getListOfAchievementTagedUser().stream().anyMatch(achievementTagedUser -> !achievementTagedUser.getUserId().equals(mentionedUserId))).collect(Collectors.toSet());
			userIdTobeAdded.forEach(tagedUser -> {
				AchievementTagedUser achievementTagedUser = new AchievementTagedUser(tagedUser, new Date(), new Date(), "API", "API");
				companyAchievement.addAchievementTagedUser(achievementTagedUser);
			});
			companyAchievement.getListOfAchievementTagedUser().removeIf(tagedUser -> !companyAchievementDto.getTagedUser().stream().anyMatch(tagedUserIdDto -> tagedUserIdDto.equals(tagedUser.getUserId())));	
		} else {
			companyAchievementDto.getTagedUser().forEach(tagedUser -> {
				AchievementTagedUser achievementTagedUser = new AchievementTagedUser(tagedUser, new Date(), new Date(), "API", "API");
				companyAchievement.addAchievementTagedUser(achievementTagedUser);
			});
		}
		return companyAchievement;
	}
	
	public static CompanyAchievementDto populateCompanyAchievementDtoFromCompanyModel (CompanyAchievement companyAchievement) {
		CompanyAchievementDto companyAchievementDto = new CompanyAchievementDto();
		companyAchievementDto.setPrivacyLevel(companyAchievement.getPrivacyLevel().name());
		companyAchievementDto.setCompanyAchievementId(companyAchievement.getId());
		companyAchievementDto.setAchievementName(companyAchievement.getTitle());
		companyAchievementDto.setAchievementDescription(companyAchievement.getDescription());
		companyAchievementDto.setAchievementStartDate(companyAchievement.getAchievedDate());
		companyAchievementDto.setTagedUser(companyAchievement.getListOfAchievementTagedUser().stream().filter(achievementTagedUser -> achievementTagedUser.getAchievementTagedUserRequestStatus().equals(AchievementTagedUserRequestStatus.APPROVED)).map(achievementTagedUser -> achievementTagedUser.getUserId()).collect(Collectors.toSet()));
		return companyAchievementDto;
	}
	
	public static CompanyAwardAndCertification populateCompanyAwardAndCertificationFromCompanyAwardAndCertificationDto (CompanyAwardAndCertificationDto companyAwardAndCertificationDto) {
		return new CompanyAwardAndCertification(companyAwardAndCertificationDto.getTitle(), companyAwardAndCertificationDto.getDescription(), new Date (), new Date (), "API", "API", PrivacyLevelEnum.valueOf(companyAwardAndCertificationDto.getPrivacyLevel()));
	}
	
	public static CompanyAwardAndCertificationDto populateCompanyAwardAndCertificationDtoFromCompanyAwardAndCertificationModel (CompanyAwardAndCertification companyAwardAndCertification) {
		return new CompanyAwardAndCertificationDto(companyAwardAndCertification.getId(), companyAwardAndCertification.getTitle(), companyAwardAndCertification.getDescription(),companyAwardAndCertification.getPrivacyLevel().name());
	}
	
	public static CompanyScholarship populateCompanyScholarshipFromCompanyScholarshipDto (CompanyScholarshipDto companyScholarshipDto) {
		CompanyScholarship companyScholarship = new CompanyScholarship();
		companyScholarship.setTitle(companyScholarshipDto.getTitle());
		companyScholarship.setDescription(companyScholarshipDto.getDescription());
		companyScholarship.setScholarshipValidity(ScholarshipValidity.valueOf(companyScholarshipDto.getValidity()));
		companyScholarship.setScholarshipCoverage(ScholarshipCoverage.valueOf(companyScholarshipDto.getModeOfCovergae()));
		if (companyScholarshipDto.getModeOfCovergae().equals(ScholarshipCoverage.AMOUNT.name()))  {
			companyScholarship.setScholarshipAmount(companyScholarshipDto.getAmount());
			companyScholarship.setCurrency(companyScholarshipDto.getCurrency());
		} else if (companyScholarshipDto.getModeOfCovergae().equals(ScholarshipCoverage.PERCENTAGE.name())) {
			companyScholarship.setPercentage(companyScholarshipDto.getPercentage());
		}
		companyScholarshipDto.getEligibleLevel().stream().map(levelName -> new ScholarshipEligibleLevel(levelName, new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleLevel);
		companyScholarshipDto.getEligibleCountry().stream().map(countryName -> new ScholarshipEligibleCountry(countryName, new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleCountry);
		companyScholarshipDto.getListOfScholarshipEligibleEntity().stream().map(eligibleEntity -> new ScholarshipEligibleEntity(eligibleEntity.getEntityId(),EntityType.valueOf(eligibleEntity.getEntityType()) , new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleEntity);
		companyScholarship.setUpdatedBy("API");
		companyScholarship.setUpdatedOn(new Date());
		companyScholarship.setCreatedBy("API");
		companyScholarship.setCreatedOn(new Date());
		companyScholarship.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyScholarshipDto.getPrivacyLevel()));
		return companyScholarship;
	}
	
	public static CompanyScholarship updateCompanyScholarshipFromCompanyScholarshipDto (CompanyScholarshipDto companyScholarshipDto , CompanyScholarship companyScholarship) {
		List<String> listOfLevelToBeAdded = new ArrayList<>();
		List<String> listOfCountryToBeAdded = new ArrayList<>();
		companyScholarship.setTitle(companyScholarshipDto.getTitle());
		companyScholarship.setDescription(companyScholarshipDto.getDescription());
		companyScholarship.setScholarshipValidity(ScholarshipValidity.valueOf(companyScholarshipDto.getValidity()));
		companyScholarship.setScholarshipCoverage(ScholarshipCoverage.valueOf(companyScholarshipDto.getModeOfCovergae()));
		companyScholarship.setUpdatedBy("API");
		companyScholarship.setUpdatedOn(new Date());
		companyScholarship.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyScholarshipDto.getPrivacyLevel()));
		if (companyScholarshipDto.getModeOfCovergae().equals(ScholarshipCoverage.AMOUNT.name()))  {
			companyScholarship.setScholarshipAmount(companyScholarshipDto.getAmount());
			companyScholarship.setCurrency(companyScholarshipDto.getCurrency());
		} else if (companyScholarshipDto.getModeOfCovergae().equals(ScholarshipCoverage.PERCENTAGE.name())) {
			companyScholarship.setPercentage(companyScholarshipDto.getPercentage());
		}
		log.info("Adding eligible level present in request and not in db");
		listOfLevelToBeAdded = companyScholarshipDto.getEligibleLevel().stream().filter(levelName -> !companyScholarship.getListOfScholarshipEligibleLevel().stream().anyMatch(scholarshipEligibleLevel -> scholarshipEligibleLevel.getLevelName().equalsIgnoreCase(levelName))).collect(Collectors.toList());
		log.info("Remove eligible level present in db and not in request");
		companyScholarship.getListOfScholarshipEligibleLevel().removeIf(eligibleLevel -> !companyScholarshipDto.getEligibleLevel().stream().anyMatch(levelName -> levelName.equalsIgnoreCase(eligibleLevel.getLevelName())));
		log.info("Creating eligible level model");
		listOfLevelToBeAdded.stream().map(levelName -> new ScholarshipEligibleLevel(levelName, new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleLevel);		
	
		log.info("Adding eligible country present in request and not in db");
		listOfCountryToBeAdded = companyScholarshipDto.getEligibleCountry().stream().filter(countryName -> !companyScholarship.getListOfScholarshipEligibleCountry().stream().anyMatch(scholarshipEligibleCountry -> scholarshipEligibleCountry.getCountryName().equalsIgnoreCase(countryName))).collect(Collectors.toList());
		log.info("Remove eligible country present in db and not in request");
		companyScholarship.getListOfScholarshipEligibleCountry().removeIf(eligibleCountry -> !companyScholarshipDto.getEligibleCountry().stream().anyMatch(countryName -> countryName.equalsIgnoreCase(eligibleCountry.getCountryName())));
		log.info("Creating eligible country model");
		listOfCountryToBeAdded.stream().map(countryName -> new ScholarshipEligibleCountry(countryName, new Date(), new Date(), "API", "API")).forEach(companyScholarship::addEligibleCountry);
		
		log.info("Adding eligible entity present in request and not in db");
		companyScholarshipDto.getListOfScholarshipEligibleEntity().stream().filter(eligibleEntityDto -> !companyScholarship.getListOfScholarshipEligibleEntity().stream().anyMatch(eligibleEntity -> eligibleEntity.getEntityId().equalsIgnoreCase(eligibleEntityDto.getEntityId()))).forEach(eligibleEntityDto ->  {
			 companyScholarship.addEligibleEntity(new ScholarshipEligibleEntity(eligibleEntityDto.getEntityId(),EntityType.valueOf(eligibleEntityDto.getEntityType()) , new Date(), new Date(), "API", "API")); 
		});
		log.info("Remove eligible country present in db and not in request");
		companyScholarship.getListOfScholarshipEligibleEntity().removeIf(eligibleEntity -> !companyScholarshipDto.getListOfScholarshipEligibleEntity().stream().anyMatch(eligibleEntityDto -> eligibleEntityDto.getEntityId().equalsIgnoreCase(eligibleEntity.getEntityId())));
		return companyScholarship;
	}
 }
