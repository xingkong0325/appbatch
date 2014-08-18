package com.xinhuanet.batch.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allinpay.ets.client.SecurityUtil;
import com.allinpay.ets.client.util.Base64;
import com.xinhuanet.batch.common.Http4Client;
import com.xinhuanet.batch.po.HeadInfo;
import com.xinhuanet.batch.po.Order;
import com.xinhuanet.batch.po.ReturnOrder;
import com.xinhuanet.batch.service.AllinpayService;

/**
 * 通联批量查询
 * 
 * @author bahaidong
 *
 */
@Component
public class AllinpayServiceImpl implements AllinpayService{
	private static final Logger logger = Logger.getLogger(AllinpayServiceImpl.class);
	@Autowired
	private Http4Client http4Cilent;
	
	private @Autowired PropertiesConfiguration props;
	
	private List<ReturnOrder> returnOrders = new ArrayList<ReturnOrder>();
	private List<HeadInfo> headInfos = new ArrayList<HeadInfo>();

	@Override
	public List<ReturnOrder> getReturnOrders() {
		return returnOrders;
	}

	@Override
	public List<HeadInfo> getHeadInfos() {
		return headInfos;
	}

	@Override
	public String batchQuery(String beginDateTime, String endDateTime, String pageNo) {
		String merchantId = props.getString("Allinpay.MerId");// 商户号
		// beginDateTime = beginDateTime;
		// endDateTime = endDateTime;
		// pageNo = pageNo;
		String signType = "1";// 签名类型，固定值
		String version = "v1.6";// 版本号，固定值
		String key = props.getString("Allinpay.key");// 秘钥

		String signSrc = "version=" + version + "&merchantId=" + merchantId
				+ "&beginDateTime=" + beginDateTime + "&endDateTime="
				+ endDateTime + "&pageNo=" + pageNo + "&signType=" + signType
				+ "&key=" + key;
		String signMsg = SecurityUtil.MD5Encode(signSrc);

		// 提交查询请求
		//String postUrl = "http://ceshi.allinpay.com/mchtoq/index.do";// 测试环境
		// String postUrl =
		// "https://service.allinpay.com/mchtoq/index.do";//生产环境
		String resultMsg = "";
		String viewMsg = "";
		try {

			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			qparams.add(new BasicNameValuePair("merchantId", merchantId));
			qparams.add(new BasicNameValuePair("version", version));
			qparams.add(new BasicNameValuePair("signType", signType));
			qparams.add(new BasicNameValuePair("pageNo", pageNo));
			qparams.add(new BasicNameValuePair("beginDateTime", beginDateTime));
			qparams.add(new BasicNameValuePair("endDateTime", endDateTime));
			qparams.add(new BasicNameValuePair("signMsg", signMsg));

			resultMsg = http4Cilent.getResponeResult(props.getString("Allinpay.Batch.Utl"), qparams);

			logger.info("批量查询响应的原始报文：" + resultMsg);
			String fileAsString = ""; // 签名信息前的对账文件内容
			String fileSignMsg = ""; // 文件签名信息
			boolean isVerified = false; // 验证签名结果
			String ENCODING = "UTF-8";
			byte[] data = Base64.decode(resultMsg);
			String tempStr = new String(data, ENCODING);
			logger.info(tempStr);
			if (tempStr.indexOf("ERRORCODE=") < 0) {
				BufferedReader fileReader = new BufferedReader(new StringReader(tempStr));
				//BufferedReader fileReader = new BufferedReader(new FileReader(""));
				// 读取交易结果
				String lines;
				StringBuffer fileBuf = new StringBuffer(); // 签名信息前的字符串 String
				// lines;
				int i = 1;
				while ((lines = fileReader.readLine()) != null) {
					if (lines.length() > 0) {
						// 按行读，每行都有换行符
						fileBuf.append(lines + "\r\n");
						if(i == 1){
							convertHeadInfo(lines);
						}else {
							convertReturnOrder(lines);
						}
						i++;
					} else {
						// 文件中读到空行，则读取下一行为签名信息
						fileSignMsg = fileReader.readLine();
					}
				}
				fileReader.close();
				fileAsString = fileBuf.toString();
				logger.info("File: \n" + fileAsString);
				logger.info("Sign: \n" + fileSignMsg);
				// 验证签名：先对文件内容计算MD5摘要，再将MD5摘要作为明文进行验证签名
				String fileMd5 = SecurityUtil.MD5Encode(fileAsString);
				String certPath = "";
				URL keyFile = this.getClass().getResource(props.getString("Allinpay.keyFile"));
				if (null != keyFile)
					certPath = keyFile.getPath();
				isVerified = SecurityUtil.verifyByRSA(certPath,
						fileMd5.getBytes(), Base64.decode(fileSignMsg));
				if (isVerified) {
					// 验证签名通过，解析交易明细，开始对账
					logger.info("验证签名通过");
					viewMsg = fileAsString + fileSignMsg;
				} else {
					// 验证签名不通过，丢弃对账文件
					logger.info("验证签名不通过");
				}
			} else {
				viewMsg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewMsg;
	}
	
	private void convertReturnOrder(String lines) {
		String[] strs = lines.split("\\|");//转义字符
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		ReturnOrder returnOrder = new ReturnOrder();
		returnOrder.setMerchantId(strs[0]);
		returnOrder.setAllinpayId(strs[1]);
		returnOrder.setOrderId(strs[2]);
		Date orderComTime = null;
		Date orderSucTime = null;
		try {
			orderComTime = format.parse(strs[3]);
			orderSucTime = format.parse(strs[5]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		returnOrder.setOrderComTime(orderComTime);
		returnOrder.setOrderMoney(Double.parseDouble(strs[4])/100);
		returnOrder.setOrderSucTime(orderSucTime);
		returnOrder.setPayMoney(Double.parseDouble(strs[6])/100);
		returnOrder.setStr1(strs[7]);
		returnOrder.setStr2(strs[8]);
		returnOrder.setFlag(strs[9]);
		returnOrders.add(returnOrder);
	}

	private void convertHeadInfo(String lines) {
		String[] strs = lines.split("\\|");//转义字符
		HeadInfo headInfo = new HeadInfo();
		headInfo.setMerchantId(strs[0]);
		headInfo.setCount(Integer.parseInt(strs[1]));
		headInfo.setPageNo(Integer.parseInt(strs[2]));
		headInfo.setFlag(strs[3].equals("Y")?true: false);
		headInfos.add(headInfo);
	}

	@Override
	public List<ReturnOrder> getResult(String beginDateTime, String endDateTime,
			String pageNo) {
		//String str = "";
		String tmp = batchQuery(beginDateTime, endDateTime, pageNo);
		//str += tmp;
		if (null == tmp || tmp.equals(""))
			return pageNo.equals("1") ? null: returnOrders;
		//根据返回报文的头部信息判断是否有下一页，是否需要继续查询
		if (headInfos.get(Integer.parseInt(pageNo) - 1).getCount() < 500 && !headInfos.get(Integer.parseInt(pageNo) - 1).isFlag())
			return returnOrders;
		else if (headInfos.get(Integer.parseInt(pageNo) - 1).getCount() == 500 && headInfos.get(Integer.parseInt(pageNo) - 1).isFlag()) {
			getResult(beginDateTime, endDateTime, (Integer.parseInt(pageNo) + 1) + "");
		}
		return returnOrders;
	}
	
	@Override
	public String findDiffWriteFile(List<ReturnOrder> returnOrders, List<Order> orders) {
		StringBuffer str = new StringBuffer();
		BufferedWriter bw = null;
		List<ReturnOrder> existsReturnOrder = new ArrayList<ReturnOrder>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String tmp = "";
		//String headInfo = "商户号|通联订单号|商户订单号|商户订单提交时间|商户订单金额|支付完成时间|订单实际支付金额|处理结果$用户id|用户名|订单id|订单金额|订单提交时间|订单状态";
		Date date = new Date();
		try {
			bw = new BufferedWriter(new FileWriter("d:/t/allipany_" + dateFormat.format(date) +".txt", true));
			if(null == returnOrders) return null;
			//判断两个订单信息是否一致，并将第三方中存在订单记录到existsReturnOrder中
			//bw.write("字段格式含义:" + headInfo + "\r\n");;
			for(int i=0; i<orders.size(); i++){
				for(int j=0; j<returnOrders.size(); j++){
					if(orders.get(i).getId().equals(returnOrders.get(j).getOrderId())){//判断是否同一笔订单
						if((orders.get(i).getPayStatus() + "").equals(returnOrders.get(j).getFlag())){//订单状态是否一致
							if(orders.get(i).getMoney() == returnOrders.get(j).getPayMoney()
									&& orders.get(i).getMoney() == returnOrders.get(j).getOrderMoney()){
								logger.info("订单信息一致:" + returnOrders.get(j).toString());
							}else {
								//通联订单信息
								String returnOrder = returnOrderStr(returnOrders.get(j));
								//新华订单信息
								String order = orderStr(orders.get(i));
								tmp = returnOrder + "$" + order + "\r\n";
								str.append("订单金额不一致:" + tmp);
								bw.write("订单金额不一致:" + tmp);
								logger.info("订单信息不一致（订单金额不一致）:" + tmp);
							}
						}else{
							//通联订单信息
							String returnOrder = returnOrderStr(returnOrders.get(j));
							//新华订单信息
							String order = orderStr(orders.get(i));
							tmp = returnOrder + "$" + order + "\r\n";
							str.append("订单状态不一致:" + tmp);
							bw.write("订单状态不一致:" + tmp);
							logger.info("订单信息不一致（订单状态不一致）:" + tmp);
						}
						existsReturnOrder.add(returnOrders.get(j));
					}
				}
			}
			
			//existsReturnOrder.size()=0说明所有的returnOrders都不在pay_orders表中
			if(0 == existsReturnOrder.size()){
				//输出在第三方存在的订单，并且不再pay_orders表中的订单
				for(int i=0; i<returnOrders.size(); i++){
					//通联订单信息
					String returnOrder = returnOrderStr(returnOrders.get(i));
					tmp = returnOrder + "$" + "\r\n";
					str.append("pay_orders中不存在的记录:" + tmp);
					bw.write("pay_orders中不存在的记录:" + tmp);
					logger.info("订单信息不一致（pay_orders中不存在的记录）:" + tmp);
				}
			}else{
				boolean tmpFlag = returnOrders.removeAll(existsReturnOrder);
				if(tmpFlag && 0 <= returnOrders.size()){
					//输出在第三方存在的订单，并且不再pay_orders表中的订单
					for(int i=0; i<returnOrders.size(); i++){
						//通联订单信息
						String returnOrder = returnOrderStr(returnOrders.get(i));
						tmp = returnOrder + "$" + "\r\n";
						str.append("pay_orders中不存在的记录:" + tmp);
						bw.write("pay_orders中不存在的记录:" + tmp);
						logger.info("订单信息不一致（pay_orders中不存在的记录）:" + tmp);
					}
				}
			}		
			
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		return str.toString();
	}
	
	/**
	 * 通联订单输出文件信息
	 * @param returnOrder
	 * @return
	 */
	private String returnOrderStr(ReturnOrder returnOrder){
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		sb.append(returnOrder.getMerchantId() + "|");
		sb.append(returnOrder.getAllinpayId() + "|");
		sb.append(returnOrder.getOrderId() + "|");		
		sb.append(format.format(returnOrder.getOrderComTime()) + "|");
		sb.append(returnOrder.getOrderMoney() + "|");
		sb.append(format.format(returnOrder.getOrderSucTime()) + "|");
		sb.append(returnOrder.getPayMoney() + "|");
		sb.append(returnOrder.getFlag());
		
		return sb.toString();
	}
	
	/**
	 * pay_order输出文件信息
	 * @param order
	 * @return
	 */
	private String orderStr(Order order){
		StringBuffer sb = new StringBuffer();		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		sb.append(order.getUid() + "|");
		sb.append(order.getLoginName() + "|");	
		sb.append(order.getId() + "|");
		sb.append(order.getMoney() + "|");
		sb.append(format.format(order.getPayTime()) + "|");
		sb.append(order.getPayStatus());
		
		return sb.toString();
	}

}
