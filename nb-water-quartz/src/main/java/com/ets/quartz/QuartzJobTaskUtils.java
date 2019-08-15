package com.ets.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ets.business.schedule.entity.nb_schedule_job;


public class QuartzJobTaskUtils {
	public final static Logger log = LoggerFactory.getLogger(QuartzJobTaskUtils.class);
	/**
	 * 通过反射调用nb_schedule_job中定义的方法
	 * 
	 * @param nb_schedule_job
	 */
	public static void invokMethod(nb_schedule_job nb_schedule_job) {
		Object object = null;
		Class<?> clazz = null;
		if (StringUtils.isNotBlank(nb_schedule_job.getSpringid())) {
			object = SpringUtils.getBean(nb_schedule_job.getSpringid());
		} else if (StringUtils.isNotBlank(nb_schedule_job.getBeanclass())) {
			try {
				clazz = Class.forName(nb_schedule_job.getBeanclass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (object == null) {
			log.error("任务名称 = [" + nb_schedule_job.getJobname() + "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(nb_schedule_job.getMethodname(),String.class);
		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + nb_schedule_job.getJobname() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (method != null) {
			try {
				
				String customercode = nb_schedule_job.getCustomercode();
				String jobId = nb_schedule_job.getJobid();
				String paras = customercode+"*"+jobId;
				method.invoke(object,paras);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		System.out.println("任务名称 = [" + nb_schedule_job.getJobname() + "]----------执行完毕!");
	}
}
