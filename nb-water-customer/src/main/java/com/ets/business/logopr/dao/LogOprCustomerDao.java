package com.ets.business.logopr.dao;

import com.ets.business.logopr.entity.tb_log_opr_customer;

import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年9月5日 下午4:20:14
 * @version :
 * 
 */
public interface LogOprCustomerDao {

	public List<tb_log_opr_customer> getLogs(Map map);
	public long getCount(Map map);
	public void addLog(tb_log_opr_customer log);
	public tb_log_opr_customer infoLog(String id);

}
