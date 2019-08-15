package com.ets.business.statistic.service;

import com.ets.business.statistic.dao.WaterStatisticDao;
import com.ets.business.statistic.entity.nb_water_statistics;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 
 * @ClassName:     WaterStatisticService.java 
 * @Description:   水表统计操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午6:33:56
 */
@Service
@Transactional
public class WaterStatisticService {
	
	@Resource
    WaterStatisticDao waterStatisticDao;

	/**
	 * 
	* @Title: addWaterStatistic 
	* @Description: 添加统计记录
	* @param: @param statistics   水表统计对象 
	* @return: void    
	* @Date: 2019年7月24日 下午6:36:07  
	 */
	public void addWaterStatistic(nb_water_statistics statistics){
		try {
			statistics.setId(UUIDUtils.getUUID());
			statistics.setCtime(DateTimeUtils.getnowdate());
			waterStatisticDao.inserWaterStatistic(statistics);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
