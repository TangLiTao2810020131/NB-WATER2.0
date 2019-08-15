package com.ets.system.sysCommandSendLog.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.sysCommandSendLog.entity.tb_sys_command_send_log;



public interface SysCommandSendLogDao {


	void insertSysCommandSendLog(tb_sys_command_send_log entity);

	void updateSysCommandSendLog(tb_sys_command_send_log entity);

	List<tb_sys_command_send_log> selectSysCommandSendLog(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	tb_sys_command_send_log info(Map<String, Object> map);
	
	String selectNewestTime(String deviceid);
	

}
