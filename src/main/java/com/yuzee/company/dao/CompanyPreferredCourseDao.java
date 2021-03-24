package com.yuzee.company.dao;

import java.util.List;

import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyPreferredCourse;

public interface CompanyPreferredCourseDao {
	
	public List<CompanyPreferredCourse> addUpdateCompanyPreferredCourse (List<CompanyPreferredCourse> listOfCompanyPreferredCourse) throws BadRequestException ;

}
