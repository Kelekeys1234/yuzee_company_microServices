package com.yuzee.company.dao;

import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyCareerAdvice;

public interface CompanyCareerAdviceDao {
	
	public CompanyCareerAdvice addUpdateCompanyCareerAdvice(CompanyCareerAdvice companyCareerAdvice ) throws BadRequestException;
	
	public CompanyCareerAdvice getCompanyCareerAdviceByCompanyIdAndId(String companyId, String id);
	
	public void deleteCompanyCareerAdviceByCompanyIdAndId(String companyId, String id);

}
