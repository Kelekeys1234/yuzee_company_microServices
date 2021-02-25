package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyPartnerDao;
import com.yuzee.company.model.CompanyPartner;
import com.yuzee.company.repository.CompanyPartnerRepository;

@Component
public class CompanyPartnerDaoImpl implements CompanyPartnerDao {
	
	@Autowired
	private CompanyPartnerRepository companyPartnerRepository;

	@Override
	public void addUpdateAllCompanyParter(List<CompanyPartner> listOfCompanyPartner) {
		companyPartnerRepository.saveAll(listOfCompanyPartner);
	}

}
