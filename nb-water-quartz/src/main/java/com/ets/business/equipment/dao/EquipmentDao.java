package com.ets.business.equipment.dao;

import com.ets.business.equipment.entity.nb_watermeter_equipment;

import java.util.List;

/**
 * 水表设备连接数据库dao类
 * @author Administrator
 *
 */
public interface EquipmentDao {


	/**
	 * 
	* @Title: selectEquipmentList 
	* @Description: 根据客户ID查询该客户所有相关水表
	* @param: @param customerId
	* @return: List<nb_watermeter_equipment>    
	* @Date: 2019年7月24日 下午8:32:12  
	 */
	List<nb_watermeter_equipment> selectEquipmentList(String customerId);
	
	/**
	 * 
	* @Title: infoEquipment 
	* @Description: 根据水表ID查询水表信息
	* @param: @param id
	* @return: nb_watermeter_equipment    
	* @Date: 2019年7月24日 下午8:37:55  
	* @throws
	 */
	nb_watermeter_equipment infoEquipment(String id);

	/**
	 * 
	* @Title: updateOnline 
	* @Description: 更新水表为离线状态
	* @param: @param equipment    
	* @return: void    
	* @Date: 2019年7月24日 下午8:21:36  
	 */
	void updateOnline(nb_watermeter_equipment equipment);
	
	
}
