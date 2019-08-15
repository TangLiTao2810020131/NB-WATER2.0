package com.ets.business.meterreadlog.dao;

import java.util.List;
import java.util.Map;
import com.ets.business.meterreadlog.entity.nb_meterread_log;

/**
 * 
 * @ClassName:     MeterreadlogDao.java 
 * @Description:   最新抄表记录日志数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:32:14
 */
public interface MeterreadlogDao {

	/**
	 * 
	* @Title: selectWMLatestNumCusId 
	* @Description: 根据客户id查询水表的历史抄表记录
	* @param: @param map(imei:水表唯一号，customerId：客户ID，startDate：查询开始时间，endDate：查询结束时间)
	* @return: List<nb_meterread_log>    
	* @Date: 2019年7月25日 下午2:32:36  
	 */
	public List<nb_meterread_log> selectWMLatestNumCusId(Map<String, String> map);
	
}
