package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyCultureDao;
import com.yuzee.company.model.CompanyCulture;
import com.yuzee.company.repository.CompanyCultureRepository;

@Component
public class CompanyCultureDaoImpl implements CompanyCultureDao {

	@Autowired
	private CompanyCultureRepository companyCultureRepository;

	@Override
	public CompanyCulture getCompanyCultureByCompanyId(String companyId) {
		return companyCultureRepository.findByCompanyId(companyId);
	}

	@Override
	public CompanyCulture addUpdateCompanyCulture(CompanyCulture companyCulture) {
		return companyCultureRepository.save(companyCulture);
	}
}