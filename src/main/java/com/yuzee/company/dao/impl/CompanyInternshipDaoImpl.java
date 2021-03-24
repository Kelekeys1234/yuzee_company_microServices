package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyInternshipDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyInternship;
import com.yuzee.company.repository.CompanyInternshipRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyInternshipDaoImpl implements CompanyInternshipDao {
	
	@Autowired
	private CompanyInternshipRepository companyInternshipRepository;

	@Override
	public CompanyInternship addUpdateCompanyInternship(CompanyInternship companyInternship) throws BadRequestException {
		try {
			return companyInternshipRepository.save(companyInternship);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company internship with same title");
			throw new BadRequestException("Can not add company internship with same title");
		}
		
	}

	@Override
	public CompanyInternship getCompanyInternshipByCompanyIdAndId(String companyId, String id) {
		return companyInternshipRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public void deleteCompanyInternshipByCompanyIdAndId(String companyId, String id) {
		companyInternshipRepository.deleteByCompanyIdAndId(companyId, id);
	}

}
