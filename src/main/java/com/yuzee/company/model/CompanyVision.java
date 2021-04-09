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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.yuzee.company.enumeration.PrivacyLevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_vision")
public class CompanyVision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5742446996036664927L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "privacy_level", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PUBLIC'")
	@Enumerated(EnumType.STRING)
	private PrivacyLevelEnum privacyLevel;
	
	@Column(name = "vision", nullable = false, columnDefinition = "text")
	private String vision;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_culture_id", insertable = true, updatable = false , nullable = false)
	private CompanyCulture companyCulture;
	
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

	public CompanyVision(PrivacyLevelEnum privacyLevel, String vision, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.privacyLevel = privacyLevel;
		this.vision = vision;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
