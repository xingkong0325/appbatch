package com.xinhuanet.batch.dao;

import java.util.Date;
import java.util.List;

import com.xinhuanet.batch.po.Order;

public interface PayOrderDao {
	/**
	 * 查询pay_orders表的明细信息
	 * @param beginTime	开始时间
	 * @param endTime	结束时间
	 * @param paytype	第三方网关
	 * @return
	 */
	public List<Order> qureyListOrder(Date beginTime, Date endTime, String paytype);
}
