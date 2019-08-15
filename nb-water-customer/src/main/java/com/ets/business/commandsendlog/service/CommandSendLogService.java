package com.ets.business.commandsendlog.service;

import com.ets.business.commandsendlog.dao.CommandSendLogDao;
import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class CommandSendLogService {
	
	@Resource
	CommandSendLogDao commandSendLogDao;
	
	public List<nb_command_send_log> getCommandSendLog(Map<String, Object> map) {
		try {
			return commandSendLogDao.selectCommandSendLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addCommandSendLog(nb_command_send_log entity){
		try {
			entity.setId(UUIDUtils.getUUID());
			entity.setCtime(DateTimeUtils.getnowdate());
			commandSendLogDao.insertCommandSendLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editCommandSendLog(nb_command_send_log entity){
		try {
			entity.setCtime(DateTimeUtils.getnowdate());
			commandSendLogDao.updateCommandSendLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getCount(Map<String, Object> map) {
		try {
			return commandSendLogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public nb_command_send_log info(Map<String, Object> map) {
		try {
			return commandSendLogDao.info(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String queryNewestTime(String deviceid) {
		try {
			return commandSendLogDao.selectNewestTime(deviceid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
