package com.ets.business.equipment.dao;

import com.ets.business.equipment.entity.WMControl;
import com.ets.business.equipment.entity.nb_watermeter_equipment;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     EquipmentDao.java 
 * @Description:   水表设备连接数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:30:15
 */
public interface EquipmentDao {

	/**
	 * 
	* @Title: selectWaterMeterCusId 
	* @Description: 根据客户id和户号查询水表信息,若户号为空则查询全部水表信息
	* @param: @param map(imei:水表唯一号，customerId：客户ID)
	* @return: List<nb_watermeter_equipment>    
	* @Date: 2019年7月25日 下午2:34:50  
	 */
	List<nb_watermeter_equipment> selectWaterMeterCusId(Map<String, String> map);
	
	/**
	 * 
	* @Title: selectWMControlCusId 
	* @Description: 根据客户id查询水表的阀控状态
	* @param: @param map(imei:水表唯一号，customerId：客户ID)
	* @return: List<WMControl>    
	* @Date: 2019年7月25日 下午2:35:18  
	 */
	List<WMControl> selectWMControlCusId(Map<String, String> map);
	
}
