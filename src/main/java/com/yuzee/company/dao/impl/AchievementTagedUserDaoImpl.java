package com.yuzee.company.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.AchievementTagedUserDao;
import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;
import com.yuzee.company.model.AchievementTagedUser;
import com.yuzee.company.repository.AchievementTagedUserRepository;

@Component
public class AchievementTagedUserDaoImpl implements AchievementTagedUserDao {
	
	@Autowired
	private AchievementTagedUserRepository achievementTagedUserRepository;

	@Override
	public List<AchievementTagedUser> getAchivementTagedUserBasedOnStatus(String userId,
			AchievementTagedUserRequestStatus achievementTagedUserRequestStatus, Pageable pageable) {
		return achievementTagedUserRepository.findByUserIdAndAchievementTagedUserRequestStatus(userId, achievementTagedUserRequestStatus, pageable);
	}

	@Override
	public AchievementTagedUser getAchievementTagedUserByUserIdAndAchivementTagedId(String userId,
			String achivementTagedId) {
		return achievementTagedUserRepository.findByUserIdAndId(userId, achivementTagedId);
	}

	@Override
	public AchievementTagedUser addAchievementTagedUser(AchievementTagedUser achievementTagedUser) {
		return achievementTagedUserRepository.save(achievementTagedUser);
	}
	

}
