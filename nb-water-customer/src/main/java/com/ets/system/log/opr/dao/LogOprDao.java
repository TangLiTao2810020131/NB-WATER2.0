package com.ets.system.log.opr.dao;

import com.ets.system.log.opr.entity.tb_log_opr;

import java.util.List;
import java.util.Map;



/**
 * @author: 姚轶文
 * @date:2018年9月5日 下午4:20:14
 * @version :
 * 
 */
public interface LogOprDao {

	public List<tb_log_opr> getLogs(Map map);
	public long getCount(Map map);
	public void addLog(tb_log_opr log);
	public tb_log_opr infoLog(String id);

}
