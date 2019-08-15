package com.ets.business.meterreadlog.dao;

import java.util.Map;
import com.ets.business.meterreadlog.entity.nb_meterread_log;

public interface MeterreadlogDao {

	/**
	 * 
	* @Title: selectLogByEidTime 
	* @Description: 查询某一天最后一条抄表记录
	* @param: @param map
	* @return: nb_meterread_log    
	* @Date: 2019年7月24日 下午8:24:11  
	 */
	public nb_meterread_log selectLogByEidTime(Map<String, Object> map);
	
	/**
	 * 
	* @Title: selectBalanceEndTimeMeterreadLog 
	* @Description:  查询需要结算的月份最后一次的读数
	* @param: @param map
	* @param: @return    
	* @return: nb_meterread_log    
	* @Date: 2019年7月24日 下午8:48:39  
	 */
	public nb_meterread_log selectBalanceEndTimeMeterreadLog(Map<String, Object> map);
	
}
