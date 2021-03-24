package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyPartnerDao;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyPartner;
import com.yuzee.company.repository.CompanyPartnerRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyPartnerDaoImpl implements CompanyPartnerDao {
	
	@Autowired
	private CompanyPartnerRepository companyPartnerRepository;

	@Override
	public void addUpdateAllCompanyParter(List<CompanyPartner> listOfCompanyPartner) throws BadRequestException {
		try {
			companyPartnerRepository.saveAll(listOfCompanyPartner);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company partner with same entity_id , entity_type ");
			throw new BadRequestException("Can not add company partner with same entity_id , entity_type ");
		}
	
	}

}
