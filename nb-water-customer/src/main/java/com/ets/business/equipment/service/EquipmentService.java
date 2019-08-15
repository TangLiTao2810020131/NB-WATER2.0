package com.ets.business.equipment.service;

import com.ets.business.equipment.dao.EquipmentDao;
import com.ets.business.equipment.entity.EquipmentModel;
import com.ets.business.equipment.entity.WMControl;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 水表设备操作数据库类
 * @author wh
 *
 */
@Service
@Transactional
public class EquipmentService {
	
	@Resource
    EquipmentDao equipmentDao;

	/**
	 * 获取水表设备列表
	 * @param map 查询列表条件集合
	 * @return
	 */
	public List<nb_watermeter_equipment> getEquipment(Map<String, Object> map) {
		try {
			return equipmentDao.selectEquipment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return equipmentDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void opentionEquipment(nb_watermeter_equipment equipment) {
		try {
			if(equipment.getId()!=null && !equipment.getId().equals(""))
			{
				equipmentDao.updateEquipment(equipment);
			}
			else
			{
				if("".equals(equipment.getStatus()) || equipment.getStatus() == null){
					equipment.setStatus("0");
				}
				equipment.setId(UUIDUtils.getUUID());
				equipment.setCtime(DateTimeUtils.getnowdate());
				equipmentDao.insertEquipment(equipment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public nb_watermeter_equipment infoEquipment(Map<String, Object> map) {
		try {
			return equipmentDao.infoEquipment(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EquipmentModel infoEquipmentM(Map<String, Object> map) {
		try {
			return equipmentDao.infoEquipmentM(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public void deleteEquipment(String[] id) {
		try {
			equipmentDao.deleteEquipment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String infoEquipment(String[] id) {
		try {
			String watermetercode = "";
			List<nb_watermeter_equipment> list = equipmentDao.infoEquipmentList(id);
			if(list.size() > 0){
				for (nb_watermeter_equipment equipment : list) {
					watermetercode += equipment.getWatermetercode()+",";
				}
			}
			return watermetercode.substring(0,watermetercode.length()-1);
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<nb_watermeter_equipment> infoEquipmentDeviceId(String[] id) {
		try {
			List<nb_watermeter_equipment> list = equipmentDao.infoEquipmentList(id);
			return list;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_watermeter_equipment> getEquipmentList(String customerId) {
		try {
			return equipmentDao.selectEquipmentList(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int isCheckIMEI(Map<String, Object> map) {
		int num = 0;
		try {
			num = equipmentDao.isCheckIMEI(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}
	
	public nb_watermeter_equipment queryWMEinfoByDeviceId(String deviceid){
		try {
			return equipmentDao.selectWMEinfoByDeviceId(deviceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_watermeter_equipment> queryEquipmentDoorId(Map<String, String> eMap) {
		try {
			return equipmentDao.selectEquipmentDoorId(eMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_watermeter_equipment> queryWaterMeterCusId(Map<String, String> map) {
		try {
			return equipmentDao.selectWaterMeterCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<WMControl> queryWMControlCusId(Map<String, String> map) {
		try {
			return equipmentDao.selectWMControlCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int isCheckWaterNum(Map<String, Object> map) {
		int num = 0;
		try {
			num = equipmentDao.selectWaterNum(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}
	
	public void updateOnlinePRS(nb_watermeter_equipment equipment) {
		try {
				equipmentDao.updateOnlinePRS(equipment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateOnline(nb_watermeter_equipment equipment) {
		try {
				equipmentDao.updateOnline(equipment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
