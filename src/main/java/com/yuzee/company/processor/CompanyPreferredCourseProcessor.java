package com.yuzee.company.processor;

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
import com.yuzee.company.dao.CompanyPreferredCoursePrimaryDetailDao;
import com.yuzee.company.dto.CompanyPreferredCourseDetailsDto;
import com.yuzee.company.dto.CompanyPreferredCourseDto;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.NotFoundException;
import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.model.Company;
import com.yuzee.company.model.CompanyPreferredCourse;
import com.yuzee.company.model.CompanyPreferredCoursePrimaryDetail;
import com.yuzee.company.utills.ValidationUtills;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyPreferredCourseProcessor {
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private CompanyPreferredCoursePrimaryDetailDao companyPreferredCoursePrimaryDetailDao;
	
	@Transactional(rollbackOn = Throwable.class)
	public void addUpdateCompanyPreferredCourse(String userId, String companyId,
			CompanyPreferredCourseDetailsDto companyPreferredCourseDetailsDto)
			throws NotFoundException, UnauthorizeException {
		log.debug("Inside CompanyPreferredCourseProcessor.addUpdateCompanyPreferredCourse() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);

		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}", companyId);
			throw new NotFoundException("No company found for company id " + companyId);
		}
		log.info("Validting user have access for company");
		ValidationUtills.validateUserAccess(userId, optionalCompany.get());
		Company company = optionalCompany.get();
		log.info("Getting all exsisting preferred course ");
		CompanyPreferredCoursePrimaryDetail companyPreferredCoursePrimaryDetail = company
				.getCompanyPreferredCoursePrimaryDetail();
		if (!ObjectUtils.isEmpty(companyPreferredCoursePrimaryDetail)) {
			companyPreferredCoursePrimaryDetail
					.setPrivacyLevel(PrivacyLevelEnum.valueOf(companyPreferredCourseDetailsDto.getPrivacyLevel()));
			companyPreferredCoursePrimaryDetail.setUpdatedOn(new Date());
			companyPreferredCoursePrimaryDetail.setUpdatedBy("API");
			log.info("Getting exsisting preferred course form db");
			if (!CollectionUtils.isEmpty(companyPreferredCoursePrimaryDetail.getListOfCompanyPreferredCourse())) {
				log.info("Adding all preferred course presend in request but not present in db");
				List<CompanyPreferredCourseDto> listOfCompanyPreferredCourseDtoToBeAdded = companyPreferredCourseDetailsDto
						.getListOfCompanyPreferredCourseDto().stream()
						.filter(companyPreferredCourseDto -> !companyPreferredCoursePrimaryDetail
								.getListOfCompanyPreferredCourse().stream()
								.anyMatch(companyPreferredCourse -> companyPreferredCourseDto.getPreferredCourseId()
										.equalsIgnoreCase(companyPreferredCourse.getCourseId())))
						.collect(Collectors.toList());
				log.info("Removing all preferred course present in db but not passed in request");
				companyPreferredCoursePrimaryDetail.getListOfCompanyPreferredCourse()
						.removeIf(
								companyPreferredCourse -> !companyPreferredCourseDetailsDto
										.getListOfCompanyPreferredCourseDto().stream()
										.anyMatch(companyPreferredCourseDto -> companyPreferredCourseDto
												.getPreferredCourseId()
												.equalsIgnoreCase(companyPreferredCourse.getCourseId())));
			
				log.info(
						"Creating CompanyPreferredCourse model from  CompanyPreferredCourseDto and adding it into list");
				listOfCompanyPreferredCourseDtoToBeAdded.stream()
				.forEach(companyPreferredCourseDto -> {
					companyPreferredCoursePrimaryDetail.addCompanyPreferredCourse(new CompanyPreferredCourse(
							companyPreferredCourseDto.getPreferredCourseName(), new Date(), new Date(), "API", "API",
							companyPreferredCourseDto.getPreferredCourseId()));
				});
				log.info("Calling DB to add company preferred course details");
				companyPreferredCoursePrimaryDetailDao
						.addUpdateCompanyPreferredCoursePrimaryDetail(companyPreferredCoursePrimaryDetail);
			}
		} else {
			log.info("Creating new company preferred course details model");
			CompanyPreferredCoursePrimaryDetail companyPreferredCoursePrimaryDetailToBeAdded = new CompanyPreferredCoursePrimaryDetail(
					PrivacyLevelEnum.valueOf(companyPreferredCourseDetailsDto.getPrivacyLevel()), new Date(),
					new Date(), "API", "API");
			companyPreferredCoursePrimaryDetailToBeAdded.setCompany(company);
			log.info("No exsisting prefered course found adding all");
			companyPreferredCourseDetailsDto
					.getListOfCompanyPreferredCourseDto().stream()
					.forEach(companyPreferredCourseDto -> {
						companyPreferredCoursePrimaryDetailToBeAdded.addCompanyPreferredCourse(new CompanyPreferredCourse(
								companyPreferredCourseDto.getPreferredCourseName(), new Date(), new Date(), "API", "API",
								companyPreferredCourseDto.getPreferredCourseId()));
					});
			log.info("Calling DB to add company preferred course details");
			companyPreferredCoursePrimaryDetailDao
					.addUpdateCompanyPreferredCoursePrimaryDetail(companyPreferredCoursePrimaryDetailToBeAdded);
		}
	}
	
	@Transactional
	public CompanyPreferredCourseDetailsDto getAllCompanyPreferredCourse(String userId, String companyId)
			throws NotFoundException {
		CompanyPreferredCourseDetailsDto companyPreferredCourseDetailsDto = new CompanyPreferredCourseDetailsDto();;
		log.debug("Inside CompanyPreferredCourseProcessor.getAllCompanyPreferredCourse() method");
		log.info("Getting company with companyId {}", companyId);
		Optional<Company> optionalCompany = companyDao.getCompanyById(companyId);

		if (!optionalCompany.isPresent()) {
			log.error("No company found for company id {}", companyId);
			throw new NotFoundException("No company found for company id " + companyId);
		}
		Company company = optionalCompany.get();
		boolean isAdmin = ValidationUtills.isAdmin(userId, optionalCompany.get());

		if (!ObjectUtils.isEmpty(company.getCompanyPreferredCoursePrimaryDetail())) { 
			if (isAdmin) {
				log.info("Company preffered course not empty for company id {} creating respose dto", companyId);
				companyPreferredCourseDetailsDto
						.setPrivacyLevel(company.getCompanyPreferredCoursePrimaryDetail().getPrivacyLevel().name());
				companyPreferredCourseDetailsDto.setListOfCompanyPreferredCourseDto(
						company.getCompanyPreferredCoursePrimaryDetail().getListOfCompanyPreferredCourse().stream()
								.map(companyPreferredCourse -> new CompanyPreferredCourseDto(
										companyPreferredCourse.getCourseId(), companyPreferredCourse.getCourseName()))
								.collect(Collectors.toList()));
			} else {
				if (company.getCompanyPreferredCoursePrimaryDetail().getPrivacyLevel().equals(PrivacyLevelEnum.PUBLIC)) {
					companyPreferredCourseDetailsDto
							.setPrivacyLevel(company.getCompanyPreferredCoursePrimaryDetail().getPrivacyLevel().name());
					companyPreferredCourseDetailsDto.setListOfCompanyPreferredCourseDto(company
							.getCompanyPreferredCoursePrimaryDetail().getListOfCompanyPreferredCourse().stream()
							.map(companyPreferredCourse -> new CompanyPreferredCourseDto(
									companyPreferredCourse.getCourseId(), companyPreferredCourse.getCourseName()))
							.collect(Collectors.toList()));
				}
			}
			
		}

		return companyPreferredCourseDetailsDto;
	}
}