package com.yuzee.company.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.yuzee.company.enumeration.AchievementTagedUserRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "achievement_taged_user", uniqueConstraints = @UniqueConstraint( columnNames = { "user_id", "company_achievement_id"}, name = "UK_UI_CAI"))
public class AchievementTagedUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7445920686052745566L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "request_status", nullable = false)
	private AchievementTagedUserRequestStatus achievementTagedUserRequestStatus = AchievementTagedUserRequestStatus.PENDING;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_achievement_id", insertable = true, updatable = false , nullable = false)
	private CompanyAchievement companyAchievement;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	private Date updatedOn;

	@Column(name = "created_by", length = 50)
	private String createdBy;

	@Column(name = "updated_by", length = 50)
	private String updatedBy;

	public AchievementTagedUser(String userId, Date createdOn, Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.userId = userId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	

}