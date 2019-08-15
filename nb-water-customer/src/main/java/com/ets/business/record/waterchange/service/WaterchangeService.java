package com.ets.business.record.waterchange.service;

import com.ets.business.record.waterchange.dao.WaterchangeDao;
import com.ets.business.record.waterchange.entity.nb_watermeter_changerecord;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 水表更换操作数据库累
 * @author wh
 *
 */
@Service
@Transactional
public class WaterchangeService {
	
	@Resource
    WaterchangeDao waterchangeDao;

	public List<nb_watermeter_changerecord> getWaterchange(Map<String, Object> map) {
		try {
			return waterchangeDao.selectWaterchange(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public long getCount(Map<String, Object> map) {
		try {
			return waterchangeDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 操作接入类型字段数据
	 * @param changerecord
	 */
	public void addWaterchange(nb_watermeter_changerecord changerecord) {
		try {
				changerecord.setId(UUIDUtils.getUUID());
				changerecord.setCtime(DateTimeUtils.getnowdate());
				waterchangeDao.insertWaterchange(changerecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public nb_watermeter_changerecord infoWaterchange(Map<String, Object> map) {
		try {
			return waterchangeDao.infoWaterchange(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}






}
