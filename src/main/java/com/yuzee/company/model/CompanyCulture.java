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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_culture")
public class CompanyCulture implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2363157497224023828L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
			
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "companyCulture", cascade = CascadeType.ALL, orphanRemoval = true)
	private CompanyVision companyVision;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "companyCulture", cascade = CascadeType.ALL, orphanRemoval = true)
	private CompanyMission companyMission;
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companyCulture")
	private List<CompanyCulturePeople> listOfCompanyCulturePeople = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
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

	public CompanyCulture( Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	public void addCompanyMission(CompanyMission companyMission) {
		companyMission.setCompanyCulture(this);
		this.setCompanyMission(companyMission);
	}
	
	public void addCompanyVision(CompanyVision companyVision) {
		companyVision.setCompanyCulture(this);
		this.setCompanyVision(companyVision);
	}
	
	
}