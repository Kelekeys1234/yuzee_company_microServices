package com.yuzee.company.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scholarship_eligible_level", uniqueConstraints = @UniqueConstraint( columnNames = { "level_name" , "company_scholarship_id"}, name = "UK_SEL_LN_CSI"),
indexes = { @Index (name = "IDX_COMPANY_SCHO_ELIG_LEVEL", columnList="company_scholarship_id", unique = false)})
public class ScholarshipEligibleLevel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -267017169239188131L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
		
	@Column(name = "level_name", nullable = false)
	private String levelName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_scholarship_id", insertable = true, updatable = false , nullable = false)
	private CompanyScholarship companyScholarship;
	
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

	public ScholarshipEligibleLevel(String levelName, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.levelName = levelName;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	

}
