package com.ets.business.equipment.service;

import com.ets.business.equipment.dao.EquipmentDao;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * 
 * @ClassName:     EquipmentService.java 
 * @Description:   水表设备业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:36:36
 */
@Service
@Transactional
public class EquipmentService {
	
	@Resource
    EquipmentDao equipmentDao;

	
	/**
	 * 
	* @Title: queryWMEinfoByDeviceId 
	* @Description: 根据设备ID查询客户端水表设备
	* @param: @param deviceid 设备ID
	* @return: nb_watermeter_equipment    
	* @Date: 2019年7月25日 下午5:00:55  
	 */
	public nb_watermeter_equipment queryWMEinfoByDeviceId(String deviceid){
		try {
			return equipmentDao.selectWMEinfoByDeviceId(deviceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: updateOnlinePRS 
	* @Description: 每上报一次就更新设备在线、信号、信噪比等数据
	* @param: @param equipment设备数据    
	* @return: void    
	* @Date: 2019年7月25日 下午5:18:25  
	 */
	public void updateOnlinePRS(nb_watermeter_equipment equipment) {
		try {
				equipmentDao.updateOnlinePRS(equipment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
