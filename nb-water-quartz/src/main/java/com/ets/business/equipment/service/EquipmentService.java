package com.ets.business.equipment.service;

import com.ets.business.equipment.dao.EquipmentDao;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @ClassName:     EquipmentService.java 
 * @Description:   水表设备操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午8:29:50
 */
@Service
@Transactional
public class EquipmentService {
	
	@Resource
    EquipmentDao equipmentDao;

	/**
	 * 
	* @Title: queryEquipmentList 
	* @Description: 根据客户ID查询该客户所有相关水表
	* @param: @param customerId
	* @return: List<nb_watermeter_equipment>    
	* @Date: 2019年7月24日 下午8:30:10  
	 */
	public List<nb_watermeter_equipment> queryEquipmentList(String customerId) {
		try {
			return equipmentDao.selectEquipmentList(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: infoEquipment 
	* @Description: 根据水表ID查询水表信息
	* @param: @param id
	* @return: nb_watermeter_equipment    
	* @Date: 2019年7月24日 下午8:37:38  
	 */
	public nb_watermeter_equipment infoEquipment(String id) {
		try {
			return equipmentDao.infoEquipment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: eidtOnline 
	* @Description: 更新水表为离线状态
	* @param: @param equipment    
	* @Date: 2019年7月24日 下午8:21:09  
	 */
	public void eidtOnline(nb_watermeter_equipment equipment) {
		try {
				equipmentDao.updateOnline(equipment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
