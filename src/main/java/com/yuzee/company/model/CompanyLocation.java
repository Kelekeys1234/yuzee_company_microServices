package com.yuzee.company.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.yuzee.company.enumeration.PrivacyLevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_location", uniqueConstraints = @UniqueConstraint( columnNames = { "name","city","state" , "country", "company_id"}, name = "UK_CL_NAME_CITY_STATE_COUNTRY_COMPANY_ID"),
indexes = { @Index (name = "IDX_COMPANY_LOCATION_COMPANY_ID_ID", columnList="company_id,id", unique = true),
		@Index (name = "IDX_COMPANY_LOCATION_COMPANY_ID_PRIVACY_LEVEL", columnList="company_id,privacy_level", unique = false)})
public class CompanyLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3003883378769918501L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "privacy_level", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'PUBLIC'")
	@Enumerated(EnumType.STRING)
	private PrivacyLevelEnum privacyLevel;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "postalCode")
	private String postalCode;
	
	@Column(name = "latitude", nullable = false)
	private Double latitude;
	
	@Column(name = "longitude", nullable = false)
	private Double longitude;
	
	@Column(name = "is_primary")
	private Boolean isPrimary;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyLocation" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyContactDetail> listOfCompanyContactDetail = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "companyLocation" , cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompanyWorkingHours> listOfCompanyWorkingHours = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	public void addContactDetails(CompanyContactDetail companyContactDetail) {
		listOfCompanyContactDetail.add(companyContactDetail);
		companyContactDetail.setCompanyLocation(this);
	}

	public void addCompanyWorkingHours(CompanyWorkingHours companyWorkingHours) {
		listOfCompanyWorkingHours.add(companyWorkingHours);
		companyWorkingHours.setCompanyLocation(this);
	}
	
	public CompanyLocation(PrivacyLevelEnum privacyLevel, String name, String address, String city, String state, String country, String postalCode ,  Double latitude,
			Double longitude, Boolean isPrimary, Date createdOn, Date updatedOn, String createdBy, String updatedBy) {
		super();
		this.privacyLevel = privacyLevel;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isPrimary = isPrimary;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.postalCode = postalCode;
	}
}
