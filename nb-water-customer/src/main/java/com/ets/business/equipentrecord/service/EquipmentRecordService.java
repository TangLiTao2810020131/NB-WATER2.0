package com.ets.business.equipentrecord.service;

import com.ets.business.equipentrecord.dao.EquipmentRecordDao;
import com.ets.business.equipentrecord.entity.nb_watermeter_record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-02-22 14:27
 */
@Service
@Transactional
public class EquipmentRecordService {
	
	@Resource
    EquipmentRecordDao equipmentRecordDao;
	
	public List<nb_watermeter_record> queryEquipmentRecordList(Map<String,Object> map){
		try {
			return equipmentRecordDao.getEquipmentRecordList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long queryCount(Map<String,Object> map){
		try {
			return equipmentRecordDao.getCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public nb_watermeter_record queryEquipmentRecord(Map<String,Object> map){
		try {
			return equipmentRecordDao.getEquipmentRecord(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addEquipmentRecord(nb_watermeter_record entity){
		try {
			equipmentRecordDao.insertEquipmentRecord(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editEquipmentRecord(nb_watermeter_record entity){
		try {
			equipmentRecordDao.updateEquipmentRecord(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
