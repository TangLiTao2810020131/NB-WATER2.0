package com.ets.business.equipentrecord.dao;

import com.ets.business.equipentrecord.entity.nb_watermeter_record;

import java.util.List;
import java.util.Map;

/**
 * @author 吴浩
 * @create 2019-02-22 14:28
 */
public interface EquipmentRecordDao {
	
	public List<nb_watermeter_record> getEquipmentRecordList(Map<String, Object> map);

	public long getCount(Map<String, Object> map);

	public nb_watermeter_record getEquipmentRecord(Map<String, Object> map);

	public void insertEquipmentRecord(nb_watermeter_record entity);

	public void updateEquipmentRecord(nb_watermeter_record entity);
}
