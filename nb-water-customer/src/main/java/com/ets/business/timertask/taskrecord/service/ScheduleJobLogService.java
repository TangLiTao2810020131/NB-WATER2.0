package com.ets.business.timertask.taskrecord.service;

import com.ets.business.timertask.taskrecord.entity.nb_schedule_job_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScheduleJobLogService {
	
	@Resource
    com.ets.business.timertask.taskrecord.dao.ScheduleJobLogDao ScheduleJobLogDao;

	public List<nb_schedule_job_log> getScheduleJobLog(Map<String, Object> map) {
		try {
			return ScheduleJobLogDao.selectScheduleJobLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public nb_schedule_job_log infoJobLog(Map<String, Object> map) {
		try {
			return ScheduleJobLogDao.infoJobLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 根据条件欻性能接入类型字典表总数
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		try {
			return ScheduleJobLogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 操作接入类型字段数据
	 * @param ScheduleJobLog
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

	/**
	 * 根据ID查询接入类型数据字典信息
	 * @param id
	 * @return
	 */
	public nb_schedule_job_log infoScheduleJobLog(String id) {
		try {
			return ScheduleJobLogDao.infoScheduleJobLog(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 根据ID删除接入类型数据字典信息
	 * @param id
	 */
	public void deleteScheduleJobLog(String[] id) {
		try {
			ScheduleJobLogDao.deleteScheduleJobLog(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	public List<nb_schedule_job_log> getScheduleJobLogAll() {
		try {
			return ScheduleJobLogDao.selectScheduleJobLogAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
