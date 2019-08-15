package com.ets.system.cus.dao;

import com.ets.system.cus.entity.nb_cus;

/**
 * 
 * @ClassName:     CusDao.java 
 * @Description:   客户数据操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 上午11:58:46
 */
public interface CusDao {
	
	/**
	 * 
	* @Title: selectCusByKey 
	* @Description: 根据客户秘钥查询客户信息
	* @param: @param secretkey
	* @return: nb_cus    
	* @Date: 2019年7月25日 上午11:58:35  
	 */
	public nb_cus selectCusByKey(String secretkey);

}
