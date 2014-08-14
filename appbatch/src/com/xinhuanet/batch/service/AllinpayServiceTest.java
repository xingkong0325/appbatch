package com.xinhuanet.batch.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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

/**
 * 通联批量查询
 * 
 * @author bahaidong
 *
 */
@Component
public class AllinpayServiceTest {
	private static final Logger logger = Logger
			.getLogger(AllinpayServiceTest.class);
	@Autowired
	private Http4Client http4Cilent;

	private @Autowired PropertiesConfiguration props;

	private List<ReturnOrder> returnOrders = new ArrayList<ReturnOrder>();
	private List<HeadInfo> headInfos = new ArrayList<HeadInfo>();

	public List<ReturnOrder> getReturnOrders() {
		return returnOrders;
	}

	public List<HeadInfo> getHeadInfos() {
		return headInfos;
	}

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
	public String batchQuery(String beginDateTime, String endDateTime,
			String pageNo) {
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
		// String postUrl = "http://ceshi.allinpay.com/mchtoq/index.do";// 测试环境
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

			logger.info("批量查询响应的原始报文：" + resultMsg);
			String fileAsString = ""; // 签名信息前的对账文件内容
			String fileSignMsg = ""; // 文件签名信息
			// BufferedReader fileReader = new BufferedReader(new
			// StringReader(tempStr));
			BufferedReader fileReader = new BufferedReader(new FileReader("d:/t/re.txt"));
			// 读取交易结果
			String lines;
			StringBuffer fileBuf = new StringBuffer(); // 签名信息前的字符串 String
			// lines;
			int i = 1;
			while ((lines = fileReader.readLine()) != null) {
				if (lines.length() > 0) {
					// 按行读，每行都有换行符
					fileBuf.append(lines + "\r\n");
					if (i == 1) {
						convertHeadInfo(lines);
					} else {
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
			// 验证签名通过，解析交易明细，开始对账
			logger.info("验证签名通过");
			viewMsg = fileAsString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewMsg;
	}

	private void convertReturnOrder(String lines) {
		String[] strs = lines.split("\\|");
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
		returnOrder.setOrderMoney(Double.parseDouble(strs[4]) / 100);
		returnOrder.setOrderSucTime(orderSucTime);
		returnOrder.setPayMoney(Double.parseDouble(strs[6]) / 100);
		returnOrder.setStr1(strs[7]);
		returnOrder.setStr2(strs[8]);
		returnOrder.setFlag(strs[9]);
		returnOrders.add(returnOrder);
	}

	private void convertHeadInfo(String lines) {
		String[] strs = lines.split("\\|");
		HeadInfo headInfo = new HeadInfo();
		headInfo.setMerchantId(strs[0]);
		headInfo.setCount(Integer.parseInt(strs[1]));
		headInfo.setPageNo(Integer.parseInt(strs[2]));
		headInfo.setFlag(strs[3].equals("Y")?true: false);
		headInfos.add(headInfo);
	}

	/**
	 * 查询全部结果集
	 * 
	 * @param beginDateTime
	 * @param endDateTime
	 * @param pageNo
	 * @return
	 */
	public List<ReturnOrder> getResult(String beginDateTime, String endDateTime,
			String pageNo) {
		//String str = "";
		String tmp = batchQuery(beginDateTime, endDateTime, pageNo);
		//str += tmp;
		if (null == tmp || tmp.equals(""))
			return null;
		if (headInfos.get(Integer.parseInt(pageNo) - 1).getCount() < 500
				&& !headInfos.get(Integer.parseInt(pageNo) - 1).isFlag())
			return returnOrders;
		else if (headInfos.get(Integer.parseInt(pageNo) - 1).getCount() == 500
				&& headInfos.get(Integer.parseInt(pageNo) - 1).isFlag()) {
			getResult(beginDateTime, endDateTime,
					(Integer.parseInt(pageNo) + 1) + "");
		}
		return returnOrders;
	}

	public String findDiffWriteFile(List<ReturnOrder> returnOrders2, List<Order> orders) {
		StringBuffer str = new StringBuffer();
		BufferedWriter bw = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			bw = new BufferedWriter(new FileWriter("d:/t/result.txt", true));
			if(null == returnOrders2 || null == orders) return null;
			for(int i=0; i<returnOrders2.size(); i++){
				for(int j=0; j<orders.size(); j++){
					if(returnOrders2.get(i).getOrderId().equals(orders.get(j).getId())){
						if(returnOrders2.get(i).getPayMoney() == orders.get(j).getMoney() && returnOrders2.get(i).getOrderMoney() == orders.get(j).getMoney()){
							logger.info("订单信息一致！");
						}else{
							String returnOrder = returnOrders2.get(i).getMerchantId() + "|" 
												+ returnOrders2.get(i).getAllinpayId() + "|"
												+ returnOrders2.get(i).getOrderId() + "|"
												+ returnOrders2.get(i).getOrderMoney() + "|"
												+ format.format(returnOrders2.get(i).getOrderComTime()) + "|"
												+ returnOrders2.get(i).getOrderMoney() + "|"
												+ format.format(returnOrders2.get(i).getOrderSucTime()) + "|"
												+ returnOrders2.get(i).getPayMoney() + "|"
												+ returnOrders2.get(i).getFlag();
							String order = orders.get(j).getUid() + "|"
										+ orders.get(j).getLoginName() + "|"
										+ orders.get(j).getId() + "|"
										+ orders.get(j).getMoney() + "|"
										+ format.format(orders.get(j).getPayTime()) + "|"									
										+ orders.get(j).getPayStatus();
							str.append(returnOrder + "$" + order + "\r\n");
							bw.write(returnOrder + "$" + order + "\r\n");
						}
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

}
