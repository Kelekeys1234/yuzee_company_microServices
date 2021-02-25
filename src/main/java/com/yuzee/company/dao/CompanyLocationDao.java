package com.yuzee.company.dao;

import java.util.List;

import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyLocation;

public interface CompanyLocationDao {
	
	public CompanyLocation addUpdateCompanyLocation(CompanyLocation companyLocation);
	
	public void deleteCompanyLocationByCompanyIdAndLocationId(String companyId, String locationId);
	
	public CompanyLocation getCompanyLocationByCompanyIdAndId (String companyId , String locationId);
	
	public List<CompanyLocation> getCompanyLocationByCompanyIdAndPrivacyLevel(String companyId,
			PrivacyLevelEnum privacyLevelEnum) ;

}
