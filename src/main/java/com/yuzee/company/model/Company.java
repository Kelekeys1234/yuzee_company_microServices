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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.yuzee.company.enumeration.IndustryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company", uniqueConstraints = @UniqueConstraint( columnNames = { "company_name"}, name = "UK_COMPANY_NAME"))
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6680304362302742436L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "is_verified")
	public boolean isVerified = false;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "tag_line")
	private String tagLine;
	
	@Column(name = "about_us")
	private String aboutUs;
	
	@Column(name = "industry")
	private String industry;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "industry_type")
	private IndustryType industryType;
	
	@Column(name = "year_founder")
	private Integer yearFounder;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private CompanyPreferredCoursePrimaryDetail companyPreferredCoursePrimaryDetail;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private CompanyPartnerPrimaryDetail companyPartnerPrimaryDetail;
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanySpeciality> listOfCompanySpeciality = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyWorkWithUs> listOfCompanyWorkWithUs = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyLocation> listOfCompanyLocation = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyAchievement> listOfCompanyAchievement = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyAwardAndCertification> listOfCompanyAwardAndCertification = new ArrayList<>();
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyScholarship> listOfCompanyScholarship = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyStaffInterview> listOfCompanyStaffInterview = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyCareerAdvice> listOfCompanyCareerAdvice = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyInternship> listOfCompanyInternship = new ArrayList<>();
	
	
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
	
	public void addCompanyLocation(CompanyLocation companyLocation) {
		listOfCompanyLocation.add(companyLocation);
		companyLocation.setCompany(this);
	}
	
	public void addCompanyAchievement(CompanyAchievement companyAchievement) {
		listOfCompanyAchievement.add(companyAchievement);
		companyAchievement.setCompany(this);
	}
	
	public void addCompanySpeciality(CompanySpeciality companySpeciality) {
		listOfCompanySpeciality.add(companySpeciality);
		companySpeciality.setCompany(this);
	}
}