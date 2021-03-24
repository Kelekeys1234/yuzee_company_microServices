package com.yuzee.company.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.Company;
import com.yuzee.company.repository.CompanyRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyDaoImpl implements CompanyDao {
	
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public Company addCompany(Company company) throws BadRequestException {
		try {
			return companyRepository.save(company);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company with same company name");
			throw new BadRequestException("Can not add company with same company name");
		}
		
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
