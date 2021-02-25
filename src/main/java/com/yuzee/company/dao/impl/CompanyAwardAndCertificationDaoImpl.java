package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyAwardAndCertificationDao;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyAwardAndCertification;
import com.yuzee.company.repository.CompanyAwardAndCertificationRepository;

@Component
public class CompanyAwardAndCertificationDaoImpl implements CompanyAwardAndCertificationDao {

	@Autowired
	private CompanyAwardAndCertificationRepository companyAwardAndCertificationRepository;

	@Override
	public CompanyAwardAndCertification addUpdateCompanyAwardAndCertification(
			CompanyAwardAndCertification companyAwardAndCertification) {
		return companyAwardAndCertificationRepository.save(companyAwardAndCertification);
	}

	@Override
	public void deleteByCompanyIdAndId(String companyId, String id) {
		companyAwardAndCertificationRepository.deleteByCompanyIdAndId(companyId, id);
	}

	@Override
	public CompanyAwardAndCertification getCompanyAwardAndCertificationByCompanyIdAndId(String companyId, String id) {
		return companyAwardAndCertificationRepository.findByCompanyIdAndId(companyId, id);
	}

	@Override
	public List<CompanyAwardAndCertification> getCompanyAwardAndCertificationByCompanyIdAndPrivacyLevel(String companyId,
			PrivacyLevelEnum privacyLevelEnum) {
		return companyAwardAndCertificationRepository.findByCompanyIdAndPrivacyLevel(companyId,privacyLevelEnum);
	}

	
}