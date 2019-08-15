package com.ets.config;

import com.ets.quartz.QuartzJob;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import javax.sql.DataSource;

@Configuration
public class QuartzConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("triggerStatistic") Trigger trigger){
        SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        schedulerFactoryBean.setTriggers(trigger);
        Resource resource=new ClassPathResource("quartz.properties");
        schedulerFactoryBean.setConfigLocation(resource);
        return schedulerFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean triggerStatistic(@Qualifier("jobDetailStatistic") JobDetail jobDetail){
        CronTriggerFactoryBean triggerStatistic=new CronTriggerFactoryBean();
        triggerStatistic.setJobDetail(jobDetail);
        triggerStatistic.setCronExpression("0 30 01 * * ?");
        return triggerStatistic;
    }

    @Bean
    public JobDetailFactoryBean jobDetailStatistic(){
        JobDetailFactoryBean jobDetailStatistic=new JobDetailFactoryBean();
        jobDetailStatistic.setJobClass(QuartzJob.class);
        jobDetailStatistic.setDurability(true);
        jobDetailStatistic.setRequestsRecovery(true);
        return jobDetailStatistic;
    }

}