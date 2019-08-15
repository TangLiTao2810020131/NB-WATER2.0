package com.ets.business.meterreadlog.service;

import com.ets.business.meterreadlog.dao.MeterreadlogDao;
import com.ets.business.meterreadlog.entity.nb_meterread_log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 * @ClassName:     MeterreadlogService.java 
 * @Description:   抄表度数记录日志
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午6:46:02
 */
@Service
@Transactional
public class MeterreadlogService {

	@Resource
    MeterreadlogDao meterreadlogDao;
	
	/**
	 * 
	* @Title: queryLogByEidTime 
	* @Description: 查询某一天最后一条抄表记录
	* @param: @param map
	* @return: nb_meterread_log    
	* @Date: 2019年7月24日 下午8:07:03  
	 */
	public nb_meterread_log queryLogByEidTime(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectLogByEidTime(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: queryBalanceEndTimeMeterreadLog 
	* @Description: 查询需要结算的月份最后一次的读数
	* @param: @param map
	* @param: @return    
	* @return: nb_meterread_log    
	* @Date: 2019年7月24日 下午8:46:56  
	 */
	public nb_meterread_log queryBalanceEndTimeMeterreadLog(Map<String, Object> map) {
		try {
			return meterreadlogDao.selectBalanceEndTimeMeterreadLog(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
