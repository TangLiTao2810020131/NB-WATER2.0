package com.ets.business.equipment.dao;

import com.ets.business.equipment.entity.nb_watermeter_equipment;

/**
 * 
 * @ClassName:     EquipmentDao.java 
 * @Description:   水表设备连接数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:37:53
 */
public interface EquipmentDao {

	/**
	 * 
	* @Title: selectWMEinfoByDeviceId 
	* @Description: 根据设备ID查询客户端水表设备
	* @param: @param deviceid 设备ID
	* @return: nb_watermeter_equipment    
	* @Date: 2019年7月25日 下午6:37:16  
	 */
	nb_watermeter_equipment selectWMEinfoByDeviceId(String deviceid);

	/**
	 * 
	* @Title: updateOnlinePRS 
	* @Description: 每上报一次就更新设备在线、信号、信噪比等数据
	* @param: @param equipment设备数据   
	* @return: void    
	* @Date: 2019年7月25日 下午6:37:27  
	 */
	void updateOnlinePRS(nb_watermeter_equipment equipment);
	
}
