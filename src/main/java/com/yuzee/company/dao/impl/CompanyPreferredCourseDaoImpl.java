package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyPreferredCourseDao;
import com.yuzee.company.model.CompanyPreferredCourse;
import com.yuzee.company.repository.CompanyPreferredCourseRepository;

@Component
public class CompanyPreferredCourseDaoImpl implements CompanyPreferredCourseDao {

	@Autowired
	private CompanyPreferredCourseRepository companyPreferredCourseRepository;

	@Override
	public List<CompanyPreferredCourse> addUpdateCompanyPreferredCourse(List<CompanyPreferredCourse> listOfCompanyPreferredCourse) {
		return companyPreferredCourseRepository.saveAll(listOfCompanyPreferredCourse);	
	}
}
