package com.yuzee.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyLocation;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, String> {
	
	public void deleteByCompanyIdAndId(String companyId, String locationId);
	
	public CompanyLocation findByCompanyIdAndId(String companyId, String id);
	
	public List<CompanyLocation> findByCompanyIdAndPrivacyLevel(String companyId , PrivacyLevelEnum privacyLevelEnum);

}
