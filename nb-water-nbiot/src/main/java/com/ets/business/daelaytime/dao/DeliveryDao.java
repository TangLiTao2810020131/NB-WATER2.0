package com.ets.business.daelaytime.dao;

import com.ets.business.daelaytime.entity.nb_delay_time_delivery;

/**
 * 
 * @ClassName:     DeliveryDao.java 
 * @Description:   上报周期命令下发延迟时间数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:32:38
 */
public interface DeliveryDao {

	/**
	 * 
	* @Title: info 
	* @Description: 根据客户ID获取设置上报周期命令下发延迟时间
	* @param: @param customerId 客户ID
	* @return: nb_delay_time_delivery    
	* @Date: 2019年7月25日 下午6:30:55  
	 */
	public nb_delay_time_delivery info(String customerId);
}
