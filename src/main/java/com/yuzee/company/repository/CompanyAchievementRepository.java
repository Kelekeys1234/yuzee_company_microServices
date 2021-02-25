package com.yuzee.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyAchievement;

@Repository
public interface CompanyAchievementRepository extends JpaRepository<CompanyAchievement, String> {
	
	public void deleteByCompanyIdAndId(String companyId , String id);
	
	public CompanyAchievement findByCompanyIdAndId(String companyId , String id);
	
	public List<CompanyAchievement> findByCompanyIdAndPrivacyLevel(String companyId , PrivacyLevelEnum privacyLevelEnum);
}
