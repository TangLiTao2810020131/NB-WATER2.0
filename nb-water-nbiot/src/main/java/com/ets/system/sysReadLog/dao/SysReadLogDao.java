package com.ets.system.sysReadLog.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.sysReadLog.entity.tb_sys_read_log;


public interface SysReadLogDao {


	void insertSysReadLog(tb_sys_read_log entity);

	List<tb_sys_read_log> selectSysReadLogs(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	tb_sys_read_log info(Map<String, Object> map);


}
