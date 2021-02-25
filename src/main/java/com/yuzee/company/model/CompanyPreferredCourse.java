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
@Table(name = "company_preferred_course", uniqueConstraints = @UniqueConstraint( columnNames = { "course_id","course_name","company_preferred_course_primary_detail_id"}, name = "UK_CPC_CI_CN_CPCPDII"),
indexes = { @Index (name = "IDX_COMPANY_PREFERRED_COURSE_CPCPD", columnList="company_preferred_course_primary_detail_id", unique = false)})
public class CompanyPreferredCourse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5547526321763417776L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "course_name", nullable = false)
	private String courseName;
		
	@Column(name = "course_id", nullable = false)
	private String courseId;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_preferred_course_primary_detail_id")
	private CompanyPreferredCoursePrimaryDetail companyPreferredCoursePrimaryDetail;

	public CompanyPreferredCourse(String courseName, Date createdOn, Date updatedOn, String createdBy, String updatedBy,
			 String courseId) {
		super();
		this.courseName = courseName;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.courseId = courseId;
	}
}