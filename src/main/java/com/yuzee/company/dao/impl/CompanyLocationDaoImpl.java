package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyLocationDao;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyLocation;
import com.yuzee.company.repository.CompanyLocationRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CompanyLocationDaoImpl implements CompanyLocationDao {
	
	@Autowired
	private CompanyLocationRepository companyLocationRepository;

	@Override
	public CompanyLocation addUpdateCompanyLocation(CompanyLocation companyLocation) throws BadRequestException {
		try {
			return companyLocationRepository.save(companyLocation);
		} catch (DataIntegrityViolationException e) {
			log.error("Can not add company location with same name , city , state ,country");
			throw new BadRequestException("Can not add company location with same name , city , state ,country");
		}
		
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
