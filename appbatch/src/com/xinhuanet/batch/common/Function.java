package com.xinhuanet.batch.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;


public abstract class Function {
	protected static final Logger logger = Logger.getLogger(Function.class);
	
	
	/**
	 * 将字符串数据转成整型数字，当为非数字字符串时，转成0
	 * @param str 指定字符串数字
	 * @return
	 */
	public static int stringToInt(String str){
		return isNumber(str)?Integer.parseInt(str):0;
	}
	
	/**
	 * 将字符串数据转成整型数字，当为非数字字符串时，转成0
	 * @param str 指定字符串数字
	 * @return
	 */
	public static long stringToLong(String str){
		return isNumber(str)?Long.parseLong(str):0l;
	}
	
	/**
	 * 将字符串数据转成小数，当为非数字字符串时，转成0
	 * @param str 指定字符串数字
	 * @return
	 */
	public static double stringToDouble(String str){
		return isNumber(str)?Double.parseDouble(str):0;
	}
	
    /**
     * 取得系统当前的时间，以Timestamp 表示
     *
     * @ return 返回Timestamp对象
     */
    public static Timestamp getDateTime() {
        java.util.Date date = new java.util.Date();
        return (new java.sql.Timestamp(date.getTime()));
    }

    /**
     * 取得当前系统时间的字符串表示
     *
     * @return String 格式 如2010-09-08 19:01:00
     */
    public static String getDateTimeString() {
        java.sql.Timestamp ts = getDateTime();
        return getDateTimeString(ts);
    }

    /**
     * construct a Date through a string like "yyyy-mm-dd"
     *
     * @param con
     * @return
     * @throws java.sql.SQLException
     */
    public static java.sql.Date getDate(String time) throws SQLException {
//    Timestamp ts = getDateTime(time);
//    return new java.sql.Date(ts.getTime());
      return java.sql.Date.valueOf(time);
    }

    /**
     * 返回一个当前日期的字符串 格式为“YYYY-MM-DD”
     *
     * @return
     */
    public static String getDateString() {
        java.sql.Timestamp ts = getDateTime();
        String str = getDateTimeString(ts);
        if (str.length() >= 10) {
            str = str.substring(0, 10);
        }
        return str;
    }


    /**
     * construct a timestamp through a string like "yyyy-mm-dd" or "yyyy-mm-dd hh:mm:ss"
     * @param sDt
     * @return
     */
    static public java.sql.Timestamp getDateTime(String sDt) {
        if (sDt == null || "".equals(sDt.trim()))
            return null;
        try {
            //sDt format:yyyy-mm-dd hh:mm:ss.fffffffff
            return java.sql.Timestamp.valueOf(sDt);
        }
        catch (IllegalArgumentException iae) {
            sDt = sDt + " 00:00:00";
            return java.sql.Timestamp.valueOf(sDt);
        }
    }
    /**
     * 验证时间戳格式是否正确，默认时间格式为yyyy-mm-dd hh:mm:ss
     * @param s 字符串时间戳
     * @return 如果正确返回true，否则返回false
     */
	public static boolean isValidDate(String s) {
		String pattern = "yyyy-mm-dd hh:mm:ss";
		return isValidDate(s,pattern);
	}
    /**
     * 验证时间戳格式是否正确，指定时间格式
     * @param s 字符串时间戳
     * @return 如果正确返回true，否则返回false
     */
	public static boolean isValidDate(String s, String pattern) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			dateFormat.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
    
    /**
     * 取得给定时间的字符串表示
     *
     * @param ts java.sql.Timestamp
     * @return String 格式 如2003-03-03 19:01:00
     */
    public static String getDateTimeString(java.sql.Timestamp ts) {
        if (ts == null) return "";
        String str = ts.toString();
        if (str.length() >= 19) {
            str = str.substring(0, 19);
        }
        return str;
    }
    /**
     * 获取指定天数以前的此时此刻时间
     * @param date 指定的时间
     * @param day 指定的天数
     * @return 指定天数以前的此时此刻时间
     */
    public static Timestamp getPreviousDay(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		date = calendar.getTime();
        return (new java.sql.Timestamp(date.getTime()));
	}
    /**
     * 获取1天以前的此时此刻时间
     * @param date 指定的时间
     * @return 1天以前的此时此刻时间
     */
    public static Timestamp getPreviousDay(Date date) {
		return getPreviousDay(date,1);
	}
    
    /**
     * 获取指定小时以前的此时此刻时间
     * @param date 指定的时间
     * @param hour 指定的天数
     * @return 指定小时以前的此时此刻时间
     */
    public static Timestamp getPreviousHour(Date date,int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -hour);
		date = calendar.getTime();
        return (new java.sql.Timestamp(date.getTime()));
	}
    /**
     * 获取1小时以前的此时此刻时间
     * @param date 指定的时间
     * @return 1小时以前的此时此刻时间
     */
    public static Timestamp getPreviousHour(Date date) {
		return getPreviousDay(date,1);
	}
	
	/**
	 * 测试此日期是否在指定日期之前。
	 * @param when 日期
	 * @return 当且仅当此 Date 对象表示的瞬间比 when 表示的瞬间早，才返回 true；否则返回 false。
	 */
	public static boolean beforeDate(Date when) {
		Date now = new Date();
		return now.before(when);
	}
	/**
	 * 格式化当前日期
	 * @param pattern 指定的日期格式
	 * @return 返回当前日期指定的字符串格式
	 */
	public static String getDateString(String pattern) {
		SimpleDateFormat sformat = new SimpleDateFormat(pattern);
		Date date = new Date();
		return sformat.format(date);
	}
	/**
	 * 格式化指定日期
	 * @param date 指定的日期
	 * @param pattern 指定的日期格式
	 * @return 返回指定日期的指定字符串格式
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sformat = new SimpleDateFormat(pattern);
		return sformat.format(date);
	}
	
	/**
	 * 在指定日期减days天
	 * 
	 * @param date
	 *            指定的日期
	 * @param days
	 *            天数
	 * @param pattern
	 *            指定的日期格式
	 * @return 返回指定日期的指定字符串格式
	 */
	public static String getBeforeDate(Date date, int days, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				- days);
		return df.format(calendar.getTime());
	}

	/**
	 * 在指定日期加days天
	 * 
	 * @param date
	 *            指定的日期
	 * @param days
	 *            天数
	 * @param pattern
	 *            指定的日期格式
	 * @return 返回指定日期的指定字符串格式
	 */
	public static String getAfterDate(Date date, int days, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				+ days);
		return df.format(calendar.getTime());
	}

	/**
	 * 格式化指定日期
	 * 
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的日期格式
	 * @return 将指定字符串格式的日期转换成Date类型返回
	 */
	public static Date getStringDate(String date, String pattern)
			throws Exception {
		SimpleDateFormat sformat = new SimpleDateFormat(pattern);
		if (date != null && date.trim().length() > 0)
			return sformat.parse(date);
		return null;
	}
	/**
	 * 获取指定日期与当前相差的天数时分
	 * @param endTime 截止时间
	 * @return 	如果大于1天，返回天和小时；<br>
	 * 			如果不足1天，返回小时;<br>
	 * 			如果不足1小时，返回分钟；<br>
	 * 			如果时间过期，则返回null；
	 */
	public static String getRemnantTime(Date endTime){
		long l1 = getDateTime().getTime();
		long l2 = endTime.getTime();
		long t = Math.abs((l2 - l1) / 1000);
//		long ho = 0;
		StringBuffer s = new StringBuffer();
		if(t >=0&&t <60){
			s.append("刚刚");
		}else if(t >=60&&t <3600){ 
			s.append(t/60).append("分钟前");
		} else if (t >= 3600 && t < (3600 * 24)) {
			s.append(t / 3600).append("小时前");
		} else if (t >= (3600 * 24)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			if(!sdf.format(endTime).equalsIgnoreCase(sdf.format(new Date()))){
				s.append(new SimpleDateFormat("yy-MM-dd HH:mm").format(endTime));
			}else{
				s.append(new SimpleDateFormat("MM-dd HH:mm").format(endTime));
			}
//			ho = t / (3600 * 24);
//			if(ho>=365){
//				s.append(ho/365).append("年");
//			}else{
//				s.append(ho).append("天");
//			}
		}
		return s.toString().equals("") ? null : s.toString();
	}
	
	/**
	 * 获取指定日期与当前相差的天数时分
	 * @param endTime 截止时间
	 * @return 	如果大于1天，返回天和小时；<br>
	 * 			如果不足1天，返回小时;<br>
	 * 			如果不足1小时，返回分钟；<br>
	 * 			如果时间过期，则返回null；
	 */
	public static String getRemnantDate(Date endTime){
		long l1 = getDateTime().getTime();
		long l2 = endTime.getTime();
		long t = Math.abs((l2 - l1) / 1000);
		long ho = 0;
		StringBuffer s = new StringBuffer();
		ho = t / (3600 * 24);
		if(ho>=3){
			s.append(new SimpleDateFormat("yyyyMMdd").format(endTime));
		}else{
			switch((int)ho){
			case 0:
				s.append("今天");
				break;
			case 1:
				s.append("昨天");
				break;
			case 2:
				s.append("前天");
				break;
			}
		}
		return s.toString().equals("") ? null : s.toString();
	}
	
	/**
	 * 获取指定日期与当前相差的天数时分
	 * @param endTime 截止时间
	 * @return 	如果大于1天，返回天和小时；<br>
	 * 			如果不足1天，返回小时;<br>
	 * 			如果不足1小时，返回分钟；<br>
	 * 			如果时间过期，则返回null；
	 */
	public static String getRemnantTime(String endTime){
		Date date = getDateTime(endTime);
		return getRemnantTime(date);
	}
	/**
	 * 获取两个日期相差天数
	 * @param fDate 指定开始日期
	 * @param oDate 指定结束日期
	 * @return 相差天数
	 */
	public static int getSubTwoDate(Date fDate, Date oDate) {
		// 首先定义一个calendar，必须使用getInstance()进行实例化
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		// 计算此日期是一年中的哪一天
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		// 求出两日期相隔天数
		int days = day2 - day1;
		return days;
	}
	
	/**
	 * 获取指定日期与当前日期相差天数
	 * @param oDate 指定结束日期
	 * @return 相差天数
	 */
	public static int getSubTwoDate(Date oDate) {
		Date fDate = getDateTime();
		return getSubTwoDate(fDate, oDate);
	}
	
	/**
	 * 验证字符串是否为空
	 * @param b_str
	 * @return 字符串为空或者为null返回true，否则返回false
	 */
    public static boolean checkStringEmpty(String b_str) {
        if (b_str == null || "".equals(b_str.trim())) {
            return true;
        }
        return false;
    }
    
    /**
     * 正则表达式数字验证
     * @param str
     * @return 是数字返回true，否则返回false
     */
    public static boolean isNumber(String str) {
        if(str == null || "".equals(str.trim())){
            return false;
        }
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
        java.util.regex.Matcher match = pattern.matcher(str);
        return match.matches();
    }
    
    /**
     * 正则表达式数字验证，保留且必须两位小数，无小数时用0补齐
     * @param str
     * @return 正确返回true，否则返回false
     */
    public static boolean isNumber2Decimal(String str) {
        if(str == null || "".equals(str.trim())){
            return false;
        }
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))\\.(\\d){2}?$");
        java.util.regex.Matcher match = pattern.matcher(str);
        return match.matches();
    }
    
    /**
     * 验证字符串的长度不超过指定数字
     * @param str 验证的字符串
     * @param length 给定的长度
     * @return 不大于指定长度返回true，否则返回false
     */
    public static boolean maxLength(String str,int length) {
        if(str == null || "".equals(str.trim())){
            return false;
        }
        if(str.length()> length){
        	return false;
        } else{
        	return true;
        }
    }
    
    /**
     * 格式化距离显示方式
     * @param d 距离，单位米
     * @return 小于1000米，返回m；大于1000米返回km
     */
    public static String getDistance(double d){
    	DecimalFormat df = new DecimalFormat("#.##");
    	if(d>=1000.0){
    		return df.format(d/1000.0)+"km";
    	}
    	return df.format(d)+"m";
    }
    
	
	/**
	 * 将Map转换为类对象，通过类反射机制进行注册
	 * @param clazz 运行时类
	 * @param map 已封装完成的K,V
	 * @return 注册完成的对象
	 */
	public static Object RegisterBean(final Class<? extends Object> clazz,Map<String,String> map){
		Object obj = null;
		try{
			obj = clazz.newInstance();
			Method methods[] = clazz.getMethods();
			for(Method m:methods){
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey().toString();
					key = key.substring(0,1).toUpperCase() + key.substring(1);
					String value = entry.getValue().toString();
					if (("set" + key).equals(m.getName())) {
						if ("java.lang.Integer".equals(m.getParameterTypes()[0].getName()) || "int".equals(m.getParameterTypes()[0].getName())) {
							Integer intValue = Integer.parseInt(value);
							m.invoke(obj, intValue);
						} else if ("java.lang.Long".equals(m.getParameterTypes()[0].getName()) || "long".equals(m.getParameterTypes()[0].getName())) {
							Long longValue = Long.parseLong(value);
							m.invoke(obj, longValue);
						} else {
							m.invoke(obj, value);
						}
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * 将double值格式化为保留两位小数点，并且以String形式返回
	 * @param d 需要格式化的值
	 * @return value 格式化完成String
	 */
	public static String formtDecimalPoint2(double d) {
		BigDecimal b = new BigDecimal(d);
		String value = b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		return value;
	}
	/**
	 * 将ISO-8859-1编码转换为UTF-8编码
	 * @param s 需转换的ISO-8859-1字符
	 * @return 成功则返回转换后的UTF-8字符，否则返回??
	 */
    public static String toISO8859_UTF8(String s) {
        String sRet = null;
        if (s == null) {
            sRet = "";
        } else {
            try {
                sRet = new String(s.getBytes("8859_1"), "UTF-8");
            } catch (Exception e) {
            	logger.error("解码出现异常："+e);
                sRet = "??";
            }
        }
        return sRet;
    }
    
    /**
     * 返回长度为strLength的随机数，在前面补0    
     * @param strLength
     * @return
     */
	public static String getFixLenthString(int strLength) {   
	       
	    Random rm = new Random();   
	       
	    // 获得随机数   
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);   
	  
	    // 将获得的获得随机数转化为字符串   
	    String fixLenthString = String.valueOf(pross);   
	  
	    // 返回固定的长度的随机数   
	    return fixLenthString.substring(1, strLength + 1);   
	} 
	
	/**
	 * 生成6位数字随机码
	 * @author Ronny
	 */
	final static String str = "0123456789";
	public static String getCode(int strLength){
		Random r = new Random();
		String res = "";
		for(int i=0;i<strLength; i++){
			int x = r.nextInt(9);
			res += str.charAt(x);
		}
		return res;
	}
	
	/**
	 * 将 元 转换为 分
	 * @param amount 金额；单位：元；double 型
	 * @return 返回等值的金额；金额：分；long型
	 */
	public static long yuan2Cents(double amount){
		long cents = new Double(Arith.mul(amount, 100.0)).longValue();
		return cents;
	}
	
	/**
	 * 将 分 转换为 元
	 * @param amount 金额；单位：分；long 型
	 * @return 返回等值的金额；金额：元；double型
	 */
	public static double cents2Yuan(long amount){
		double yuan = Arith.div(amount, 100);
		return yuan;
	}
}
