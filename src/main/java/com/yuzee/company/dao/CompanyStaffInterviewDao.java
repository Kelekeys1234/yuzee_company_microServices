package com.yuzee.company.dao;

import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyStaffInterview;

public interface CompanyStaffInterviewDao {

	public CompanyStaffInterview addUpdateCompanyStaffInterview (CompanyStaffInterview companyStaffInterview) throws BadRequestException;
	
	public CompanyStaffInterview getCompanyStaffInterviewByCompanyIdAndId(String companyId , String staffInterviewId);
	
	public void deleteCompanyStaffInterviewByCompanyIdAndId(String companyId , String staffInterviewId);
	
}
