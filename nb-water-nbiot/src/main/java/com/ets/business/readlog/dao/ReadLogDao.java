package com.ets.business.readlog.dao;

import com.ets.business.readlog.entity.nb_read_log;

/**
 * 
 * @ClassName:     ReadLogDao.java 
 * @Description:   上报数据数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:51:31
 */
public interface ReadLogDao {


	/**
	 * 
	* @Title: insertReadLog 
	* @Description: 上报数据新增记录
	* @param: @param entity 上报数据对象  
	* @return: void    
	* @Date: 2019年7月25日 下午7:51:47  
	* @throws
	 */
	void insertReadLog(nb_read_log entity);

}
