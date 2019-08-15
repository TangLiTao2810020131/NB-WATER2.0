package com.ets.business.meter.meterread.dao;

import com.ets.business.meter.meterread.entity.MeterreadModel;
import com.ets.business.meter.meterread.entity.nb_meterread;
import com.ets.business.statistics.owner.entity.nb_owner_water_statistics;

import java.util.List;
import java.util.Map;

public interface MeterreadDao {

	public void insertMeterread(nb_meterread entity);
	public void deleteMeterread(String id[]);
	public void updateMeterread(nb_meterread entity);
	public nb_meterread infoMeterread(String id);
	public MeterreadModel meterreadIfo(String id);
	public List<MeterreadModel> selectMeterread(Map<String, Object> map);
	public long selectCount(Map<String, Object> map);
	public nb_meterread infoMeterReadByWaterID(Map<String, Object> map);
	public void updateMeterreadweterId(nb_meterread entity);
	public nb_meterread queryMeterRead(Map<String, Object> map);
	
	public List<nb_meterread> selectWMHistoryNumCusId(Map<String, String> map);
	
	public long queryAllReadCount();
	
	public long queryAllReadCountOnLine();
	
	public List<nb_owner_water_statistics> queryAllRead(Map<String, Object> map);
	public List<nb_meterread> queryAllReadOnLine(Map<String, Object> map);
}
