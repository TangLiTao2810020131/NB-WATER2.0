package com.ets.business.balance.dao;

import com.ets.business.balance.entity.nb_balance;
import java.util.Map;

/**
 * 
 * @ClassName:     BalanceDao.java 
 * @Description:   水表用水量结算数据可操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午8:53:34
 */
public interface BalanceDao {


	/**
	 * 
	* @Title: selectLastBalance 
	* @Description: 查询上月用水量
	* @param: @param map
	* @return: nb_balance    
	* @Date: 2019年7月24日 下午8:52:07  
	 */
	nb_balance selectLastBalance(Map<String, Object> map);

	/**
	 * 
	* @Title: insertBalance 
	* @Description: 添加结算记录
	* @param: @param balance    
	* @return: void    
	* @Date: 2019年7月24日 下午8:51:08  
	 */
	void insertBalance(nb_balance balance);

}
