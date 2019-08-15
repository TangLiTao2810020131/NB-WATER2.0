package com.ets.business.daelaytime.service;

import com.ets.business.daelaytime.dao.BasicNumDao;
import com.ets.business.daelaytime.dao.DeliveryDao;
import com.ets.business.daelaytime.dao.ValveControlDao;
import com.ets.business.daelaytime.entity.nb_delay_time_basicnum;
import com.ets.business.daelaytime.entity.nb_delay_time_delivery;
import com.ets.business.daelaytime.entity.nb_delay_time_valvecontrol;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 
 * @ClassName:     DelayTimeService.java 
 * @Description:   客户端命令下发延迟时间
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:31:40
 */
@Service
@Transactional
public class DelayTimeService {

	@Resource
	BasicNumDao basicNumDao;
	@Resource
    DeliveryDao deliveryDao;
	@Resource
    ValveControlDao valveControlDao;
	
	/**
	 * 
	* @Title: infoBasicNum 
	* @Description: 根据客户ID获取设置表读数命令下发延迟时间
	* @param: @param customerId 客户ID
	* @return: nb_delay_time_basicnum    
	* @Date: 2019年7月25日 下午5:22:49  
	 */
	public nb_delay_time_basicnum infoBasicNum(String customerId)
	{
		return basicNumDao.info(customerId);
	}
	
	/**
	 * 
	* @Title: infoDelivery 
	* @Description: 根据客户ID获取设置上报周期命令下发延迟时间
	* @param: @param customerId 客户ID
	* @return: nb_delay_time_delivery    
	* @Date: 2019年7月25日 下午5:21:38  
	 */
	public nb_delay_time_delivery infoDelivery(String customerId)
	{
		return deliveryDao.info(customerId);
	}
	
	/**
	 * 
	* @Title: infoValveControl 
	* @Description: 根据客户ID获取设置阀控命令下发延迟时间
	* @param: @param customerId 客户ID
	* @return: nb_delay_time_valvecontrol    
	* @Date: 2019年7月25日 下午5:23:17  
	 */
	public nb_delay_time_valvecontrol infoValveControl(String customerId)
	{
		return valveControlDao.info(customerId);
	}
}
