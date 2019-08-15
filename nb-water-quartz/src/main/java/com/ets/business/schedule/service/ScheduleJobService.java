package com.ets.business.schedule.service;


import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.ets.business.schedule.dao.ScheduleJobDao;
import com.ets.business.schedule.entity.nb_schedule_job;
import com.ets.common.DateTimeUtils;
import com.ets.common.ObjectCode;
import com.ets.quartz.QuartzJobFactory;
import com.ets.quartz.QuartzJobFactoryDisallowConcurrentExecution;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @ClassName:     ScheduleJobService.java 
 * @Description:   定时任务操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午9:08:38
 */
@Service
public class ScheduleJobService {

	public final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ScheduleJobDao jobDao;

	/**
	 * 
	* @Title: getAllTask 
	* @Description: 从数据库中取 区别于getAllJob
	* @param: @param map
	* @param: @return    
	* @return: List<nb_schedule_job>    
	* @Date: 2019年7月24日 下午9:12:43  
	 */
	public List<nb_schedule_job> getAllTask(Map<String, Object> map) {
		return jobDao.getAllMap(map);
	}


	/**
	 * 
	* @Title: getTaskById 
	* @Description:根据任务id从数据库中查询定时任务
	* @param: @param jobId
	* @param: @return    
	* @return: nb_schedule_job    
	* @Date: 2019年7月24日 下午9:12:18  
	 */
	public nb_schedule_job getTaskById(String jobId) {
		return jobDao.selectByPrimaryKey(jobId);
	}

	
	/**
	 * 
    * @Title: changeJob
    * @Description: 启动或停驶定时任务
    * @param: @param jobId 任务ID
    * @param: @param cmd 任务类型（start：启动，stop：停止）
	* @return: boolean    
	* @Date: 2019年7月24日 下午5:45:03  
	 */
	public boolean changeJob(String jobId, String cmd) {
		nb_schedule_job job = getTaskById(jobId);
		if (job == null) {
			return false;
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
		int fig = jobDao.updateByPrimaryKeySelective(job);
		if(fig == 1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	* @Title: updateCron 
	* @Description: 更改任务 cron表达式
	* @param: @param job
	* @param: @return    
	* @return: nb_schedule_job    
	* @Date: 2019年7月24日 下午9:11:59  
	 */
	public nb_schedule_job updateCron(nb_schedule_job job) {
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
			jobDao.updateByPrimaryKeySelective(jobdb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return job;

	}

	/**
	 * 
	* @Title: addJob 
	* @Description: 添加或更新定时任务
	* @param: @param job    
	* @return: void    
	* @Date: 2019年7月24日 下午9:11:36  
	* @throws
	 */
	public void addJob(nb_schedule_job job) {
		if (job == null || !ObjectCode.TASK_STATUS_RUNNING.equals(job.getJobstatus())) {
			return;
		}

		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			log.debug(scheduler + ".......................................................................................add");
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobname(), job.getJobgroup());

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 不存在，创建一个
			if (null == trigger) {
				Class clazz = ObjectCode.TASK_CONCURRENT_IS.equals(job.getIsconcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

				JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobname(), job.getJobgroup()).build();

				jobDetail.getJobDataMap().put("nb_schedule_job", job);

				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronexpression());

				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobname(), job.getJobgroup()).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();

				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronexpression());

				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();

				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 
	* @Title: init 
	* @Description: 初始化定时任务
	* @param: @throws Exception    
	* @return: void    
	* @Date: 2019年7月24日 下午9:11:08  
	 */
	@PostConstruct
	public void init() throws Exception {

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		
		// 这里获取任务信息数据
		List<nb_schedule_job> jobList = jobDao.getAll();

		for (nb_schedule_job job : jobList) {
			addJob(job);
		}
	}

	/**
	 * 
	* @Title: getAllJob 
	* @Description: 获取所有计划中的任务列表
	* @param: @return    
	* @return: List<nb_schedule_job>    
	* @Date: 2019年7月24日 下午9:10:56  
	 */
	public List<nb_schedule_job> getAllJob() {
		List<nb_schedule_job> jobList = new ArrayList<nb_schedule_job>();
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					nb_schedule_job job = new nb_schedule_job();
					job.setJobname(jobKey.getName());
					job.setJobgroup(jobKey.getGroup());
					job.setDescription("触发器:" + trigger.getKey());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					job.setJobstatus(triggerState.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						job.setCronexpression(cronExpression);
					}
					jobList.add(job);
				}
			}
		} catch (Exception e) {
		}
		return jobList;
	}

	/**
	 * 
	* @Title: getRunningJob 
	* @Description: 查询所有正在运行的定时任务
	* @param: @return    
	* @return: List<nb_schedule_job>    
	* @Date: 2019年7月24日 下午9:10:38  
	 */
	public List<nb_schedule_job> getRunningJob() {
		List<nb_schedule_job> jobList = null;
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			jobList = new ArrayList<nb_schedule_job>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				nb_schedule_job job = new nb_schedule_job();
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				job.setJobname(jobKey.getName());
				job.setJobgroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobstatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronexpression(cronExpression);
				}
				jobList.add(job);
			}
		} catch (Exception e) {
		}
		return jobList;
	}

	/**
	 * 
	* @Title: pauseJob 
	* @Description: 暂停定时任务
	* @param: @param nb_schedule_job    
	* @return: void    
	* @Date: 2019年7月24日 下午9:10:15  
	 */
	public void pauseJob(nb_schedule_job nb_schedule_job) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(nb_schedule_job.getJobname(), nb_schedule_job.getJobgroup());
		try {
			scheduler.pauseJob(jobKey);
		} catch (Exception e) {
		}
	}

	/**
	 * 恢复一个job
	 * 
	 * @param nb_schedule_job
	 * @throws SchedulerException
	 */
	public void resumeJob(nb_schedule_job nb_schedule_job) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(nb_schedule_job.getJobname(), nb_schedule_job.getJobgroup());
		try {
			scheduler.resumeJob(jobKey);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	* @Title: deleteJob 
	* @Description: 删除定时任务
	* @param: @param nb_schedule_job    
	* @return: void    
	* @Date: 2019年7月24日 下午9:09:57  
	 */
	public void deleteJob(nb_schedule_job nb_schedule_job) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(nb_schedule_job.getJobname(), nb_schedule_job.getJobgroup());
		try {
			scheduler.deleteJob(jobKey);
		} catch (Exception e) {
		}

	}

	/**
	 * 
	* @Title: runAJobNow 
	* @Description: 立即执行定时任务
	* @param: @param nb_schedule_job    
	* @return: void    
	* @Date: 2019年7月24日 下午9:09:27  
	 */
	public void runAJobNow(nb_schedule_job nb_schedule_job) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(nb_schedule_job.getJobname(), nb_schedule_job.getJobgroup());
		try {
			scheduler.triggerJob(jobKey);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	* @Title: updateJobCron 
	* @Description: 更新定时任务时间表达式
	* @param: @param nb_schedule_job    
	* @return: void    
	* @Date: 2019年7月24日 下午9:09:14  
	 */
	public void updateJobCron(nb_schedule_job nb_schedule_job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			TriggerKey triggerKey = TriggerKey.triggerKey(nb_schedule_job.getJobname(), nb_schedule_job.getJobgroup());

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(nb_schedule_job.getCronexpression());

			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
		}
	}



}
