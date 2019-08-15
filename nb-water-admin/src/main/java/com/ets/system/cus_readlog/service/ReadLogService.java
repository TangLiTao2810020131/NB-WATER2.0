package com.ets.system.cus_readlog.service;

import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.cus_readlog.dao.ReadLogDao;
import com.ets.system.cus_readlog.entity.nb_read_log;

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
