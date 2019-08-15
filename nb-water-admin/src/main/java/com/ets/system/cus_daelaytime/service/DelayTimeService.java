package com.ets.system.cus_daelaytime.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.UUIDUtils;
import com.ets.system.cus_daelaytime.dao.BasicNumDao;
import com.ets.system.cus_daelaytime.dao.DeliveryDao;
import com.ets.system.cus_daelaytime.dao.ValveControlDao;
import com.ets.system.cus_daelaytime.entity.nb_delay_time_basicnum;
import com.ets.system.cus_daelaytime.entity.nb_delay_time_delivery;
import com.ets.system.cus_daelaytime.entity.nb_delay_time_valvecontrol;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:48:24
 * @version :
 * 
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
	
	public void insetBasicNum(nb_delay_time_basicnum entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			basicNumDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			basicNumDao.add(entity);
		}
	}
	
	public nb_delay_time_basicnum infoBasicNum(String customerId)
	{
		return basicNumDao.info(customerId);
	}
	
	
	public void insetDelivery(nb_delay_time_delivery entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			deliveryDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			deliveryDao.add(entity);
		}
	}
	
	public nb_delay_time_delivery infoDelivery(String customerId)
	{
		return deliveryDao.info(customerId);
	}
	
	public void insetValveControl(nb_delay_time_valvecontrol entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			valveControlDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			valveControlDao.add(entity);
		}
	}
	
	public nb_delay_time_valvecontrol infoValveControl(String customerId)
	{
		return valveControlDao.info(customerId);
	}
}
