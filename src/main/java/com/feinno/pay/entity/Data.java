package com.feinno.pay.entity;

// Generated 2013-5-27 21:34:54

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.feinno.security.entity.IdEntity;
import com.google.common.collect.Lists;

/**
 * PayData
 */
@Entity
@Table(name = "pay_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class Data extends IdEntity {

	private static final long serialVersionUID = -8555362312194193048L;

	@Column(name = "name", length = 20)
	private String name;

	@Column(name = "code", length = 20)
	private String code;

	@Column(name = "cp_id", length = 50)
	private String cpId;

	@Column(name = "cp_service_id", length = 50)
	private String cpServiceId;

	@Column(name = "consume_code", length = 50)
	private String consumeCode;

	@Column(name = "content_id", length = 50)
	private String contentId;

	@Column(name = "channel_id", length = 50)
	private String channelId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "data")
	private List<Product> product = Lists.newArrayList();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "data")
	private List<Channel> channel = Lists.newArrayList();

	public Data() {
	}

	public Data(Date createTime) {
		this.createTime = createTime;
	}

	public Data(String name, String code, String cpId, String cpServiceId,
			String consumeCode, String contentId, String channelId,
			Date createTime, List<Product> product, List<Channel> channel) {
		this.name = name;
		this.code = code;
		this.cpId = cpId;
		this.cpServiceId = cpServiceId;
		this.consumeCode = consumeCode;
		this.contentId = contentId;
		this.channelId = channelId;
		this.createTime = createTime;
		this.product = product;
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getCpServiceId() {
		return this.cpServiceId;
	}

	public void setCpServiceId(String cpServiceId) {
		this.cpServiceId = cpServiceId;
	}

	public String getConsumeCode() {
		return consumeCode;
	}

	public void setConsumeCode(String consumeCode) {
		this.consumeCode = consumeCode;
	}

	public String getContentId() {
		return this.contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Product> getProduct() {
		return this.product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public List<Channel> getChannel() {
		return channel;
	}

	public void setChannel(List<Channel> channel) {
		this.channel = channel;
	}

}
