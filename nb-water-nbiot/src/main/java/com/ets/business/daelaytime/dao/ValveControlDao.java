package com.ets.business.daelaytime.dao;

import com.ets.business.daelaytime.entity.nb_delay_time_valvecontrol;

/**
 * 
 * @ClassName:     ValveControlDao.java 
 * @Description:   设置阀控命令下发延迟时间数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:32:59
 */
public interface ValveControlDao {

	/**
	 * 
	* @Title: info 
	* @Description: 根据客户ID获取设置阀控命令下发延迟时间
	* @param: @param customerId 客户ID
	* @return: nb_delay_time_valvecontrol    
	* @Date: 2019年7月25日 下午6:31:14  
	 */
	public nb_delay_time_valvecontrol info(String customerId);
}
