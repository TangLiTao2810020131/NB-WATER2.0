package com.ets.system.cus_commandlog.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.cus_commandlog.entity.nb_command_send_log;


public interface CommandSendLogDao {


	void insertCommandSendLog(nb_command_send_log entity);

	void updateCommandSendLog(nb_command_send_log entity);

	List<nb_command_send_log> selectCommandSendLog(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	nb_command_send_log info(Map<String, Object> map);
	
	String selectNewestTime(String deviceid);
	

}
