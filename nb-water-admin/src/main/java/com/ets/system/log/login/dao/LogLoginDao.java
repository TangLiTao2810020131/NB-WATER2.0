package com.ets.system.log.login.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.log.login.entity.tb_log_login;

/**
 * @author: 姚轶文
 * @date:2018年9月5日 上午9:39:02
 * @version :
 * 
 */
public interface LogLoginDao {

	public List<tb_log_login> getLogs(Map map);
	public long getCount(Map map);
	public void addLog(tb_log_login log);
	public tb_log_login infoLog(String id);
}
