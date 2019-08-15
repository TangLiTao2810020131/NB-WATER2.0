package com.ets.system.sysReadLog.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ets.system.sysReadLog.dao.SysReadLogDao;
import com.ets.system.sysReadLog.entity.tb_sys_read_log;

@Service
@Transactional
public class SysReadLogService {
	
	@Resource
	SysReadLogDao sysReadLogDao;
	
	public void addSysReadLog(tb_sys_read_log entity){
		try {
			sysReadLogDao.insertSysReadLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<tb_sys_read_log> getReadLog(Map<String, Object> map) {
		try {
			return sysReadLogDao.selectSysReadLogs(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return sysReadLogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public tb_sys_read_log info(Map<String, Object> map) {
		try {
			return sysReadLogDao.info(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
