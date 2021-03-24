package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyPreferredCourseDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyPreferredCourse;
import com.yuzee.company.repository.CompanyPreferredCourseRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyPreferredCourseDaoImpl implements CompanyPreferredCourseDao {

	@Autowired
	private CompanyPreferredCourseRepository companyPreferredCourseRepository;

	@Override
	public List<CompanyPreferredCourse> addUpdateCompanyPreferredCourse(List<CompanyPreferredCourse> listOfCompanyPreferredCourse) throws BadRequestException {
		try {
			return companyPreferredCourseRepository.saveAll(listOfCompanyPreferredCourse);	
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company partner with same entity_id , entity_type ");
			throw new BadRequestException("Can not add company partner with same entity_id , entity_type ");
		}
	}
}
