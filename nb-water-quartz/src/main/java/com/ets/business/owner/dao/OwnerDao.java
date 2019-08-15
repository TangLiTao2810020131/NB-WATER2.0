package com.ets.business.owner.dao;

import com.ets.business.owner.entity.nb_owner;
import java.util.Map;

/**
 * 
 * @ClassName:     OwnerDao.java 
 * @Description:   业主数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午8:44:01
 */
public interface OwnerDao {

	/**
	 * 
	* @Title: infoOwner 
	* @Description: 查询业主信息
	* @param: @param map
	* @return: nb_owner    
	* @Date: 2019年7月24日 下午8:43:42  
	 */
	nb_owner infoOwner(Map<String, Object> map);
}
