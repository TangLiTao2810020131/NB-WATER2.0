package com.ets.system.sysEquipment.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.sysEquipment.dao.SysEquipmentDao;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;


/**
 * 水表设备操作数据库类
 * @author wh
 *
 */
@Service
@Transactional
public class SysEquipmentService {
	
	@Resource
	SysEquipmentDao sysEquipmentDao;

	public List<tb_sys_equipment> getSysEquipment(Map<String, Object> map) {
		return sysEquipmentDao.selectSysEquipment(map);
	}

	public long getCount(Map<String, Object> map) {
		return sysEquipmentDao.selectCount(map);
	}

	public int isCheckIMEI(String imei) {
		
		int num = 0;
		
		try {
			
			num = sysEquipmentDao.isCheckIMEI(imei);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return num;
	}

	public void opentionSysEquipment(tb_sys_equipment equipment) {
		
		try {
			
			if(equipment.getUuid() != null && !equipment.getUuid().equals("")){
				
				sysEquipmentDao.updateSysEquipment(equipment);
			
			}else{
				
				equipment.setUuid(UUIDUtils.getUUID());
				
				equipment.setCtime(DateTimeUtils.getnowdate());
				
				equipment.setDstatus("0");
				
				equipment.setCstatus("0");
				
				sysEquipmentDao.insertSysEquipment(equipment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteSysEquipment(String[] id) {

		try {
			
			sysEquipmentDao.deleteSysEquipment(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void updateSysEquipment(String deviceId) {
		
		try {
			
			sysEquipmentDao.updateSysEquipment(deviceId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public tb_sys_equipment querySysWMEinfoByDeviceId(String deviceid) {
		
		try {
			
			return sysEquipmentDao.selectSysWMEinfoByDeviceId(deviceid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public void open(String deviceId)
    {
    	sysEquipmentDao.open(deviceId);
    }

    public void close(String deviceId)
    {
    	sysEquipmentDao.close(deviceId);
    }
    
    public void updateCstatus(String deviceId)
    {
    	sysEquipmentDao.updateCstatus(deviceId);
    }
    
    public void updateDstatus(String deviceId)
    {
    	sysEquipmentDao.updateDstatus(deviceId);
    }

	public int getWaterNum(String batchid) {
		return sysEquipmentDao.selectWaterNum(batchid);
	}
}
