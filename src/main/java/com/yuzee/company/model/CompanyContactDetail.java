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
@Table(name = "company_contact_detail", uniqueConstraints = @UniqueConstraint( columnNames = { "key", "company_location_id"}, name = "UK_CCD_KEY_CLI"),
indexes = { @Index (name = "IDX_COMPANY_LOCATION_COMPANY_ID", columnList="company_location_id", unique = false)})
public class CompanyContactDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7351584066595388761L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "`key`", nullable =  false)
	private String key;
	
	@Column(name = "`value`", nullable =  false)
	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "company_location_id", insertable = true, updatable = false , nullable = false)
	private CompanyLocation companyLocation;
	
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

	public CompanyContactDetail(String key, String value, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy) {
		super();
		this.key = key;
		this.value = value;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	

}
