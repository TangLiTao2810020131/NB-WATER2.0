package com.ets.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ets.business.schedule.entity.nb_schedule_job;

/**
 * 计划任务执行处 无状态
 * @author wuhao
 *
 */
public class QuartzJobFactory implements Job {
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		nb_schedule_job scheduleJob = (nb_schedule_job) context.getMergedJobDataMap().get("nb_schedule_job");
		QuartzJobTaskUtils.invokMethod(scheduleJob);
		System.out.println("QuartzJobFactory");
	}
}