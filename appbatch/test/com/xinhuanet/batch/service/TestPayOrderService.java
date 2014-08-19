package com.xinhuanet.batch.service;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xinhuanet.batch.po.Order;

public class TestPayOrderService {
	
	private static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

	@Test
	public void testQureyListOrder() {
		PayOrderService payOrderService = (PayOrderService)ctx.getBean("payOrderServiceImpl");
		String beginDateTime = "2014081800";
		String endDateTime = "2014081823";
		String paytype = "allinpay";
		List<Order> orders = payOrderService.qureyListOrder(beginDateTime, endDateTime, paytype);
		System.out.println("返回结果:\r\n" + orders.get(0).getId());
	}

}
