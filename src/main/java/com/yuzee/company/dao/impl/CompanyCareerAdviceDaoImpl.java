package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyCareerAdviceDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyCareerAdvice;
import com.yuzee.company.repository.CompanyCareerAdviceRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyCareerAdviceDaoImpl implements CompanyCareerAdviceDao {
	
	@Autowired
	private CompanyCareerAdviceRepository companyCareerAdviceRepository;

	@Override
	public CompanyCareerAdvice addUpdateCompanyCareerAdvice(CompanyCareerAdvice companyCareerAdvice) throws BadRequestException {
		try {
			return companyCareerAdviceRepository.save(companyCareerAdvice);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company career advice with same title");
			throw new BadRequestException("Can not add company career advice with same title");
		}
	}

	@Override
	public CompanyCareerAdvice getCompanyCareerAdviceByCompanyIdAndId(String companyId, String id) {
		return companyCareerAdviceRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public void deleteCompanyCareerAdviceByCompanyIdAndId(String companyId, String id) {
		companyCareerAdviceRepository.deleteByCompanyIdAndId(companyId, id);
	}

}
