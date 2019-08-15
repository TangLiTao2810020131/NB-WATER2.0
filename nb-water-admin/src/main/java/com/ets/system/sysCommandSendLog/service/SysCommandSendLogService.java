package com.ets.system.sysCommandSendLog.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.sysCommandSendLog.dao.SysCommandSendLogDao;
import com.ets.system.sysCommandSendLog.entity.tb_sys_command_send_log;


@Service
@Transactional
public class SysCommandSendLogService {
	
	@Resource
	SysCommandSendLogDao sysCommandSendLogDao;
	
	public List<tb_sys_command_send_log> getSysCommandSendLog(Map<String, Object> map) {
		try {
			return sysCommandSendLogDao.selectSysCommandSendLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addSysCommandSendLog(tb_sys_command_send_log entity){
		try {
			entity.setId(UUIDUtils.getUUID());
			entity.setCtime(DateTimeUtils.getnowdate());
			sysCommandSendLogDao.insertSysCommandSendLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editSysCommandSendLog(tb_sys_command_send_log entity){
		try {
			entity.setCtime(DateTimeUtils.getnowdate());
			sysCommandSendLogDao.updateSysCommandSendLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getCount(Map<String, Object> map) {
		try {
			return sysCommandSendLogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public tb_sys_command_send_log info(Map<String, Object> map) {
		try {
			return sysCommandSendLogDao.info(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String queryNewestTime(String deviceid) {
		try {
			return sysCommandSendLogDao.selectNewestTime(deviceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
