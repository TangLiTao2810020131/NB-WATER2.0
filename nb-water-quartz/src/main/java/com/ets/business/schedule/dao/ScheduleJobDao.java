package com.ets.business.schedule.dao;


import java.util.List;
import java.util.Map;

import com.ets.business.schedule.entity.nb_schedule_job;

/**
 * 
 * @ClassName:     ScheduleJobDao.java 
 * @Description:   定时任务数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午9:13:04
 */
public interface ScheduleJobDao {
	int deleteByPrimaryKey(String jobId);

	int insert(nb_schedule_job jon);

	int insertSelective(nb_schedule_job jon);

	/**
	 * 
	* @Title: selectByPrimaryKey 
	* @Description: 根据任务id从数据库中查询定时任务
	* @param: @param jobId
	* @param: @return    
	* @return: nb_schedule_job    
	* @Date: 2019年7月24日 下午9:13:52  
	 */
	nb_schedule_job selectByPrimaryKey(String jobId);

	/**
	 * 
	* @Title: updateByPrimaryKeySelective 
	* @Description: 更新定时任务
	* @param: @param jon
	* @param: @return    
	* @return: int    
	* @Date: 2019年7月24日 下午9:14:10  
	 */
	int updateByPrimaryKeySelective(nb_schedule_job jon);

	int updateByPrimaryKey(nb_schedule_job jon);

	/**
	 * 
	* @Title: getAllMap 
	* @Description: 从数据库中取 区别于getAllJob
	* @param: @param map
	* @param: @return    
	* @return: List<nb_schedule_job>    
	* @Date: 2019年7月24日 下午9:13:31  
	 */
	List<nb_schedule_job> getAllMap(Map<String, Object> map);
	List<nb_schedule_job> getAll();

	nb_schedule_job infoJob(Map<String, Object> map);

	int isCkeckJobName(Map<String, Object> map);
}