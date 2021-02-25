package com.yuzee.company.dao;

import com.yuzee.company.model.CompanyStaffInterview;

public interface CompanyStaffInterviewDao {

	public CompanyStaffInterview addUpdateCompanyStaffInterview (CompanyStaffInterview companyStaffInterview);
	
	public CompanyStaffInterview getCompanyStaffInterviewByCompanyIdAndId(String companyId , String staffInterviewId);
	
	public void deleteCompanyStaffInterviewByCompanyIdAndId(String companyId , String staffInterviewId);
	
}
