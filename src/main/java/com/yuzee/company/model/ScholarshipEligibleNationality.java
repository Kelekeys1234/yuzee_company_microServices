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
@Table(name = "scholarship_eligible_nationality", uniqueConstraints = @UniqueConstraint( columnNames = { "nationality" , "company_scholarship_id"}, name = "UK_SEN_N_CSI"),
indexes = { @Index (name = "IDX_COMPANY_SCHO_ELIG_NATIONALITY", columnList="company_scholarship_id", unique = false)})
public class ScholarshipEligibleNationality implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5533629328763827079L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "nationality", nullable = false)
	private String nationality;
	
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

}
