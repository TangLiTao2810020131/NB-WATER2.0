package com.ets.system.cus_quartzlog.service;

import com.ets.common.Common;
import com.ets.system.cus_quartzlog.dao.QuartzDao;
import com.ets.system.cus_quartzlog.entity.qrtz_triggers;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuartzService {
	
	@Resource
    QuartzDao quartzDao;

	public List<qrtz_triggers> getQrtzTriggers(Map<String, Object> map) {
		
		List<qrtz_triggers> list = quartzDao.selectQrtzTriggers(map);
		
		for (com.ets.system.cus_quartzlog.entity.qrtz_triggers qrtz_triggers : list) {
			
			qrtz_triggers.setNextFireTime(DateFormatUtils.format(Long.valueOf(qrtz_triggers.getNextFireTime()), "yyyy-MM-dd HH:mm:ss"));
			qrtz_triggers.setPrevFireTime(DateFormatUtils.format(Long.valueOf(qrtz_triggers.getPrevFireTime()), "yyyy-MM-dd HH:mm:ss"));
			qrtz_triggers.setStartTime(DateFormatUtils.format(Long.valueOf(qrtz_triggers.getStartTime()), "yyyy-MM-dd HH:mm:ss"));
			qrtz_triggers.setEndTime(DateFormatUtils.format(Long.valueOf(qrtz_triggers.getEndTime()), "yyyy-MM-dd HH:mm:ss"));
			qrtz_triggers.setTriggerState(Common.status.get(qrtz_triggers.getTriggerState()));
			
		}
		
		return list;
	}

	public long getCount() {
		return quartzDao.selectCount();
	}

}
