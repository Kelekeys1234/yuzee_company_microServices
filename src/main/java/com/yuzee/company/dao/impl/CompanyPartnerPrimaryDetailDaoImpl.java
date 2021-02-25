package com.yuzee.company.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyPartnerPrimaryDetailDao;
import com.yuzee.company.model.CompanyPartnerPrimaryDetail;
import com.yuzee.company.repository.CompanyPartnerPrimaryDetailRepository;

@Component
public class CompanyPartnerPrimaryDetailDaoImpl implements CompanyPartnerPrimaryDetailDao {
	
	@Autowired
	private CompanyPartnerPrimaryDetailRepository companyPartnerPrimaryDetailRepository;

	@Override
	public CompanyPartnerPrimaryDetail addUpdateCompanyPartnerPrimaryDetail(
			CompanyPartnerPrimaryDetail companyPartnerPrimaryDetail) {
		return companyPartnerPrimaryDetailRepository.save(companyPartnerPrimaryDetail);
	}

}
