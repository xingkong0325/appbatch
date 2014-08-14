package com.xinhuanet.batch.common;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public abstract class BaseDAO{
	/**
	 * 从库标识
	 */
	protected final static Boolean READ = true;
	
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private JdbcTemplate jdbcTemplateRead;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateRead;

    @Autowired
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                dataSource);
    }

    @Autowired
    @Resource(name="dataSourceRead")
    public void setDataSourceRead(DataSource dataSourcePlatform){
        this.jdbcTemplateRead = new JdbcTemplate(dataSourcePlatform);
        this.namedParameterJdbcTemplateRead = new NamedParameterJdbcTemplate(dataSourcePlatform);
    }
    
    /**
     * 使用主库进行数据库写操作
     * @return
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }
    /**
     * 使用从库进行数据库读取
     * @param read 是否使用从库
     * @return 如果read为true时使用从库，否则使用主库
     */
    public JdbcTemplate getJdbcTemplate(boolean read) {
    	if(read){
    		return this.getJdbcTemplateRead();
    	}
        return this.getJdbcTemplate();
    }
    /**
     * 使用从库进行数据库读取
     * @param read 是否使用从库
     * @return 如果read为true时使用从库，否则使用主库
     */
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(boolean read) {
    	if(read){
    		return this.getNamedParameterJdbcTemplateRead();
    	}
        return this.getNamedParameterJdbcTemplate();
    }

    private JdbcTemplate getJdbcTemplateRead() {
        return jdbcTemplateRead;
    }

    private NamedParameterJdbcTemplate getNamedParameterJdbcTemplateRead() {
        return namedParameterJdbcTemplateRead;
    }
}
