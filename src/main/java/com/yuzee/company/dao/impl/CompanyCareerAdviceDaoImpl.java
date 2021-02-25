package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyCareerAdviceDao;
import com.yuzee.company.model.CompanyCareerAdvice;
import com.yuzee.company.repository.CompanyCareerAdviceRepository;

@Component
public class CompanyCareerAdviceDaoImpl implements CompanyCareerAdviceDao {
	
	@Autowired
	private CompanyCareerAdviceRepository companyCareerAdviceRepository;

	@Override
	public CompanyCareerAdvice addUpdateCompanyCareerAdvice(CompanyCareerAdvice companyCareerAdvice) {
		return companyCareerAdviceRepository.save(companyCareerAdvice);
	}

	@Override
	public CompanyCareerAdvice getCompanyCareerAdviceByCompanyIdAndId(String companyId, String id) {
		return companyCareerAdviceRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public void deleteCompanyCareerAdviceByCompanyIdAndId(String companyId, String id) {
		companyCareerAdviceRepository.deleteByCompanyIdAndId(companyId, id);
	}

}
