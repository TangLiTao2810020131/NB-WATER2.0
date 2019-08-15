package com.ets.business.readlog.service;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.readlog.dao.ReadLogDao;
import com.ets.business.readlog.entity.nb_read_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class ReadLogService {
	
	@Resource
	ReadLogDao readLogDao;
	
	public void addReadLog(nb_read_log entity){
		try {
			readLogDao.insertReadLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<nb_read_log> getReadLog(Map<String, Object> map) {
		try {
			return readLogDao.selectReadLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return readLogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public nb_read_log info(Map<String, Object> map) {
		try {
			return readLogDao.info(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
