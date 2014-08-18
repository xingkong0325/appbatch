package com.xinhuanet.batch.service;

import java.util.List;
import com.xinhuanet.batch.po.Order;


public interface PayOrderService {

	/**
	 * 查询pay_orders表的明细信息
	 * @param beginDateTime	开始时间,格式：'2014081800'
	 * @param endDateTime	结束时间，格式：'2014081823'
	 * @param paytype	第三方网关
	 * @return
	 */
	public List<Order> qureyListOrder(String beginDateTime, String endDateTime, String paytype);
}