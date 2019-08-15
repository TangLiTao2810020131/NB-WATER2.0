package com.ets.business.equipment.service;

import com.ets.business.equipment.dao.EquipmentDao;
import com.ets.business.equipment.entity.WMControl;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     EquipmentService.java 
 * @Description:   水表设备业务操作类  
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:29:52
 */
@Service
@Transactional
public class EquipmentService {
	
	@Resource
    EquipmentDao equipmentDao;

	/**
	 * 
	* @Title: queryWaterMeterCusId 
	* @Description: 根据客户id和户号查询水表信息,若户号为空则查询全部水表信息
	* @param: @param map(imei:水表唯一号，customerId：客户ID)
	* @return: List<nb_watermeter_equipment>    
	* @Date: 2019年7月25日 下午2:25:27  
	 */
	public List<nb_watermeter_equipment> queryWaterMeterCusId(Map<String, String> map) {
		try {
			return equipmentDao.selectWaterMeterCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: queryWMControlCusId 
	* @Description: 根据客户id查询水表的阀控状态
	* @param: @param map(imei:水表唯一号，customerId：客户ID)
	* @return: List<WMControl>    
	* @Date: 2019年7月25日 下午2:34:12  
	 */
	public List<WMControl> queryWMControlCusId(Map<String, String> map) {
		try {
			return equipmentDao.selectWMControlCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
