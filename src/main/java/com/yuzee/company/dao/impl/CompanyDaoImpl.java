package com.yuzee.company.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.model.Company;
import com.yuzee.company.repository.CompanyRepository;

@Component
public class CompanyDaoImpl implements CompanyDao {
	
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public Company addCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Optional<Company> getCompanyById(String companyId) {
		return companyRepository.findById(companyId);
	}

	@Override
	public List<Company> getAllCompanyByIds(Set<String> companyIds) {
		return companyRepository.findAllById(companyIds);
	}

}
