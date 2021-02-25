package com.yuzee.company.dao;

import java.util.List;

import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyAchievement;

public interface CompanyAchievementDao {
	
	public CompanyAchievement addUpdateCompanyAchievement (CompanyAchievement companyAchievement);
	
	public void deleteCompanyAchievementByCompanyIdAndAchievementId (String companyId , String achievementId);
	
	public CompanyAchievement getCompanyAchievementByCompanyIdAndAchievementId (String companyId , String achievementId);
	
	public List<CompanyAchievement> getCompanyAchievementByCompanyIdAndPrivacyLevel (String companyId , PrivacyLevelEnum privacyLevelEnum);

}
