package com.yuzee.company.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.yuzee.company.enumeration.PrivacyLevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_achievement", uniqueConstraints = @UniqueConstraint( columnNames = { "title", "company_id"}, name = "UK_CA_TITLE_CI"),
indexes = { @Index (name = "IDX_COMPANY_ACHIEVEMENT_COMPANY_ID_ID", columnList="company_id,id", unique = true),
		@Index (name = "IDX_COMPANY_ACHIEVEMENT_COMPANY_ID_PRIVACY_LEVEL", columnList="company_id,privacy_level", unique = false)})
public class CompanyAchievement implements Serializable {
	
	private static final long serialVersionUID = 992056623806787005L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "privacy_level", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PUBLIC'")
	@Enumerated(EnumType.STRING)
	private PrivacyLevelEnum privacyLevel;
	
	@Column(name = "title" , nullable = false)
	private String title;
	
	@Column(name = "description" , nullable = false)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "achieved_date")
	private Date achievedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_id", insertable = true, updatable = false , nullable = false)
	private Company company;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyAchievement" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AchievementTagedUser> listOfAchievementTagedUser = new ArrayList<>();
	
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

	public CompanyAchievement(PrivacyLevelEnum privacyLevelEnum,String title, String description, Date achievedDate, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.title = title;
		this.description = description;
		this.achievedDate = achievedDate;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.privacyLevel = privacyLevelEnum;
	}
	
	public void addAchievementTagedUser(AchievementTagedUser achievementTagedUser) {
		listOfAchievementTagedUser.add(achievementTagedUser);
		achievementTagedUser.setCompanyAchievement(this);
	}
}
