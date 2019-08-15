package com.ets.business.meter.meterread.service;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.meter.meterread.dao.MeterreadDao;
import com.ets.business.meter.meterread.entity.MeterreadModel;
import com.ets.business.meter.meterread.entity.nb_meterread;
import com.ets.business.statistics.owner.entity.nb_owner_water_statistics;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MeterreadService {

	@Resource
    MeterreadDao meterreadDao;
	
	
	/**
	 * 新建水表时建立水表初始读数
	 * @param equipment
	 */
	public void addMeterread(nb_watermeter_equipment equipment, String type) {

		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("watermeterid", equipment.getId());
			nb_meterread  meterreadDB  = this.infoMeterreadByWaterID(map);
			if(meterreadDB == null){
				meterreadDB  = new nb_meterread();
			}
			meterreadDB.setCustomercode(equipment.getCustomercode());
			meterreadDB.setWatermeterid(equipment.getId());
			meterreadDB.setType(type);
			meterreadDB.setValue(equipment.getBasenum());
			meterreadDB.setOptiontime(meterreadDB.getOptiontime() == null || meterreadDB.getOptiontime() == "" ? equipment.getInstalldate() : meterreadDB.getOptiontime());
			meterreadDB.setOptionuser(equipment.getOptionuser());
			meterreadDB.setCtime(DateTimeUtils.getnowdate());
			this.insetMeterread(meterreadDB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void insetMeterread(nb_meterread entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			meterreadDao.updateMeterread(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			meterreadDao.insertMeterread(entity);
		}
	}
	
	public void updateMeterread(nb_meterread entity)
	{
		try {
			meterreadDao.updateMeterreadweterId(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public nb_meterread infoMeterread(String id)
	{
		return meterreadDao.infoMeterread(id);
	}

	public MeterreadModel meterreadIfo(String id)
	{
		return meterreadDao.meterreadIfo(id);
	}
	
	public void deleteMeterread(String id[])
	{
		meterreadDao.deleteMeterread(id);
	}

	public List<MeterreadModel> getMeterread(Map<String, Object> map) {
		try {
			return meterreadDao.selectMeterread(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getCount(Map<String, Object> map) {
		try {
			return meterreadDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public nb_meterread infoMeterreadByWaterID(Map<String,Object> map) {
		try {
			return meterreadDao.infoMeterReadByWaterID(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 更新水表最新读书并添加自动抄表记录
	 * @param deviceId 平台设备ID
	 * @param readnum 最新表读数
	 * @param equipmentDB 设备对象
	 */
	public void AddMeterRead(String deviceId, String readnum, nb_watermeter_equipment equipmentDB){
		Map<String,Object> map = new HashMap<String, Object>(); 
		map.put("id", equipmentDB.getId());
		nb_meterread m = infoMeterreadByWaterID(map);
		m.setValue(readnum);
		m.setOptionuser("");
		m.setOptiontime(DateTimeUtils.getnowdate());
		m.setType("1");
		insetMeterread(m);
	}
	
	public nb_meterread queryMeterRead(Map<String,Object> map){
		return meterreadDao.queryMeterRead(map);
	}
	
	public List<nb_meterread> queryWMHistoryNumCusId(Map<String,String> map){
		return meterreadDao.selectWMHistoryNumCusId(map);
	}
	
	public List<nb_owner_water_statistics> queryAllRead(Map<String, Object> map){
		return meterreadDao.queryAllRead(map);
	}
	
	public long queryAllReadCount(){
		return meterreadDao.queryAllReadCount();
	}
	
	public long queryAllReadCountOnLine(){
		return meterreadDao.queryAllReadCountOnLine();
	}
	
	public List<nb_meterread> queryAllReadOnLine(Map<String, Object> map) {
		try {
			return meterreadDao.queryAllReadOnLine(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
