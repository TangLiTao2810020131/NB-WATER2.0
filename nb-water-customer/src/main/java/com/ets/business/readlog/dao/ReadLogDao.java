package com.ets.business.readlog.dao;

import com.ets.business.readlog.entity.nb_read_log;

import java.util.List;
import java.util.Map;

public interface ReadLogDao {


	void insertReadLog(nb_read_log entity);

	List<nb_read_log> selectReadLog(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	nb_read_log info(Map<String, Object> map);


}
