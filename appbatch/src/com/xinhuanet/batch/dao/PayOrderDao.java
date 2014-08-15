package com.xinhuanet.batch.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xinhuanet.batch.common.BaseDAO;
import com.xinhuanet.batch.mapper.OrdersRowMapper;
import com.xinhuanet.batch.po.Order;

@Component
public class PayOrderDao extends BaseDAO{
	//private static final Logger logger = Logger.getLogger(PayOrderDao.class);
	
	public List<Order> qureyListOrder(Date beginTime, Date endTime, String paytype){
		//a.paystatus='1' and 
		String sql = "select * from pay_orders a where a.paytime>=? and a.paytime<=? and a.paytype=?";
		Object[] params = new Object[]{beginTime, endTime, paytype};
		List<Order> orders = getJdbcTemplate(READ).query(sql, params, new OrdersRowMapper());;
		return orders;
	}
}
