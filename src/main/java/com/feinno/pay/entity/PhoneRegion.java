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
 * PhoneRegion
 */
@Entity
@Table(name = "pay_phone_region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class PhoneRegion extends IdEntity {

	private static final long serialVersionUID = -6032537132433094970L;

	@Column(name = "phone_part", length = 20)
	private String phonePart;

	@Column(name = "province_code", length = 20)
	private String provinceCode;

	@Column(name = "city_code", length = 20)
	private String cityCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	public PhoneRegion() {
	}

	public PhoneRegion(Date createTime) {
		this.createTime = createTime;
	}

	public PhoneRegion(String phonePart, String provinceCode, String cityCode,
			Date createTime) {
		this.phonePart = phonePart;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.createTime = createTime;
	}

	public String getPhonePart() {
		return this.phonePart;
	}

	public void setPhonePart(String phonePart) {
		this.phonePart = phonePart;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
