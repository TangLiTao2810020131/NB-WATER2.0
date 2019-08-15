package com.ets.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.ets.task.SimpleService;

/**
 * 
 * @ClassName:     QuartzJob.java 
 * @Description:   定时任务
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 上午8:58:50
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution// 涓嶅厑璁稿苟鍙戞墽琛�
public class QuartzJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {

        SimpleService simpleService = getApplicationContext(jobexecutioncontext).getBean("simpleService", SimpleService.class);
        simpleService.testMethod1();
        simpleService.testMethod2();

    }

    private ApplicationContext getApplicationContext(final JobExecutionContext jobexecutioncontext) {
        try {
            return (ApplicationContext) jobexecutioncontext.getScheduler().getContext().get("applicationContextKey");
        } catch (SchedulerException e) {
            logger.error("jobexecutioncontext.getScheduler().getContext() error!", e);
            throw new RuntimeException(e);
        }
    }}
