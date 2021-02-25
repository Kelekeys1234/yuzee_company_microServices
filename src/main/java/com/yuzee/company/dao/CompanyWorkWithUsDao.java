package com.yuzee.company.dao;

import java.util.Optional;

import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.model.CompanyWorkWithUs;

public interface CompanyWorkWithUsDao {
	
	public Optional<CompanyWorkWithUs> getCompanyWorkWithUsByCompanyIdAndWorkWithUsValue (String companyId, WorkWithUs workWithUs);

	public CompanyWorkWithUs addUpdateCompanyWorkWithUs(CompanyWorkWithUs companyWorkWithUs);

	public void deleteCompanyWorkWithUsByCompanyIdAndCompanyWorkWithUsId(String companyId , String companyWorkWithUsId);
}
