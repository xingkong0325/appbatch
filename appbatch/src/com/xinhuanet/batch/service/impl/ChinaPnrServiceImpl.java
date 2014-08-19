package com.xinhuanet.batch.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chinapnr.SecureLink;

import com.xinhuanet.batch.common.ChinaPnr;
import com.xinhuanet.batch.common.Function;
import com.xinhuanet.batch.common.Http4Client;
import com.xinhuanet.batch.po.ChinapnrProperties;
import com.xinhuanet.batch.po.Order;
import com.xinhuanet.batch.service.ChinaPnrService;
import com.xinhuanet.batch.service.PayOrderService;

@Component
public class ChinaPnrServiceImpl implements ChinaPnrService {
	 private static final Logger logger = LoggerFactory.getLogger(ChinaPnrServiceImpl.class);
	 
	 @Autowired
	 private PayOrderService payOrderService;
	 
	 /**
	  * http4Client服务
	  */
	 @Autowired
	 private Http4Client http4Client;
	 
	 /**
	  * 读取配置文件服务
	  */
	 @Autowired
	 private PropertiesConfiguration props;

	@Override
	public ChinapnrProperties queryOrder(String ordId) {
		// 设置汇付天下查询明细所需属性
		ChinapnrProperties chinapnr = new ChinapnrProperties();
		chinapnr.setVersion(props.getString("ChinaPnr.QueryOrder.version"));// 版本
		chinapnr.setCmdId(props.getString("ChinaPnr.QueryOrder.CmdId")); // 消息类型
		chinapnr.setMerId(props.getString("ChinaPnr.MerId"));// 商户号
		chinapnr.setOrdId(ordId);// 订单号
		try {
			// 签名
			String MerKeyFile  = this.getClass().getResource(props.getString("ChinaPnr.MerPrK")).getPath();//商户秘钥文件路径
			String MerData = chinapnr.getVersion() + chinapnr.getCmdId() + chinapnr.getMerId() + chinapnr.getOrdId() + "";
			
			SecureLink sl = new SecureLink();
			int ret = sl.SignMsg(chinapnr.getMerId(), MerKeyFile, MerData);
			if(0 != ret){//签名失败后返回
				logger.info("查询订单状态：签名错误 ret=" + ret + " MerData:["+ MerData +"]");
				chinapnr = new ChinapnrProperties();
				chinapnr.setSignRet(ret);
				chinapnr.setErrMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
				return chinapnr;
			}
			String chkValue = sl.getChkValue();
			chinapnr.setChkValue(chkValue);// 数字签名
			
			List<NameValuePair> qparams  = new ArrayList<NameValuePair>();
			qparams.add(new BasicNameValuePair("Version", chinapnr.getVersion()));
			qparams.add(new BasicNameValuePair("CmdId", chinapnr.getCmdId()));
			qparams.add(new BasicNameValuePair("MerId", chinapnr.getMerId()));
			qparams.add(new BasicNameValuePair("OrdId", chinapnr.getOrdId()));
			qparams.add(new BasicNameValuePair("UsrId", ""));
			qparams.add(new BasicNameValuePair("ChkValue", chinapnr.getChkValue()));
			
			String returnValue = http4Client.getResponeResult(props.getString("ChinaPnr.uri") + "/" + props.getString("ChinaPnr.path"), qparams);
			
			if(!StringUtils.isBlank(returnValue)){// 解析返回结果
				logger.info("返回订单明细字符串:\r\n" + returnValue);
				Map<String, String> map = ChinaPnr.wrapperMap(returnValue);
				chinapnr = (ChinapnrProperties)Function.RegisterBean(ChinapnrProperties.class, map);
			}else {//请求异常后返回
				logger.info("系统错误，未返回数据!");
				chinapnr = new ChinapnrProperties();
				chinapnr.setProcStat(ChinaPnr.PROCSTAT_ERROR);
				return chinapnr;
			}
		} catch (Exception e) {
			logger.error("查询订单状态：签名验证异常",e);
			chinapnr = new ChinapnrProperties();
			chinapnr.setSignRet(-101);//私钥文件异常
			chinapnr.setSignMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
			return chinapnr;
		}
		
		try {
			// 验签
			String MerKeyFile = getClass().getResource(props.getString("ChinaPnr.PgPubk")).getPath();
			
			String MerData = chinapnr.getCmdId() + chinapnr.getRespCode()
			+ StringUtils.stripToEmpty(chinapnr.getTrxId()) + StringUtils.stripToEmpty(chinapnr.getOrdAmt())
			+ StringUtils.stripToEmpty(chinapnr.getCurCode()) + StringUtils.stripToEmpty(chinapnr.getPid())
			+ StringUtils.stripToEmpty(chinapnr.getOrdId()) + StringUtils.stripToEmpty(chinapnr.getDivDetails())
			+ StringUtils.stripToEmpty(chinapnr.getMerPriv()) + StringUtils.stripToEmpty(chinapnr.getRefCnt())
			+ StringUtils.stripToEmpty(chinapnr.getRefAmt()) + StringUtils.stripToEmpty(chinapnr.getErrMsg()); // 参数顺序不能错

			SecureLink sl = new SecureLink();
			int ret = sl.VeriSignMsg(MerKeyFile, MerData, chinapnr.getChkValue());

			if (0 != ret) {
				logger.info("查询订单明细：签名验证错误 ret=" + ret + " MerData[" + MerData + "]");
				chinapnr = new ChinapnrProperties();
				chinapnr.setSignRet(ret);
				chinapnr.setSignMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
				return chinapnr;
			} else {
				if (chinapnr.getRespCode().equals(ChinaPnr.RESPCODE_SUCCEE)) {
					logger.info("查询订单" + ordId + "明细：" + chinapnr.getErrMsg() + "，交易成功");
				} else {
					logger.info("查询订单" + ordId + "明细：交易失败");
				}
			}
		} catch (Exception e) {
			logger.error("查询订单明细：验证签名验证异常",e);
			chinapnr = new ChinapnrProperties();
			chinapnr.setSignRet(-112);//公钥文件异常
			chinapnr.setSignMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
			return chinapnr;
		}
		return chinapnr;
	}
	
	@Override
	public ChinapnrProperties queryStatus(String ordId) {
		// 设置汇付天下查询订单明细所需属性
		ChinapnrProperties chinapnr = new ChinapnrProperties();
		chinapnr.setVersion(props.getString("ChinaPnr.QueryStatus.version"));// 版本
		chinapnr.setCmdId(props.getString("ChinaPnr.QueryStatus.CmdId"));// 消息类型
		chinapnr.setMerId(props.getString("ChinaPnr.MerId"));// 商户号
		chinapnr.setOrdId(ordId);// 订单号
		try {
			// 签名
			String MerKeyFile = getClass().getResource(
					props.getString("ChinaPnr.MerPrK")).getPath(); // 商户私钥文件路径
																	// 请将MerPrK510010.key改为你的私钥文件名称
			String MerData = chinapnr.getVersion() + chinapnr.getCmdId()
					+ chinapnr.getMerId() + chinapnr.getOrdId();

			SecureLink sl = new SecureLink();
			int ret = sl.SignMsg(chinapnr.getMerId(), MerKeyFile, MerData);

			if (ret != 0) {//签名失败后返回
				logger.info("查询订单状态：签名错误 ret=" + ret + " MerData:["+ MerData +"]");
				chinapnr = new ChinapnrProperties();
				chinapnr.setSignRet(ret);
				chinapnr.setErrMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
				return new ChinapnrProperties();
			}
			String chkValue = sl.getChkValue();
			chinapnr.setChkValue(chkValue);// 数字签名

			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			qparams.add(new BasicNameValuePair("Version", chinapnr.getVersion()));
			qparams.add(new BasicNameValuePair("CmdId", chinapnr.getCmdId()));
			qparams.add(new BasicNameValuePair("MerId", chinapnr.getMerId()));
			qparams.add(new BasicNameValuePair("OrdId", chinapnr.getOrdId()));
			qparams.add(new BasicNameValuePair("ChkValue", chinapnr.getChkValue()));
			
			String returnValue = http4Client.getResponeResult(props.getString("ChinaPnr.uri")+ "/" +props.getString("ChinaPnr.path"),qparams);
			
			if(!StringUtils.isBlank(returnValue)){//解析返回结果
				logger.info("返回订单状态字符串:\r\n" + returnValue);
				Map<String, String> map = ChinaPnr.wrapperMap(returnValue);
				chinapnr = (ChinapnrProperties) Function.RegisterBean(
						ChinapnrProperties.class, map);
			} else{//请求异常后返回
				chinapnr = new ChinapnrProperties();
				chinapnr.setProcStat(ChinaPnr.PROCSTAT_ERROR);
				chinapnr.setErrMsg(ChinaPnr.getProcStatName(chinapnr.getProcStat()));
				return chinapnr;
			}
		} catch (Exception e) {
			logger.error("查询订单状态：签名验证异常",e);
			chinapnr = new ChinapnrProperties();
			chinapnr.setSignRet(-101);//私钥文件异常
			chinapnr.setSignMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
			return chinapnr;
		}
				
		// 验签
		try {
			String MerKeyFile = getClass().getResource(props.getString("ChinaPnr.PgPubk")).getPath();
			String MerData = chinapnr.getCmdId() + chinapnr.getRespCode()+ chinapnr.getProcStat() + chinapnr.getErrMsg().trim(); // 参数顺序不能错

			SecureLink sl = new SecureLink();
			int ret = sl.VeriSignMsg(MerKeyFile, MerData, chinapnr.getChkValue());

			if (ret != 0) { //验证签名失败后返回
				logger.info("签名验证失败[" + MerData + "]");
				chinapnr = new ChinapnrProperties();
				chinapnr.setSignRet(ret);
				chinapnr.setSignMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
				return chinapnr;
			} else {
				if (chinapnr.getRespCode().equals("000000")) {
					logger.info("查询订单"+ordId+"状态："+chinapnr.getErrMsg()+"，交易成功");
				} else {
					logger.info("查询订单"+ordId+"状态：交易失败");
				}
			}
		} catch (Exception e) {
			logger.error("查询订单状态：验证签名验证异常",e);
			chinapnr = new ChinapnrProperties();
			chinapnr.setSignRet(-112);//公钥文件异常
			chinapnr.setSignMsg(ChinaPnr.getRetName(chinapnr.getSignRet()));
			return chinapnr;
		}
		return chinapnr;
	}

	@Override
	public String findDiffWriteFile(List<Order> orders) {
		if(0 == orders.size()) return null;
		String ordId = "";
		BufferedWriter bw = null;
		String tmp = "";
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		try {
			bw = new BufferedWriter(new FileWriter("d:/t/chinapnr_" + dateFormat.format(date) +".txt", true));
			for(int i=0; i<orders.size(); i++){
				Order order = orders.get(i);
				ordId = order.getId();
				ChinapnrProperties chinapnr = new ChinapnrProperties();
				chinapnr = this.queryOrder(ordId);
//				ChinapnrProperties chinapnrStatus = new ChinapnrProperties();
//				chinapnrStatus = this.queryStatus(ordId);
				if(null == chinapnr.getOrdId()){
					continue;
				}
				if(order.getMoney() == Double.parseDouble(chinapnr.getOrdAmt())){
					logger.info("订单号" + ordId + "金额一致");
				}else{
					String chinapnrStr = chinapnrStr(chinapnr);
					String orderStr = payOrderService.orderStr(order);
					tmp = chinapnrStr + "$" + orderStr + "\r\n";
					logger.info("订单信息不一致（订单金额不一致）:" + tmp);
					sb.append("订单金额不一致:" + tmp);
					bw.write("订单金额不一致:" + tmp);
				}
				
				
			}
			bw.flush();
		} catch (NumberFormatException e) {
			logger.info("金额转换失败!");
		} catch (IOException e) {
			logger.info("创建文件或写入文件失败!");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				logger.info("关闭文件输入流失败!");
			}
		}
		return sb.toString();
	}

	/**
	 * 钱管家交易唯一标识|订单号|订单金额|应答返回码|币种|退款次数|退款总金额|应答返回码
	 * @param chinapnr
	 * @return
	 */
	private String chinapnrStr(ChinapnrProperties chinapnr) {
		StringBuffer sb = new StringBuffer();
		sb.append(chinapnr.getTrxId() + "|");
		sb.append(chinapnr.getOrdId() + "|");
		sb.append(chinapnr.getOrdAmt() + "|");
		sb.append(chinapnr.getRespCode() + "|");
		sb.append(chinapnr.getCurCode() + "|");
		sb.append(chinapnr.getRefCnt() + "|");
		sb.append(chinapnr.getRefAmt() + "|");
		sb.append(chinapnr.getErrMsg());
		
		return sb.toString();
	}

}
