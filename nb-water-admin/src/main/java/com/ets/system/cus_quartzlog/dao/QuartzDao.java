package com.ets.system.cus_quartzlog.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.cus_quartzlog.entity.qrtz_triggers;

public interface QuartzDao {

	List<qrtz_triggers> selectQrtzTriggers(Map<String, Object> map);

	long selectCount();

}
