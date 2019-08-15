package com.ets.business.daelaytime.dao;

import com.ets.business.daelaytime.entity.nb_delay_time_basicnum;

/**
 * 
 * @ClassName:     BasicNumDao.java 
 * @Description:   表读数命令下发延迟时间数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:32:12
 */
public interface BasicNumDao {
	
	/**
	 * 
	* @Title: info 
	* @Description: 根据客户ID获取设置表读数命令下发延迟时间
	* @param: @param customerId 客户ID
	* @return: nb_delay_time_basicnum    
	* @Date: 2019年7月25日 下午6:30:34  
	 */
	public nb_delay_time_basicnum info(String customerId);
}
