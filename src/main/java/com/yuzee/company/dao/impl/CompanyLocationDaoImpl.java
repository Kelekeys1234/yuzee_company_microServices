package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyLocationDao;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyLocation;
import com.yuzee.company.repository.CompanyLocationRepository;

@Component
public class CompanyLocationDaoImpl implements CompanyLocationDao {
	
	@Autowired
	private CompanyLocationRepository companyLocationRepository;

	@Override
	public CompanyLocation addUpdateCompanyLocation(CompanyLocation companyLocation) {
		return companyLocationRepository.save(companyLocation);
	}

	@Override
	public void deleteCompanyLocationByCompanyIdAndLocationId(String companyId, String locationId) {
		companyLocationRepository.deleteByCompanyIdAndId(companyId, locationId);
	}

	@Override
	public CompanyLocation getCompanyLocationByCompanyIdAndId(String companyId, String locationId) {
		return companyLocationRepository.findByCompanyIdAndId(companyId, locationId);
	}

	@Override
	public List<CompanyLocation> getCompanyLocationByCompanyIdAndPrivacyLevel(String companyId,
			PrivacyLevelEnum privacyLevelEnum) {
		return companyLocationRepository.findByCompanyIdAndPrivacyLevel(companyId, privacyLevelEnum);
	}

}
