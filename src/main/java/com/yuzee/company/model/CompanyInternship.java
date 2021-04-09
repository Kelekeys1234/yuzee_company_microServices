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
@Table(name = "company_internship", uniqueConstraints = @UniqueConstraint( columnNames = { "title", "company_id"}, name = "UK_CINT_TITLE_CI"),
indexes = { @Index (name = "IDX_COMPANY_INTERNSHIP_COMPANY_ID_ID", columnList="company_id,id", unique = true)})
public class CompanyInternship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7001005089511752649L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false,columnDefinition = "text")
	private String description;
	
	@Column(name = "education_need",columnDefinition = "text")
	private String educationNeed;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyInternship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InternshipMember> listOfInternshipMember = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyInternship" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InternshipSkill> listOfInternshipSkill = new ArrayList<>();
	
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

	public CompanyInternship(String title, String description, Company company, Date createdOn, Date updatedOn,
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

	public void addInternshipMember(List<InternshipMember> listOfInternshipMember) {
		listOfInternshipMember.stream().forEach(internshipMember -> {
			internshipMember.setCompanyInternship(this);
			this.listOfInternshipMember.add(internshipMember);
		});
	}
	
	public void addInternshipSkill(InternshipSkill internshipSkill) {
		internshipSkill.setCompanyInternship(this);
		this.listOfInternshipSkill.add(internshipSkill);
	}
}
