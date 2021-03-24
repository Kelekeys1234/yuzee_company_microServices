package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyScholarshipDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyScholarship;
import com.yuzee.company.repository.CompanyScholarshipRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyScholarshipDaoImpl implements CompanyScholarshipDao {

	@Autowired
	private CompanyScholarshipRepository companyScholarshipRepository;

	@Override
	public CompanyScholarship addUpdateCompanyScholarship(CompanyScholarship companyScholarship) throws BadRequestException {
		try {
			return companyScholarshipRepository.save(companyScholarship);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company scholarship with same title");
			throw new BadRequestException("Can not add company scholarship with same title");
		}
		
	}

	@Override
	public CompanyScholarship getCompanyScholarshipByCompanyIdAndId(String companyId, String id) {
		return companyScholarshipRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public void deleteCompanyScholarshipByCompanyIdAndId(String companyId, String id) {
		companyScholarshipRepository.deleteByCompanyIdAndId(companyId, id);
		
	}

	
}
