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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_partner", uniqueConstraints = @UniqueConstraint( columnNames = { "entity_id","entity_type","company_partner_primary_detail_id"}, name = "UK_CP_EI_ET_CPPDI"),
indexes = { @Index (name = "IDX_COMPANY_PATNER_CPPDI", columnList="company_partner_primary_detail_id", unique = false)})
public class CompanyPartner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7364630144212162677L;
	
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
	@JoinColumn(name = "company_partner_primary_detail_id")
	private CompanyPartnerPrimaryDetail companyPartnerPrimaryDetail;

	public CompanyPartner(String entityId, EntityType entityType, Date createdOn, Date updatedOn, String createdBy,
			String updatedBy, CompanyPartnerPrimaryDetail companyPartnerPrimaryDetail) {
		super();
		this.entityId = entityId;
		this.entityType = entityType;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.companyPartnerPrimaryDetail = companyPartnerPrimaryDetail;
	}
}