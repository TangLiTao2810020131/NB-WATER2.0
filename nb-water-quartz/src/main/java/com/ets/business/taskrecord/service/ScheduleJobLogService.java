package com.ets.business.taskrecord.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.business.taskrecord.dao.ScheduleJobLogDao;
import com.ets.business.taskrecord.entity.nb_schedule_job_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;

/**
 * 
 * @ClassName:     ScheduleJobLogService.java 
 * @Description:   定时任务日志操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午9:08:11
 */
@Service
@Transactional
public class ScheduleJobLogService {
	
	@Resource
    ScheduleJobLogDao ScheduleJobLogDao;


	/**
	 * 
	* @Title: opentionScheduleJobLog 
	* @Description: 操作结算定时任务记录
	* @param: @param entity    
	* @return: void    
	* @Date: 2019年7月24日 下午9:05:18  
	 */
	public void opentionScheduleJobLog(nb_schedule_job_log entity) {
		try {
			if(entity.getId()!=null && !entity.getId().equals(""))
			{
				ScheduleJobLogDao.updateScheduleJobLog(entity);
			}
			else
			{
				entity.setId(UUIDUtils.getUUID());
				entity.setCtime(DateTimeUtils.getnowdate());
				ScheduleJobLogDao.insertScheduleJobLog(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





}
