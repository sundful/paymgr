package com.feinno.pay.entity;

// Generated 2013-5-27 21:34:54

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
 * PayOrder
 */
@Entity
@Table(name = "pay_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.feinno.pay.entity")
public class Order extends IdEntity {

	private static final long serialVersionUID = 2487667937430531398L;

	@Column(name = "channel", length = 10)
	private String channel;

	@Column(name = "phone", length = 13)
	private String phone;

	@Column(name = "money")
	private Integer money;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	private Date createTime;

	@Column(name = "order_id", length = 64)
	private String orderId;

	@Column(name = "status", length = 10)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notify_time", length = 19)
	private Date notifyTime;

	public Order() {
	}

	public Order(Date createTime) {
		this.createTime = createTime;
	}

	public Order(String channel, String phone, Integer money, Date createTime,
			String orderId, String status, Date notifyTime) {
		this.channel = channel;
		this.phone = phone;
		this.money = money;
		this.createTime = createTime;
		this.orderId = orderId;
		this.status = status;
		this.notifyTime = notifyTime;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getNotifyTime() {
		return this.notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

}
