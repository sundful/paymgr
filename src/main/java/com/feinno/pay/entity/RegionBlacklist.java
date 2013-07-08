package com.feinno.pay.entity;

// Generated 2013-5-24 10:46:39

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.feinno.security.entity.IdEntity;

/**
 * RegionBlacklist
 */
@Entity
@Table(name = "pay_region_blacklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class RegionBlacklist extends IdEntity {

	private static final long serialVersionUID = 2563509244968951218L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id")
	private Channel channel;

	@Column(name = "province_name", length = 50)
	private String provinceName;

	@Column(name = "province_code", length = 50)
	private String provinceCode;

	@Column(name = "city_name", length = 50)
	private String cityName;

	@Column(name = "city_code", length = 50)
	private String cityCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	public RegionBlacklist() {
	}

	public RegionBlacklist(Date createTime) {
		this.createTime = createTime;
	}

	public RegionBlacklist(Channel channel, String provinceName,
			String provinceCode, String cityName, String cityCode,
			Date createTime) {
		this.channel = channel;
		this.provinceName = provinceName;
		this.provinceCode = provinceCode;
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.createTime = createTime;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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
