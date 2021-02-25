package com.yuzee.company.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;
import com.yuzee.company.model.AchievementTagedUser;

public interface AchievementTagedUserDao {
	
	public List<AchievementTagedUser> getAchivementTagedUserBasedOnStatus(String userId , AchievementTagedUserRequestStatus achievementTagedUserRequestStatus, Pageable pageable);

	public AchievementTagedUser getAchievementTagedUserByUserIdAndAchivementTagedId(String userId , String achivementTagedId);

	public AchievementTagedUser addAchievementTagedUser(AchievementTagedUser achievementTagedUser) ;
}
