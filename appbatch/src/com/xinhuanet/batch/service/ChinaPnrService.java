package com.xinhuanet.batch.service;

import java.util.List;

import com.xinhuanet.batch.po.ChinapnrProperties;
import com.xinhuanet.batch.po.Order;

/**
 * 
 * @author bahaidong
 *
 */
public interface ChinaPnrService {
	/**
	 * 汇付天下查询订单明细信息
	 * @param ordId 订单号
	 * @return ChinapnrProperties对象。
	 * <br/>1.签名/验签错误或异常返回，验签状态
	 * <br/>2.请求数据为null或""时返回，处理状态
	 * <br/>3.正确时返回汇付返回的结果
	 */
	public ChinapnrProperties queryOrder(String ordId);
	
	/**
	 * 汇付天下查询订单被支付状态
	 * @param ordId 订单号
	 * @return ChinapnrProperties对象。
	 * <br/>1.签名/验签错误或异常返回，验签状态
	 * <br/>2.请求数据为null或""时返回，处理状态
	 * <br/>3.正确时返回汇付返回的结果
	 */
	public ChinapnrProperties queryStatus(String ordId);
	
	/**
	 * 找到差异订单，并写到一个文件中
	 * 根据oders的订单id找到汇付天下的订单，在进行比较，先看状态，再看金额
	 * 	文件格式:第三方订单信息$pay_orders表订单信息：
	 * 		字段含义:钱管家交易唯一标识|订单号|订单金额|应答返回码|币种|退款次数|退款总金额|应答返回码$用户id|用户名|订单id|订单金额|订单提交时间|订单状态
	 * 		如：2014010203963207|20140102041529534081|70.00|000000|RMB|0|0.00|交易已支付（冻结）$2902353|duanwc|20140102041529534081|70.1|20140102161534|1
	 * @param orders
	 * @return
	 */
	public String findDiffWriteFile(List<Order> orders) ;
}
