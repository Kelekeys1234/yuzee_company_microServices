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
import javax.persistence.ManyToOne;
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
@Table(name = "scholarship_application_deadline")
public class ScholarshipApplicationDeadline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1387401087541587123L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "student_type", nullable = false)
	private com.yuzee.company.enumeration.ScholarshipApplicationDeadline scholarshipApplicationDeadline;
	
	@Column(name = "`key`", nullable = false)
	private String key;
	
	@Column(name = "`value`", nullable = false)
	private String value;
	
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
