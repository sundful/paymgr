package com.feinno.pay.entity;

// Generated 2013-5-24 10:46:39

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.feinno.security.entity.IdEntity;

/**
 * Region
 */
@Entity
@Table(name = "pay_region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class Region extends IdEntity {

	private static final long serialVersionUID = -1588119597947861401L;

	@Column(name = "region_name", length = 50)
	private String regionName;

	@Column(name = "region_code", length = 50)
	private String regionCode;

	@Column(name = "region_type", length = 20)
	private String regionType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	public Region() {
	}

	public Region(Date createTime) {
		this.createTime = createTime;
	}

	public Region(String regionName, String regionCode, String regionType,
			Date createTime) {
		this.regionName = regionName;
		this.regionCode = regionCode;
		this.regionType = regionType;
		this.createTime = createTime;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionType() {
		return this.regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
