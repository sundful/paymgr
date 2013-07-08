package com.feinno.pay.entity;

// Generated 2013-6-25 15:36:16

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
 * OrderDay
 */
@Entity
@Table(name = "pay_order_day")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class OrderDay extends IdEntity {

	private static final long serialVersionUID = 5817977556106676902L;

	@Column(name = "channel", length = 10)
	private String channel;

	@Column(name = "percent")
	private Integer percent;

	@Column(name = "money")
	private Integer money;

	@Column(name = "total_count")
	private Integer totalCount;
	
	@Column(name = "real_success_count")
	private Integer realSuccessCount;
	
	@Column(name = "cp_success_count")
	private Integer cpSuccessCount;

	@Column(name = "real_total_money")
	private Integer realTotalMoney;

	@Column(name = "cp_total_money")
	private Integer cpTotalMoney;

	@Temporal(TemporalType.DATE)
	@Column(name = "stat_date", length = 10)
	private Date statDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	public OrderDay() {
	}

	public OrderDay(Date createTime) {
		this.createTime = createTime;
	}

	public OrderDay(String channel, Integer percent, Integer money,
			Integer totalCount, Integer realSuccessCount,
			Integer cpSuccessCount, Integer realTotalMoney,
			Integer cpTotalMoney, Date statDate, Date createTime) {
		this.channel = channel;
		this.percent = percent;
		this.money = money;
		this.totalCount = totalCount;
		this.realSuccessCount = realSuccessCount;
		this.cpSuccessCount = cpSuccessCount;
		this.realTotalMoney = realTotalMoney;
		this.cpTotalMoney = cpTotalMoney;
		this.statDate = statDate;
		this.createTime = createTime;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getPercent() {
		return this.percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getRealSuccessCount() {
		return this.realSuccessCount;
	}

	public void setRealSuccessCount(Integer realSuccessCount) {
		this.realSuccessCount = realSuccessCount;
	}

	public Integer getCpSuccessCount() {
		return this.cpSuccessCount;
	}

	public void setCpSuccessCount(Integer cpSuccessCount) {
		this.cpSuccessCount = cpSuccessCount;
	}

	public Integer getRealTotalMoney() {
		return this.realTotalMoney;
	}

	public void setRealTotalMoney(Integer realTotalMoney) {
		this.realTotalMoney = realTotalMoney;
	}

	public Integer getCpTotalMoney() {
		return this.cpTotalMoney;
	}

	public void setCpTotalMoney(Integer cpTotalMoney) {
		this.cpTotalMoney = cpTotalMoney;
	}

	public Date getStatDate() {
		return this.statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
