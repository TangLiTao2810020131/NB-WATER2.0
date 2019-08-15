package com.ets.business.statistic.dao;


import com.ets.business.statistic.entity.nb_water_statistics;

/**
 * 
 * @ClassName:     WaterStatisticDao.java 
 * @Description:   水表统计数据操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午6:36:43
 */
public interface WaterStatisticDao {
	
	/**
	 * 
	* @Title: inserWaterStatistic 
	* @Description: 添加水表统计记录
	* @param: statistics 水表统计实体对象  
	* @return: void    
	* @Date: 2019年7月24日 下午6:37:05  
	 */
	public void inserWaterStatistic(nb_water_statistics statistics);

}
