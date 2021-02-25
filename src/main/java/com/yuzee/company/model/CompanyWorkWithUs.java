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

import com.yuzee.company.enumeration.WorkWithUs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_work_with_us", uniqueConstraints = @UniqueConstraint( columnNames = { "work_with_us", "company_id"}, name = "UK_CWWS_WWS_CI"),
indexes = { @Index (name = "IDX_COMPANY_WORK_WITH_US_CI", columnList="company_id", unique = false),
		@Index (name = "IDX_COMPANY_WORK_WITH_US_WWS_ID", columnList="work_with_us,id", unique = true),
@Index (name = "IDX_COMPANY_WORK_WITH_US_CI_ID", columnList="company_id,id", unique = true)})
public class CompanyWorkWithUs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8095201452388870978L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "work_with_us", nullable = false)
	private WorkWithUs workWithUs;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_id", insertable = true, updatable = false , nullable = false)
	private Company company;

	@Column(name = "description", nullable = false,columnDefinition = "text")
	private String description;
	
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

	public CompanyWorkWithUs(WorkWithUs workWithUs, Company company, String description, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.workWithUs = workWithUs;
		this.company = company;
		this.description = description;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}	
}
