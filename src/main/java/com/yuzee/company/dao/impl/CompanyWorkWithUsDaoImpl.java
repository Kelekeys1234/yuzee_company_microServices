package com.yuzee.company.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyWorkWithUsDao;
import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyWorkWithUs;
import com.yuzee.company.repository.CompanyWorkWithUsRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyWorkWithUsDaoImpl implements CompanyWorkWithUsDao {
	
	@Autowired
	private CompanyWorkWithUsRepository companyWorkWithUsRepository;

	@Override
	public Optional<CompanyWorkWithUs> getCompanyWorkWithUsByCompanyIdAndWorkWithUsValue(String companyId,
			WorkWithUs workWithUs) {
		return companyWorkWithUsRepository.findByCompanyIdAndWorkWithUs(companyId, workWithUs);
	}

	@Override
	public CompanyWorkWithUs addUpdateCompanyWorkWithUs(CompanyWorkWithUs companyWorkWithUs) throws BadRequestException {
		try {
			return companyWorkWithUsRepository.save(companyWorkWithUs);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company work with us with same work with us name");
			throw new BadRequestException("Can not add company work with us with same work with us name");
		}
		
	}

	@Override
	public void deleteCompanyWorkWithUsByCompanyIdAndCompanyWorkWithUsId(String companyId, String companyWorkWithUsId) {
		companyWorkWithUsRepository.deleteByCompanyIdAndId(companyId, companyWorkWithUsId);
	}
}
