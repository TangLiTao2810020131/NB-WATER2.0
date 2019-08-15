package com.ets.system.cus_readlog.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.cus_readlog.entity.nb_read_log;

public interface ReadLogDao {


	void insertReadLog(nb_read_log entity);

	List<nb_read_log> selectReadLog(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	nb_read_log info(Map<String, Object> map);


}
