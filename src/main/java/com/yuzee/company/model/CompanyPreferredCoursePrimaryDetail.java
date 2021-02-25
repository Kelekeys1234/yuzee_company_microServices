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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "company_preferred_course_primary_detail", uniqueConstraints = @UniqueConstraint( columnNames = { "company_id"}, name = "UK_CPCPD_CI"))
public class CompanyPreferredCoursePrimaryDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9220337656283025983L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "privacy_level", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PUBLIC'")
	@Enumerated(EnumType.STRING)
	private PrivacyLevelEnum privacyLevel;

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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_id", insertable = true, updatable = false , nullable = false)
	private Company company;
	
	@OneToMany(mappedBy = "companyPreferredCoursePrimaryDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyPreferredCourse> listOfCompanyPreferredCourse = new ArrayList<>();

	public CompanyPreferredCoursePrimaryDetail(PrivacyLevelEnum privacyLevel, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.privacyLevel = privacyLevel;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	public void addCompany(Company company) {
		this.company = company;
		company.setCompanyPreferredCoursePrimaryDetail(this);
	}
	
	public void addCompanyPreferredCourse(CompanyPreferredCourse companyPreferredCourse) {
		this.listOfCompanyPreferredCourse.add(companyPreferredCourse);
		companyPreferredCourse.setCompanyPreferredCoursePrimaryDetail(this);
	}
}
