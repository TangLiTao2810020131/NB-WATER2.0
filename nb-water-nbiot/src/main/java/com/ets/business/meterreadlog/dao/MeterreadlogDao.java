package com.ets.business.meterreadlog.dao;

import com.ets.business.meterreadlog.entity.nb_meterread_log;

/**
 * 
 * @ClassName:     MeterreadlogDao.java 
 * @Description:   最新抄表记录日志数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:42:14
 */
public interface MeterreadlogDao {

	/**
	 * 
	* @Title: insertMeterreadlog 
	* @Description: 新增最新抄表日志记录
	* @param: @param entity 最新抄表日志对象 
	* @return: void    
	* @Date: 2019年7月25日 下午7:41:44  
	* @throws
	 */
	public void insertMeterreadlog(nb_meterread_log entity);
}
