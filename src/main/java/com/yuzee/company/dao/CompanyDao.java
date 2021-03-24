package com.yuzee.company.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.Company;

public interface CompanyDao {
	
	public Company addCompany (Company company) throws BadRequestException;
	
	public Optional<Company> getCompanyById (String companyId);
	
	public List<Company> getAllCompanyByIds(Set<String> companyIds);
	
}
