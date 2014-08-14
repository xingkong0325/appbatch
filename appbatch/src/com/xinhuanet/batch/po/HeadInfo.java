package com.xinhuanet.batch.po;

import java.io.Serializable;

public class HeadInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商户号
	 */
	String merchantId;
	/**
	 * 记录数
	 */
	int count;
	/**
	 * 页码
	 */
	int pageNo;
	/**
	 * 是否有下一页 ，Y/N
	 */
	boolean flag;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
