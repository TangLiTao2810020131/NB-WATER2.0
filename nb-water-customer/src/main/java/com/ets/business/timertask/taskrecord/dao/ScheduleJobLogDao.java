package com.ets.business.timertask.taskrecord.dao;

import com.ets.business.timertask.taskrecord.entity.nb_schedule_job_log;

import java.util.List;
import java.util.Map;

/**
 * @author WH
 *
 */
public interface ScheduleJobLogDao {

	List<nb_schedule_job_log> selectScheduleJobLog(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	void updateScheduleJobLog(nb_schedule_job_log entity);

	void insertScheduleJobLog(nb_schedule_job_log entity);

	nb_schedule_job_log infoScheduleJobLog(String id);

	void deleteScheduleJobLog(String[] id);

	List<nb_schedule_job_log> infoScheduleJobLogList(String[] id);

	List<nb_schedule_job_log> selectScheduleJobLogAll();

	nb_schedule_job_log infoJobLog(Map<String, Object> map);

}
