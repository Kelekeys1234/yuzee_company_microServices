package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyStaffInterviewDao;
import com.yuzee.company.model.CompanyStaffInterview;
import com.yuzee.company.repository.CompanyStaffInterviewRepository;

@Component
public class CompanyStaffInterviewDaoImpl implements CompanyStaffInterviewDao {

	@Autowired
	private CompanyStaffInterviewRepository companyStaffInterviewRepository;

	@Override
	public CompanyStaffInterview addUpdateCompanyStaffInterview(CompanyStaffInterview companyStaffInterview) {
		return companyStaffInterviewRepository.save(companyStaffInterview);
	}

	@Override
	public CompanyStaffInterview getCompanyStaffInterviewByCompanyIdAndId(String companyId, String staffInterviewId) {
		return companyStaffInterviewRepository.findByCompanyIdAndId(companyId, staffInterviewId);
	}

	@Override
	public void deleteCompanyStaffInterviewByCompanyIdAndId(String companyId, String staffInterviewId) {
		companyStaffInterviewRepository.deleteByCompanyIdAndId(companyId, staffInterviewId);
	}
	

}
