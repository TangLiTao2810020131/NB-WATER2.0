package com.ets.business.owner.dao;

import com.ets.business.owner.entity.nb_owner;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     OwnerDao.java 
 * @Description:   业主数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午12:01:28
 */
public interface OwnerDao {

	/**
	 * 
	* @Title: queryOwnerCusId 
	* @Description: 根据客户id和户号查询业主信息,若户号为空则查询全部业主信息
	* @param: @param map
	* @param: @return    
	* @return: List<nb_owner>    
	* @Date: 2019年7月25日 下午12:01:09  
	* @throws
	 */
	List<nb_owner> queryOwnerCusId(Map<String, String> map);

}
