package com.xinhuanet.batch.service;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xinhuanet.batch.po.ChinapnrProperties;
import com.xinhuanet.batch.po.Order;

public class TestChinaPnrService {
	
	private static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

	@Test
	public void testQueryOrder() {
		ChinaPnrService chinapnrService = ctx.getBean(ChinaPnrService.class);
		String ordId = "20140102110802222234";
		ChinapnrProperties chinapnr = chinapnrService.queryOrder(ordId);
		System.out.println("返回结果Id:" + chinapnr.getOrdId());
	}
	
	@Test
	public void testQueryStatus() {
		ChinaPnrService chinapnrService = ctx.getBean(ChinaPnrService.class);
		String ordId = "20140102110802222234";
		ChinapnrProperties chinapnr = chinapnrService.queryStatus(ordId);
		System.out.println("返回结果订单状态:" + chinapnr.getProcStat());
	}
	
	@Test
	public void testFindDiffWriteFile() {
		ChinaPnrService chinapnrService = ctx.getBean(ChinaPnrService.class);
		PayOrderService payOrderService = ctx.getBean(PayOrderService.class);
		String beginDateTime = "2014010200";
		String endDateTime = "2014010223";
		String paytype = "chinapnr";
		List<Order> orders = payOrderService.qureyListOrder(beginDateTime, endDateTime, paytype);
		String str = chinapnrService.findDiffWriteFile(orders);
		System.out.println("返回结果:\r\n" + str);
	}

}
