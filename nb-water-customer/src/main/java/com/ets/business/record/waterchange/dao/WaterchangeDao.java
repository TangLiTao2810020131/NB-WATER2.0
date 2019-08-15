package com.ets.business.record.waterchange.dao;

import com.ets.business.record.waterchange.entity.nb_watermeter_changerecord;

import java.util.List;
import java.util.Map;

/**
 * 水表更换连接数据库dao
 * @author wh
 *
 */
public interface WaterchangeDao {

	List<nb_watermeter_changerecord> selectWaterchange(Map<String, Object> map);
	long selectCount(Map<String, Object> map);
	void insertWaterchange(nb_watermeter_changerecord changerecord);
	nb_watermeter_changerecord infoWaterchange(Map<String, Object> map);

	

}
