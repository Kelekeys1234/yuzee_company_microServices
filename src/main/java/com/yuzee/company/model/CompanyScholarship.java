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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.yuzee.company.enumeration.Gender;
import com.yuzee.company.enumeration.PrivacyLevelEnum;
import com.yuzee.company.enumeration.ScholarshipCoverage;
import com.yuzee.company.enumeration.ScholarshipValidity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_scholarship", uniqueConstraints = @UniqueConstraint( columnNames = { "title", "company_id"}, name = "UK_CS_TITLE_CI"))
public class CompanyScholarship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7719609437598808352L;
	
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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "scholarship_validity")
	private ScholarshipValidity scholarshipValidity;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "scholarship_coverage")
	private ScholarshipCoverage scholarshipCoverage;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "scholarship_amount")
	private Double scholarshipAmount;
	
	@Column(name = "percentage")
	private Double percentage;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "eligible_gender")
	private Gender gender;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "application_dead_line")
	private Date applicationDeadLine;
	
	@Column(name = "general_requirements" , columnDefinition = "text")
	private String generalRequirements;
	
	@Column(name = "eligible_nationality")
	private String eligbleNationality;
	
	@Column(name = "how_to_apply" , columnDefinition = "text")
	private String howToApply;
	
	@Column(name = "benefits" , columnDefinition = "text")
	private String benefits;
	
	@Column(name = "conditions" , columnDefinition = "text")
	private String conditions;
	
	@Column(name = "successful_candidate_requirment" , columnDefinition = "text")
	private String successfulCandidateRequirment;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyScholarship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipEligibleCountry> listOfScholarshipEligibleCountry = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyScholarship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipEligibleLevel> listOfScholarshipEligibleLevel = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyScholarship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipEligibleEntity> listOfScholarshipEligibleEntity = new ArrayList<>();
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyScholarship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipRequiredLanguage> listOfScholarshipRequiredLanguage = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyScholarship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ScholarshipApplicationDeadline> listOfScholarshipApplicationDeadline = new ArrayList<>();
	
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

	
	public void addEligibleLevel(ScholarshipEligibleLevel scholarshipEligibleLevel) {
		listOfScholarshipEligibleLevel.add(scholarshipEligibleLevel);
		scholarshipEligibleLevel.setCompanyScholarship(this);
	}
	
	public void addEligibleCountry(ScholarshipEligibleCountry scholarshipEligibleCountry) {
		listOfScholarshipEligibleCountry.add(scholarshipEligibleCountry);
		scholarshipEligibleCountry.setCompanyScholarship(this);
	}

	
	public void addEligibleLanguage(ScholarshipRequiredLanguage scholarshipRequiredLanguage) {
		listOfScholarshipRequiredLanguage.add(scholarshipRequiredLanguage);
		scholarshipRequiredLanguage.setCompanyScholarship(this);
	}

	public void addEligibleEntity(ScholarshipEligibleEntity scholarshipEligibleEntity) {
		listOfScholarshipEligibleEntity.add(scholarshipEligibleEntity);
		scholarshipEligibleEntity.setCompanyScholarship(this);
	}
	
	public CompanyScholarship(String title, String description, ScholarshipValidity scholarshipValidity,
			ScholarshipCoverage scholarshipCoverage, String currency, Double scholarshipAmount, Double percentage,
			Company company, Date createdOn, Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.title = title;
		this.description = description;
		this.scholarshipValidity = scholarshipValidity;
		this.scholarshipCoverage = scholarshipCoverage;
		this.currency = currency;
		this.scholarshipAmount = scholarshipAmount;
		this.percentage = percentage;
		this.company = company;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}
