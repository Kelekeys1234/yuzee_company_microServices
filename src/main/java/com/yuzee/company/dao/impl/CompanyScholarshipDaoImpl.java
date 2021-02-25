package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyScholarshipDao;
import com.yuzee.company.model.CompanyScholarship;
import com.yuzee.company.repository.CompanyScholarshipRepository;

@Component
public class CompanyScholarshipDaoImpl implements CompanyScholarshipDao {

	@Autowired
	private CompanyScholarshipRepository companyScholarshipRepository;

	@Override
	public CompanyScholarship addUpdateCompanyScholarship(CompanyScholarship companyScholarship) {
		return companyScholarshipRepository.save(companyScholarship);
	}

	@Override
	public CompanyScholarship getCompanyScholarshipByCompanyIdAndId(String companyId, String id) {
		return companyScholarshipRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public void deleteCompanyScholarshipByCompanyIdAndId(String companyId, String id) {
		companyScholarshipRepository.deleteByCompanyIdAndId(companyId, id);
		
	}

	
}
