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
@Table(name = "internship_member", uniqueConstraints = @UniqueConstraint( columnNames = { "user_id", "company_internship_id"}, name = "UK_CI_USER_ID_CII"),
indexes = { @Index (name = "IDX_COMPANY_INTERNSHIP_ID", columnList="company_internship_id", unique = false)})
public class InternshipMember implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -461629786038755444L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_internship_id", insertable = true, updatable = false , nullable = false)
	private CompanyInternship companyInternship;
	
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

	public InternshipMember(String userId, Date createdOn, Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.userId = userId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	

}
