package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyStaffInterviewDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyStaffInterview;
import com.yuzee.company.repository.CompanyStaffInterviewRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyStaffInterviewDaoImpl implements CompanyStaffInterviewDao {

	@Autowired
	private CompanyStaffInterviewRepository companyStaffInterviewRepository;

	@Override
	public CompanyStaffInterview addUpdateCompanyStaffInterview(CompanyStaffInterview companyStaffInterview) throws BadRequestException {
		try {
			return companyStaffInterviewRepository.save(companyStaffInterview);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company staff interview with same title");
			throw new BadRequestException("Can not add company staff interview with same title");
		}
		
		
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
