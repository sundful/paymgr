package com.feinno.pay.entity;

// Generated 2013-5-27 21:34:54

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
 * PayChannel
 */
@Entity
@Table(name = "pay_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class Channel extends IdEntity {

	private static final long serialVersionUID = -8089794604080930991L;

	@Column(name = "code", length = 10)
	private String code;

	@Column(name = "name", length = 40)
	private String name;

	@Column(name = "percent", length = 20)
	private Integer percent;

	@Column(name = "ip_allow", length = 30)
	private String ipAllow;

	@Column(name = "request_key", length = 32)
	private String requestKey;

	@Column(name = "callback_key", length = 32)
	private String callbackKey;

	@Column(name = "enable")
	private Boolean enable;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "data_id")
	private Data data;

	public Channel() {
	}

	public Channel(Date createTime) {
		this.createTime = createTime;
	}

	public Channel(String code, String name, Integer percent, String ipAllow,
			String requestKey, String callbackKey, Boolean enable, Data data) {
		this.code = code;
		this.name = name;
		this.percent = percent;
		this.ipAllow = ipAllow;
		this.requestKey = requestKey;
		this.callbackKey = callbackKey;
		this.enable = enable;
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPercent() {
		return this.percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public String getIpAllow() {
		return this.ipAllow;
	}

	public void setIpAllow(String ipAllow) {
		this.ipAllow = ipAllow;
	}

	public String getRequestKey() {
		return this.requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getCallbackKey() {
		return this.callbackKey;
	}

	public void setCallbackKey(String callbackKey) {
		this.callbackKey = callbackKey;
	}

	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
