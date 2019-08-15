package com.ets.business.meter.meterreadlog.dao;

import com.ets.business.meter.meterreadlog.entity.MeterreadLogModel;
import com.ets.business.meter.meterreadlog.entity.nb_meterread_log;

import java.util.List;
import java.util.Map;

public interface MeterreadlogDao {

	public void insertMeterreadlog(nb_meterread_log entity);
	public void deleteMeterreadlog(String id[]);
	public void updateMeterreadlog(nb_meterread_log entity);
	public nb_meterread_log infoMeterreadlog(String id);
	public long selectCount(Map<String, Object> map);
	public List<MeterreadLogModel> selectMeterreadlog(Map<String, Object> map);
	public List<MeterreadLogModel> selectMeterreadlogEX(Map<String, Object> map);

	public nb_meterread_log selectBalanceEndTimeMeterreadLog(Map<String, Object> map);
	public nb_meterread_log selectBalanceStartTimeMeterreadLog(Map<String, Object> map);
	public List<nb_meterread_log> selectWMLatestNumCusId(Map<String, String> map);
	
	public nb_meterread_log selectLogByEidTime(Map<String, Object> map);
	
}
