package com.xinhuanet.batch;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xinhuanet.batch.po.Order;
import com.xinhuanet.batch.po.ReturnOrder;
import com.xinhuanet.batch.service.AllinpayService;
import com.xinhuanet.batch.service.AllinpayServiceTest;
import com.xinhuanet.batch.service.PayOrderService;

public class TestAllinpayBatch {
	
	private static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

	@Test
	public void testBatchQuery() {
		AllinpayService allinpayService = (AllinpayService)ctx.getBean("allinpayService");
		String beginDateTime = "2014081400";
		String endDateTime = "2014081423";
		String pageNo = "1";
		String str = allinpayService.batchQuery(beginDateTime, endDateTime, pageNo);
		System.out.println("返回结果:\r\n" + str);
	}
	
	@Test
	public void testGetResultTest() {
		AllinpayServiceTest allinpayService = (AllinpayServiceTest)ctx.getBean("allinpayServiceTest");
		String beginDateTime = "2014081400";
		String endDateTime = "2014081423";
		String pageNo = "1";
		List<ReturnOrder> returnOrders = allinpayService.getResult(beginDateTime, endDateTime, pageNo);
		System.out.println("返回结果:\r\n" + returnOrders.size());
	}
	
	@Test
	public void testQureyListOrder() {
		PayOrderService payOrderService = (PayOrderService)ctx.getBean("payOrderService");
		String beginDateTime = "2014081400";
		String endDateTime = "2014081423";
		String paytype = "allinpay";
		List<Order> orders = payOrderService.qureyListOrder(beginDateTime, endDateTime, paytype);
		System.out.println("返回结果:\r\n" + orders.get(0).getId());
	}
	
	@Test
	public void testString() {
		String str = "18962532835|asd|34234|njhu|||1";
		String[] strs = str.split("\\|");
		System.out.println("返回结果:\r\n" + strs[0]);
	}
	
	@Test
	public void testFindDiffWriteFile() {
		AllinpayServiceTest allinpayService = (AllinpayServiceTest)ctx.getBean("allinpayServiceTest");
		PayOrderService payOrderService = (PayOrderService)ctx.getBean("payOrderService");
		String beginDateTime = "2014081400";
		String endDateTime = "2014081423";
		String paytype = "allinpay";
		String pageNo = "1";
		List<ReturnOrder> returnOrders = allinpayService.getResult(beginDateTime, endDateTime, pageNo);
		List<Order> orders = payOrderService.qureyListOrder(beginDateTime, endDateTime, paytype);
		String str = allinpayService.findDiffWriteFile(returnOrders, orders);
		System.out.println("返回结果:\r\n" + str);
	}

}
