package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyPreferredCoursePrimaryDetailDao;
import com.yuzee.company.model.CompanyPreferredCoursePrimaryDetail;
import com.yuzee.company.repository.CompanyPreferredCoursePrimaryDetailRepository;

@Component
public class CompanyPreferredCoursePrimaryDetailDaoImpl implements CompanyPreferredCoursePrimaryDetailDao {
	
	@Autowired
	private CompanyPreferredCoursePrimaryDetailRepository companyPreferredCoursePrimaryDetailRepository;

	@Override
	public CompanyPreferredCoursePrimaryDetail addUpdateCompanyPreferredCoursePrimaryDetail(
			CompanyPreferredCoursePrimaryDetail companyPreferredCoursePrimaryDetail) {
		return companyPreferredCoursePrimaryDetailRepository.save(companyPreferredCoursePrimaryDetail);
	}

}
