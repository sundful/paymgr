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
 * PhoneBlacklist
 */
@Entity
@Table(name = "pay_phone_blacklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class PhoneBlacklist extends IdEntity {

	private static final long serialVersionUID = -2681867597864527302L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id")
	private Channel channel;

	@Column(name = "phone_num", length = 11)
	private String phoneNum;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	public PhoneBlacklist() {
	}

	public PhoneBlacklist(Date createTime) {
		this.createTime = createTime;
	}

	public PhoneBlacklist(Channel channel, String phoneNum, Date createTime) {
		this.channel = channel;
		this.phoneNum = phoneNum;
		this.createTime = createTime;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
