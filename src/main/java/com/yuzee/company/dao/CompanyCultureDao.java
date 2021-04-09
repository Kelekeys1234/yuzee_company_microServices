package com.yuzee.company.dao;

import com.yuzee.company.model.CompanyCulture;

public interface CompanyCultureDao {
	
	public CompanyCulture getCompanyCultureByCompanyId(String companyId);
	
	public CompanyCulture addUpdateCompanyCulture(CompanyCulture companyCulture);
}
