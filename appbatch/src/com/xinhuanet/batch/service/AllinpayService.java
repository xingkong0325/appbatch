package com.xinhuanet.batch.service;

import java.util.List;

import com.xinhuanet.batch.po.HeadInfo;
import com.xinhuanet.batch.po.Order;
import com.xinhuanet.batch.po.ReturnOrder;

/**
 * 通联批量查询
 * 
 * @author bahaidong
 *
 */

public interface AllinpayService {

	/**
	 * 
	 * @param beginDateTime
	 *            格式：yyyymmddhh，例如：2013011600，目前只支持对当天订单进行查询
	 * @param endDateTime
	 *            格式：yyyymmddhh，例如：2013011600，查询时间范围为当天的00:00:00~23:59:59
	 * @param pageNo
	 *            查询页码从1开始
	 * @return
	 */
	public String batchQuery(String beginDateTime, String endDateTime, String pageNo);

	/**
	 * 查询全部结果集
	 * 
	 * @param beginDateTime
	 * @param endDateTime
	 * @param pageNo
	 * @return
	 */
	public List<ReturnOrder> getResult(String beginDateTime, String endDateTime, String pageNo);
	
	/**
	 * 找到差异订单，并写到一个文件中
	 * 目前是先找到订单号相同的订单， 先判断状态，再判断金额与状态是否一致，如果不一致将记录写入到文件中去:
	 * 	文件格式:第三方订单信息$pay_orders表订单信息：
	 * 		字段含义:商户号|通联订单号|商户订单号|商户订单提交时间|商户订单金额|支付完成时间|订单实际支付金额|处理结果$用户id|用户名|订单id|订单金额|订单提交时间|订单状态
	 * 		如：100020091218001|201408141351303291|20140814015552683367|33.33|20140814015552|33.33|20140814135132|33.33|1$92263595|donglin0325|20140814015552683367|33.13|20140814135552|1
	 * @param returnOrders
	 * @param orders
	 * @return
	 */
	public String findDiffWriteFile(List<ReturnOrder> returnOrders, List<Order> orders) ;
	
	/**
	 * 获得第三方返回的订单信息
	 * @return
	 */
	public List<ReturnOrder> getReturnOrders();
	
	/**
	 * 返回第三方头部信息
	 * @return
	 */
	public List<HeadInfo> getHeadInfos();

}
