package com.ets.business.meterreadlog.service;

import com.ets.business.meterreadlog.dao.MeterreadlogDao;
import com.ets.business.meterreadlog.entity.nb_meterread_log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     MeterreadlogService.java 
 * @Description:   最新抄表记录日志业务操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:30:41
 */
@Service
@Transactional
public class MeterreadlogService {

	@Resource
    MeterreadlogDao meterreadlogDao;
	
	/**
	 * 
	* @Title: queryWMLatestNumCusId 
	* @Description: 根据客户id查询水表的历史抄表记录
	* @param: @param map(imei:水表唯一号，customerId：客户ID，startDate：查询开始时间，endDate：查询结束时间)
	* @param: @return    
	* @return: List<nb_meterread_log>    
	* @Date: 2019年7月25日 下午2:31:07  
	 */
	public List<nb_meterread_log> queryWMLatestNumCusId(Map<String, String> map) {
		try {
			return meterreadlogDao.selectWMLatestNumCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
