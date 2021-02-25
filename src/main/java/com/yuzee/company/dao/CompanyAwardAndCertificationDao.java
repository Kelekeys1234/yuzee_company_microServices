package com.yuzee.company.dao;

import java.util.List;

import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyAwardAndCertification;

public interface CompanyAwardAndCertificationDao {
	
	public CompanyAwardAndCertification addUpdateCompanyAwardAndCertification (CompanyAwardAndCertification companyAwardAndCertification) ;

	public void deleteByCompanyIdAndId(String companyId , String id);
	
	public CompanyAwardAndCertification getCompanyAwardAndCertificationByCompanyIdAndId(String companyId , String id);
	
	public List<CompanyAwardAndCertification> getCompanyAwardAndCertificationByCompanyIdAndPrivacyLevel(String companyId , PrivacyLevelEnum privacyLevelEnum); 
}
