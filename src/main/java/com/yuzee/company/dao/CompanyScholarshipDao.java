package com.yuzee.company.dao;

import com.yuzee.company.model.CompanyScholarship;

public interface CompanyScholarshipDao {
	
	public CompanyScholarship addUpdateCompanyScholarship(CompanyScholarship companyScholarship);
	
	public CompanyScholarship getCompanyScholarshipByCompanyIdAndId(String companyId , String id);
	
	public void deleteCompanyScholarshipByCompanyIdAndId(String companyId , String id);

}
