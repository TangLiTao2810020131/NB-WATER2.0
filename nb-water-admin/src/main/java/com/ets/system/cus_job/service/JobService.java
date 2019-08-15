package com.ets.system.cus_job.service;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ets.common.DateTimeUtils;
import com.ets.system.cus_job.dao.JobDao;
import com.ets.system.cus_job.entity.nb_schedule_job;


/**
 * 
 * @Description: 计划任务管理
 * @author wuhao
 */
@Service
public class JobService {
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobDao jobDao;


	/**
	 * 添加到数据库中 区别于addJob
	 */
	public void addTask(nb_schedule_job job) {
		job.setCreatetime(DateTimeUtils.getnowdate());
		jobDao.insertJob(job);
	}



}
