package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyInternshipDao;
import com.yuzee.company.model.CompanyInternship;
import com.yuzee.company.repository.CompanyInternshipRepository;

@Component
public class CompanyInternshipDaoImpl implements CompanyInternshipDao {
	
	@Autowired
	private CompanyInternshipRepository companyInternshipRepository;

	@Override
	public CompanyInternship addUpdateCompanyInternship(CompanyInternship companyInternship) {
		return companyInternshipRepository.save(companyInternship);
	}

	@Override
	public CompanyInternship getCompanyInternshipByCompanyIdAndId(String companyId, String id) {
		return companyInternshipRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public void deleteCompanyInternshipByCompanyIdAndId(String companyId, String id) {
		companyInternshipRepository.deleteByCompanyIdAndId(companyId, id);
	}

}
