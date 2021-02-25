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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "company_award_and_certification", uniqueConstraints = @UniqueConstraint( columnNames = { "title", "company_id"}, name = "UK_CAAC_TITLE_CI"),
indexes = { @Index (name = "IDX_COMPANY_AWARD_AND_CERT_COMPANY_ID_ID", columnList="company_id,id", unique = true),
		@Index (name = "IDX_COMPANY_AWARD_AND_CERT_COMPANY_ID_PRIVACY_LEVEL", columnList="company_id,privacy_level", unique = false)})
public class CompanyAwardAndCertification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2942962945907036805L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "privacy_level", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PUBLIC'")
	@Enumerated(EnumType.STRING)
	private PrivacyLevelEnum privacyLevel;

	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_id", insertable = true, updatable = false , nullable = false)
	private Company company;
	
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

	public CompanyAwardAndCertification(String title, String description, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy, PrivacyLevelEnum privacyLevel) {
		super();
		this.title = title;
		this.description = description;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.privacyLevel = privacyLevel;
	}
}