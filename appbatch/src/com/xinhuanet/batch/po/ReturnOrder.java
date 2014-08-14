package com.xinhuanet.batch.po;

import java.io.Serializable;
import java.util.Date;

public class ReturnOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商户号
	 */
	String merchantId;
	/**
	 * 通联订单号
	 */
	String allinpayId;
	/**
	 * 商户订单号
	 */
	String orderId;
	/**
	 * 商户订单提交时间
	 */
	Date orderComTime;
	/**
	 * 商户订单金额
	 */
	double orderMoney;
	/**
	 * 支付完成时间
	 */
	Date orderSucTime;
	/**
	 * 订单实际支付金额
	 */
	double payMoney;
	/**
	 * 扩展字段1
	 */
	String str1;
	/**
	 * 扩展字段2
	 */
	String str2;
	/**
	 * 处理结果
	 */
	String flag;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAllinpayId() {
		return allinpayId;
	}
	public void setAllinpayId(String allinpayId) {
		this.allinpayId = allinpayId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderComTime() {
		return orderComTime;
	}
	public void setOrderComTime(Date orderComTime) {
		this.orderComTime = orderComTime;
	}
	public double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public Date getOrderSucTime() {
		return orderSucTime;
	}
	public void setOrderSucTime(Date orderSucTime) {
		this.orderSucTime = orderSucTime;
	}
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	public String getStr1() {
		return str1;
	}
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	public String getStr2() {
		return str2;
	}
	public void setStr2(String str2) {
		this.str2 = str2;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "ReturnOrder [merchantId=" + merchantId + ", allinpayId="
				+ allinpayId + ", orderId=" + orderId + ", orderComTime="
				+ orderComTime + ", orderMoney=" + orderMoney
				+ ", orderSucTime=" + orderSucTime + ", payMoney=" + payMoney
				+ ", str1=" + str1 + ", str2=" + str2 + ", flag=" + flag + "]";
	}

}
