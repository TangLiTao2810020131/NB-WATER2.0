package com.ets.business.meterread.dao;

import com.ets.business.meterread.entity.nb_meterread;
import com.ets.business.statistic.entity.nb_water_statistics;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     MeterreadDao.java 
 * @Description:   最新表读数表数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午6:40:47
 */
public interface MeterreadDao {


	/**
	 * 
	* @Title: queryAllReadCount 
	* @Description: 查询所有水表个数
	* @return: long    
	* @Date: 2019年7月24日 下午6:42:58  
	 */
	public long queryAllReadCount();
	
	/**
	 * 
	* @Title: queryAllRead 
	* @Description: 分页查询最新表读数相关的所以水表设备
	* @param: @param map
	* @return: List<nb_water_statistics>    
	* @Date: 2019年7月24日 下午6:44:21  
	 */
	public List<nb_water_statistics> queryAllRead(Map<String, Object> map);
	
	/**
	 * 
	* @Title: queryAllReadCountOnLine 
	* @Description: 查询最新抄表数据的最新上报数据的所有水表个数
	* @return: long    
	* @Date: 2019年7月24日 下午8:16:13  
	 */
	public long queryAllReadCountOnLine();
	
	/**
	 * 
	* @Title: queryAllReadOnLine 
	* @Description: 分页查询最新抄表数据的最新上报数据的所有水表
	* @param: @param map
	* @return: List<nb_meterread>    
	* @Date: 2019年7月24日 下午8:20:39  
	 */
	public List<nb_meterread> queryAllReadOnLine(Map<String, Object> map);
}
