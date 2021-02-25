package com.yuzee.company.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyWorkWithUsDao;
import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.model.CompanyWorkWithUs;
import com.yuzee.company.repository.CompanyWorkWithUsRepository;

@Component
public class CompanyWorkWithUsDaoImpl implements CompanyWorkWithUsDao {
	
	@Autowired
	private CompanyWorkWithUsRepository companyWorkWithUsRepository;

	@Override
	public Optional<CompanyWorkWithUs> getCompanyWorkWithUsByCompanyIdAndWorkWithUsValue(String companyId,
			WorkWithUs workWithUs) {
		return companyWorkWithUsRepository.findByCompanyIdAndWorkWithUs(companyId, workWithUs);
	}

	@Override
	public CompanyWorkWithUs addUpdateCompanyWorkWithUs(CompanyWorkWithUs companyWorkWithUs) {
		return companyWorkWithUsRepository.save(companyWorkWithUs);
	}

	@Override
	public void deleteCompanyWorkWithUsByCompanyIdAndCompanyWorkWithUsId(String companyId, String companyWorkWithUsId) {
		companyWorkWithUsRepository.deleteByCompanyIdAndId(companyId, companyWorkWithUsId);
	}
}
