package com.ets.business.timertask.task.service;

import com.ets.business.timertask.task.dao.TimerTaskMapper;
import com.ets.business.timertask.task.entity.nb_schedule_job;
import com.ets.common.DateTimeUtils;
import com.ets.common.ObjectCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * @Description: 计划任务管理
 * @author wuhao
 */
@Service
public class TimerTaskService {
	public final Logger log = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private TimerTaskMapper jobMapper;

	/**
	 * 从数据库中取 区别于getAllJob
	 * 
	 * @return
	 */
	public List<nb_schedule_job> getAllTask(Map<String, Object> map) {
		return jobMapper.getAllMap(map);
	}

	/**
	 * 添加到数据库中 区别于addJob
	 */
	public void addTask(nb_schedule_job job) {
		job.setCreatetime(DateTimeUtils.getnowdate());
		jobMapper.insertSelective(job);
	}

	/**
	 * 从数据库中查询job
	 */
	public nb_schedule_job getTaskById(String jobId) {
		return jobMapper.selectByPrimaryKey(jobId);
	}

	/**
	 * 更改任务状态
	 * 
	 * @
	 */
	public void changeStatus(String jobId, String cmd)  {
		nb_schedule_job job = getTaskById(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			deleteJob(job);
			job.setJobstatus(ObjectCode.TASK_STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobstatus(ObjectCode.TASK_STATUS_RUNNING);
			addJob(job);
		}
		job.setCreatetime(DateTimeUtils.getnowdate());
		job.setUpdatetime(DateTimeUtils.getnowdate());
		jobMapper.updateByPrimaryKeySelective(job);
	}

	/**
	 * 更改任务 cron表达式
	 * 
	 * @
	 */
	public nb_schedule_job updateCron(nb_schedule_job job)  {
		nb_schedule_job jobdb = getTaskById(job.getJobid());
		try {
			if (jobdb == null) {
				return null;
			}
			jobdb.setExecutordate(job.getExecutordate());
			jobdb.setCronexpression(job.getCronexpression());
			jobdb.setJobgroup(job.getJobgroup());
			if (ObjectCode.TASK_STATUS_RUNNING.equals(jobdb.getJobstatus())) {
				updateJobCron(jobdb);
			}
			jobMapper.updateByPrimaryKeySelective(jobdb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return job;

	}

	/**
	 * 添加任务
	 * 
	 * @
	 */
	public void addJob(nb_schedule_job job)  {}


	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @
	 */
	public List<nb_schedule_job> getAllJob()  {

		List<nb_schedule_job> jobList = new ArrayList<nb_schedule_job>();

		return jobList;
	}

	/**
	 * 所有正在运行的job
	 * 
	 * @return
	 * @
	 */
	public List<nb_schedule_job> getRunningJob()  {
		List<nb_schedule_job> jobList = new ArrayList<nb_schedule_job>();
		
		return jobList;
	}

	/**
	 * 暂停一个job
	 * 
	 * @param nb_schedule_job
	 * @
	 */
	public void pauseJob(nb_schedule_job nb_schedule_job)  {

	}

	/**
	 * 恢复一个job
	 * 
	 * @param nb_schedule_job
	 * @
	 */
	public void resumeJob(nb_schedule_job nb_schedule_job)  {

	}

	/**
	 * 删除一个job
	 * 
	 * @param nb_schedule_job
	 * @
	 */
	public void deleteJob(nb_schedule_job nb_schedule_job)  {


	}

	/**
	 * 立即执行job
	 * 
	 * @param nb_schedule_job
	 * @
	 */
	public void runAJobNow(nb_schedule_job nb_schedule_job)  {

	}

	/**
	 * 更新job时间表达式
	 * 
	 * @param nb_schedule_job
	 * @
	 */
	public void updateJobCron(nb_schedule_job nb_schedule_job)  {

	}

	public nb_schedule_job infoJob(Map<String, Object> map) {
		try {
			return jobMapper.infoJob(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int isCkeckJobName(Map<String, Object> map) {
		int sum = 0;
		try {
			sum = jobMapper.isCkeckJobName(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}
}
