/**
 * create on 2011-12-13
 */
package com.feinno.pay.entity;

import java.io.Serializable;

/**
 * @author 孙维维(sundful)
 * 
 */
public class BackMsg implements Serializable {

	private static final long serialVersionUID = 6471190498759416962L;

	private String resultCode;
	private String resultMsg;
	private String sessionkey;
	private String orderid;

	public BackMsg() {
		super();
	}

	public BackMsg(String resultCode, String resultMsg, String sessionkey,
			String orderid) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.sessionkey = sessionkey;
		this.orderid = orderid;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}
