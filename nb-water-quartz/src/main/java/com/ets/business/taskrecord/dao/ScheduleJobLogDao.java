package com.ets.business.taskrecord.dao;


import com.ets.business.taskrecord.entity.nb_schedule_job_log;

/**
 * 
 * @ClassName:     ScheduleJobLogDao.java 
 * @Description:   定时任务日志数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午9:06:48
 */
public interface ScheduleJobLogDao {


	/**
	 * 
	* @Title: updateScheduleJobLog 
	* @Description: 修改结算定时任务记录
	* @param: @param entity    
	* @return: void    
	* @Date: 2019年7月24日 下午9:06:10  
	 */
	void updateScheduleJobLog(nb_schedule_job_log entity);

	/**
	 * 
	* @Title: insertScheduleJobLog 
	* @Description: 新增结算定时任务记录
	* @param: @param entity    
	* @return: void    
	* @Date: 2019年7月24日 下午9:06:31  
	* @throws
	 */
	void insertScheduleJobLog(nb_schedule_job_log entity);

}
