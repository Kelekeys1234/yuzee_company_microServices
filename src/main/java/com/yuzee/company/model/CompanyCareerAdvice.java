package com.yuzee.company.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_career_advice", uniqueConstraints = @UniqueConstraint( columnNames = { "title", "company_id"}, name = "UK_CCAD_TITLE_CI"),
indexes = { @Index (name = "IDX_COMPANY_CAREER_ADVICE_COMPANY_ID_ID", columnList="company_id,id", unique = true)})
public class CompanyCareerAdvice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8330706669461119390L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false, columnDefinition = "text")
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companyCareerAdvice")
	private List<CompanyEmployee> listOfCompanyEmployee = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companyCareerAdvice")
	private List<CompanyCareerAdviceCollaboration> listOfCompanyCareerAdviceCollaboration = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companyCareerAdvice")
	private List<CompanyPreferredCourse> listOfCompanyPreferredCourse = new ArrayList<>(); 
	
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

	public CompanyCareerAdvice(String title, String description, Company company, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.title = title;
		this.description = description;
		this.company = company;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	public void addCompanyEmployee(CompanyEmployee companyEmployee) {
		companyEmployee.setCompanyCareerAdvice(this);
		this.listOfCompanyEmployee.add(companyEmployee);
	}
	
	public void addCompanyCareerAdviceCollaboration (CompanyCareerAdviceCollaboration companyCareerAdviceCollaboration) {
		companyCareerAdviceCollaboration.setCompanyCareerAdvice(this);
		this.listOfCompanyCareerAdviceCollaboration.add(companyCareerAdviceCollaboration);
	}
	
	public void addCompanyPreferredCourse (CompanyPreferredCourse companyPreferredCourse) {
		companyPreferredCourse.setCompanyCareerAdvice(this);
		this.listOfCompanyPreferredCourse.add(companyPreferredCourse);
	}
}