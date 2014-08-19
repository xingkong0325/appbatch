package com.xinhuanet.batch.common;

import java.util.HashMap;
import java.util.Map;

public class ChinaPnr {
	/**
	 * 汇付RespCode返回的成功值
	 */
	public static final String RESPCODE_SUCCEE = "000000";
	/**
	 * 第三方平台未返回数据
	 */
	public static final String PROCSTAT_ERROR = "-1";
	
	public static String getProcStatName(String procStat) {
		if(PROCSTAT_ERROR.equals(procStat)){
			return "系统错误，未返回数据";
		} else if("0".equals(procStat)){
			return "支付交易不存在";
		} else if("1".equals(procStat)){
			return "交易已成功支付，并且已经结算";
		} else if("2".equals(procStat)){
			return "交易未支付或支付失败";
		} else if("3".equals(procStat)){
			return "交易已成功支付，但未结算";
		} else if("5".equals(procStat)){
			return "退款交易，已扣款，系统处理中";
		} else if("6".equals(procStat)){
			return "退款交易，处理失败";
		} else if("7".equals(procStat)){
			return "退款交易，未处理";
		} else if("8".equals(procStat)){
			return "退款交易已成功";
		}
		return null;
	}
	
	/**
	 * 将字符串格式化，封装成Map对象K,V对
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @return 返回封装完成的字符串
	 */
	public static Map<String, String> wrapperMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		String strs[] = str.trim().split("\r\n");
		for (String s : strs) {
			if (!"".equals(s) && s != null) {
				String temps[] = s.split("=");
				if (temps.length < 2) {
					map.put(temps[0], "");
				} else {
					map.put(temps[0], temps[1].replaceAll("\r\n", ""));
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * @param ret 签名返回码
	 * @return 签名返回码所代表的含义
	 */
	public static String getRetName(int ret) {
		if(ret == 0){
			return "本次调用成功";
		} else if(ret == -100){
			return "环境变量\"NPCDIR\"未设置。参考：需要在在Linux或Unix上设置环境变量NPCDIR";
		} else if(ret == -101){
			return "商户密钥文件不存在或无法打开。参考：设置密钥文件为可写方式";
		} else if(ret == -102){
			return "密钥文件格式错误。参考：可能商户的密钥文件在传输途中有误";
		} else if(ret == -103){
			return "参数错误。参考：请检查参与签名的参数是否正确";
		} else if(ret == -112){
			return "公钥文件不存在或无法打开。参考：设置密钥文件为可写方式";
		} else if(ret == -113){
			return "公钥文件格式错误。参考：可能公钥文件在传输途中有误";
		} else if(ret == -301){
			return "验证签名出错。参考：请检查参与签名的参数是否正确";
		} else if(ret == -401){
			return "参与签名的数据体超过规定长度";
		}
		return null;
	}
}
