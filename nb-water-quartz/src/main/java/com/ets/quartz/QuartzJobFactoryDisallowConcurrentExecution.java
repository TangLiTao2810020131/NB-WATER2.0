package com.ets.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ets.business.schedule.entity.nb_schedule_job;



/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @author wuhao
 *
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	public final Logger log = LoggerFactory.getLogger(this.getClass());

	public void execute(JobExecutionContext context) throws JobExecutionException {
		nb_schedule_job scheduleJob = (nb_schedule_job) context.getMergedJobDataMap().get("nb_schedule_job");
		QuartzJobTaskUtils.invokMethod(scheduleJob);
		System.out.println("QuartzJobFactoryDisallowConcurrentExecution");
	}
}