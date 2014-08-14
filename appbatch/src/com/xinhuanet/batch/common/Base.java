package com.xinhuanet.batch.common;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Base implements ApplicationContextAware{
	protected final Logger logger = Logger.getLogger(getClass());
	ApplicationContext ctx = null;
	
	@Autowired
	protected PropertiesConfiguration props;
	
	public String getProperty(String prop,String defaultVal){
		return props.getString(prop, defaultVal);
	}
	
	public String getProperty(String prop){
		return props.getString(prop);
	}
	
	public int getPropertyInt(String prop){
		return props.getInt(prop);
	}
	
	/**
	 * 获取spring配置文件service的上下文
	 * @param <T>
	 * @param name  Spring配置文件的id
	 * @return Spring注入的方法
	 */
	public <T> T getBean(String name, Class<T> t) {
		return ctx.getBean(name, t);
	}
	/**
	 * 获取spring配置文件service的上下文
	 * @param <T>
	 * @param name  Spring配置文件的id
	 * @return Spring注入的方法
	 */
	public <T> T getBean(Class<T> t) {
		return ctx.getBean(t);
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ctx = arg0;
	}
}
