package com.yuzee.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyAwardAndCertification;

@Repository
public interface CompanyAwardAndCertificationRepository extends JpaRepository<com.yuzee.company.model.CompanyAwardAndCertification, String> {

	public void deleteByCompanyIdAndId(String companyId , String id);
	
	public CompanyAwardAndCertification findByCompanyIdAndId(String companyId , String id);
	
	public List<CompanyAwardAndCertification>  findByCompanyIdAndPrivacyLevel(String companyId , PrivacyLevelEnum privacyLevelEnum);

}
