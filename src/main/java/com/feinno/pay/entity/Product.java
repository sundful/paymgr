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
 * PayProduct
 */
@Entity
@Table(name = "pay_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class Product extends IdEntity {

	private static final long serialVersionUID = 3273295664634664578L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "data_id")
	private Data data;

	@Column(name = "code", length = 50)
	private String code;

	@Column(name = "money")
	private Integer money;

	@Column(name = "description", length = 50)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	public Product() {
	}

	public Product(Date createTime) {
		this.createTime = createTime;
	}

	public Product(Data data, String code, Integer money, String description,
			Date createTime) {
		this.data = data;
		this.code = code;
		this.money = money;
		this.description = description;
		this.createTime = createTime;
	}

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
