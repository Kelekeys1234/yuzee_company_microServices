package com.yuzee.company.model;

import java.io.Serializable;
import java.sql.Time;
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
@Table(name = "company_working_hour", uniqueConstraints = @UniqueConstraint( columnNames = { "week_day","company_location_id"}, name = "UK_WEEK_DAY_LOCATION_ID"),
indexes = { @Index (name = "IDX_COMPANY_WORKING_HOUR_CLI", columnList="company_location_id", unique = false)})
public class CompanyWorkingHours implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5979724600985931922L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length=36)
	private String id;
	
	@Column(name = "week_day", nullable = false)
	private String weekDay;
	
	@Column(name = "opening_at", nullable = false)
	private Time openingAt;
	
	@Column(name = "closing_at", nullable = false)
	private Time closingAt;
	
	@Column(name = "is_off_day", nullable = false)
	private Boolean isOffDay = false;
	
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

	public CompanyWorkingHours(String weekDay, Time openingAt, Time closingAt, Boolean isOffDay, Date createdOn, Date updatedOn,
			String createdBy, String updatedBy) {
		super();
		this.weekDay = weekDay;
		this.isOffDay = isOffDay;
		this.openingAt = openingAt;
		this.closingAt = closingAt;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	
	


}
