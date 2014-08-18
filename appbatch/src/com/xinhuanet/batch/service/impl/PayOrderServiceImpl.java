package com.xinhuanet.batch.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinhuanet.batch.dao.PayOrderDao;
import com.xinhuanet.batch.po.Order;
import com.xinhuanet.batch.service.PayOrderService;

@Component
public class PayOrderServiceImpl implements PayOrderService{
	
	@Autowired
	private PayOrderDao payOrderDao;

	@Override
	public List<Order> qureyListOrder(String beginDateTime, String endDateTime, String paytype){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		beginDateTime = beginDateTime + "0000";
		endDateTime = endDateTime + "5959";
		List<Order> orders = null;
		try {
			Date beginTime = format.parse(beginDateTime);
			Date endTime = format.parse(endDateTime);
			orders = payOrderDao.qureyListOrder(beginTime, endTime, paytype);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return orders;
	}
}
