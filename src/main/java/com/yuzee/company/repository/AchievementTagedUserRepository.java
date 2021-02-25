package com.yuzee.company.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;
import com.yuzee.company.model.AchievementTagedUser;

@Repository
public interface AchievementTagedUserRepository extends JpaRepository<AchievementTagedUser, String> {

	public List<AchievementTagedUser> findByUserIdAndAchievementTagedUserRequestStatus(String userId, AchievementTagedUserRequestStatus
			achievementTagedUserRequestStatus, Pageable pageable);
	
	public AchievementTagedUser findByUserIdAndId(String userId , String id);
}
