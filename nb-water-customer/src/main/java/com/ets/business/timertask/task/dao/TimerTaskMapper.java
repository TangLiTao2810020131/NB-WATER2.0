package com.ets.business.timertask.task.dao;

import com.ets.business.timertask.task.entity.nb_schedule_job;

import java.util.List;
import java.util.Map;


public interface TimerTaskMapper {
	int deleteByPrimaryKey(String jobId);

	int insert(nb_schedule_job jon);

	int insertSelective(nb_schedule_job jon);

	nb_schedule_job selectByPrimaryKey(String jobId);

	int updateByPrimaryKeySelective(nb_schedule_job jon);

	int updateByPrimaryKey(nb_schedule_job jon);

	List<nb_schedule_job> getAllMap(Map<String, Object> map);
	List<nb_schedule_job> getAll();

	nb_schedule_job infoJob(Map<String, Object> map);

	int isCkeckJobName(Map<String, Object> map);
}