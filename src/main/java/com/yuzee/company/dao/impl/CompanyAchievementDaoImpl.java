package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.CompanyAchievementDao;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.model.CompanyAchievement;
import com.yuzee.company.repository.CompanyAchievementRepository;

@Component
public class CompanyAchievementDaoImpl implements CompanyAchievementDao{

	@Autowired
	private CompanyAchievementRepository companyAchievementRepository;

	@Override
	public CompanyAchievement addUpdateCompanyAchievement(CompanyAchievement companyAchievement) {
		return companyAchievementRepository.save(companyAchievement);
	}

	@Override
	public void deleteCompanyAchievementByCompanyIdAndAchievementId(String companyId, String achievementId) {
		companyAchievementRepository.deleteByCompanyIdAndId(companyId, achievementId);
	}

	@Override
	public CompanyAchievement getCompanyAchievementByCompanyIdAndAchievementId(String companyId, String achievementId) {
		return companyAchievementRepository.findByCompanyIdAndId(companyId, achievementId);
	}

	@Override
	public List<CompanyAchievement> getCompanyAchievementByCompanyIdAndPrivacyLevel(String companyId,
			PrivacyLevelEnum privacyLevelEnum) {
		return companyAchievementRepository.findByCompanyIdAndPrivacyLevel(companyId, privacyLevelEnum);
	}
}
