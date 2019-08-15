package com.ets.business.meterread.service;

import com.ets.business.meterread.dao.MeterreadDao;
import com.ets.business.meterread.entity.nb_meterread;
import com.ets.business.statistic.entity.nb_water_statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     MeterreadService.java 
 * @Description:   最新表读数操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午6:30:43
 */
@Service
@Transactional
public class MeterreadService {

	@Resource
    MeterreadDao meterreadDao;
	
	/**
	 * 
	 * @Title: queryAllReadCount 
	 * @Description: 查询最新抄表数据的所有水表个数
	 * @return: long    
	 * @Date: 2019年7月24日 下午6:31:10  
	*/
	public long queryAllReadCount(){
		return meterreadDao.queryAllReadCount();
	}
	
	/**
	 * 
	* @Title: queryAllRead 
	* @Description: 分页查询最新表读数相关的所以水表设备
	* @param: @param map
	* @return: List<nb_water_statistics>    
	* @Date: 2019年7月24日 下午6:40:11  
	 */
	public List<nb_water_statistics> queryAllRead(Map<String, Object> map){
		return meterreadDao.queryAllRead(map);
	}
	
	/**
	 * 
	* @Title: queryAllReadCountOnLine 
	* @Description: 查询最新抄表数据的最新上报数据的所有水表个数
	* @return: long    
	* @Date: 2019年7月24日 下午8:14:05  
	 */
	public long queryAllReadCountOnLine(){
		return meterreadDao.queryAllReadCountOnLine();
	}
	
	/**
	 * 
	* @Title: queryAllReadOnLine 
	* @Description: 分页查询最新抄表数据的最新上报数据的所有水表
	* @param: @param map
	* @return: List<nb_meterread>    
	* @Date: 2019年7月24日 下午8:19:52  
	 */
	public List<nb_meterread> queryAllReadOnLine(Map<String, Object> map) {
		try {
			return meterreadDao.queryAllReadOnLine(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
