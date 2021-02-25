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

import com.yuzee.company.enumeration.EntityType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "scholarship_eligible_entity", uniqueConstraints = @UniqueConstraint( columnNames = { "entity_id" , "company_scholarship_id"}, name = "UK_SEE_EI_CSI"),
indexes = { @Index (name = "IDX_COMPANY_SCHO_ELIG_ENTITY", columnList="company_scholarship_id", unique = false)})
public class ScholarshipEligibleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7144393613822021080L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "entity_id", nullable = false)
	private String entityId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "entity_type", nullable = false)
	private EntityType entityType;
	
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

	public ScholarshipEligibleEntity(String entityId, EntityType entityType, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.entityId = entityId;
		this.entityType = entityType;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
}