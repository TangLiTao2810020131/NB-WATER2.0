package com.ets.business.meter.meterreadlog.service;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.meter.meterreadlog.dao.MeterreadlogDao;
import com.ets.business.meter.meterreadlog.entity.MeterreadLogModel;
import com.ets.business.meter.meterreadlog.entity.nb_meterread_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MeterreadlogService {

	@Resource
    MeterreadlogDao meterreadlogDao;
	
	
	/**
	 * 新建水表时建立水表初始读数日志
	 * @param equipment
	 */
	public void addMeterreadlog(nb_watermeter_equipment equipment, String type) {
		
		try {
			nb_meterread_log  meterreadlog  = new nb_meterread_log();
			meterreadlog.setWatermeterid(equipment.getId());
			meterreadlog.setCustomercode(equipment.getCustomercode());
			meterreadlog.setValue(equipment.getBasenum());
			meterreadlog.setType(type);
			meterreadlog.setOptionuser(equipment.getOptionuser());
			meterreadlog.setIssuccess("1");
			meterreadlog.setMessgae("");
			meterreadlog.setOptiontime(meterreadlog.getOptiontime() == null || meterreadlog.getOptiontime() == "" ? equipment.getInstalldate() : meterreadlog.getOptiontime());
			meterreadlog.setCtime(DateTimeUtils.getnowdate());
			this.insetMeterreadlog(meterreadlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insetMeterreadlog(nb_meterread_log entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			meterreadlogDao.updateMeterreadlog(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			entity.setCtime(DateTimeUtils.getnowdate());
			meterreadlogDao.insertMeterreadlog(entity);
		}
	}
	
	public nb_meterread_log infoMeterreadlog(String id)
	{
		return meterreadlogDao.infoMeterreadlog(id);
	}
	
	public void deleteMeterreadlog(String id[])
	{
		meterreadlogDao.deleteMeterreadlog(id);
	}

	public long getCount(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<MeterreadLogModel> getMeterreadlog(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectMeterreadlog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public nb_meterread_log queryBalanceEndTimeMeterreadLog(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectBalanceEndTimeMeterreadLog(map);
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
	public void AddMeterReadLog(String deviceId, String readnum, nb_watermeter_equipment equipmentDB){
		nb_meterread_log mlog = new nb_meterread_log();
		mlog.setWatermeterid(equipmentDB.getId());
		mlog.setValue(readnum);
		mlog.setType("1");
		mlog.setOptionuser("无");
		mlog.setOptiontime(DateTimeUtils.getnowdate());
		mlog.setMessgae("无");
		mlog.setIssuccess("1");
		mlog.setCustomercode(equipmentDB.getCustomercode());
		insetMeterreadlog(mlog);
	}

	public nb_meterread_log selectBalanceStartTimeMeterreadLog(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectBalanceStartTimeMeterreadLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<MeterreadLogModel> getMeterreadlogEX(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectMeterreadlogEX(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<nb_meterread_log> queryWMLatestNumCusId(Map<String, String> map) {
		try {
			return meterreadlogDao.selectWMLatestNumCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public nb_meterread_log queryLogByEidTime(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectLogByEidTime(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
