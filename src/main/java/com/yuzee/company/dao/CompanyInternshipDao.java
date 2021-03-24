package com.yuzee.company.dao;

import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyInternship;

public interface CompanyInternshipDao {
	
	public CompanyInternship addUpdateCompanyInternship (CompanyInternship companyInternship) throws BadRequestException;
	
	public CompanyInternship getCompanyInternshipByCompanyIdAndId (String companyId , String id);
	
	public void deleteCompanyInternshipByCompanyIdAndId (String companyId , String id);

}
