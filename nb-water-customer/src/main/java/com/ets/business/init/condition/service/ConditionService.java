package com.ets.business.init.condition.service;

import com.ets.business.init.condition.dao.AccountOpenerDicDao;
import com.ets.business.init.condition.dao.OverdueFineDicDao;
import com.ets.business.init.condition.dao.PriceMaxDicDao;
import com.ets.business.init.condition.entity.nb_account_opener_dic;
import com.ets.business.init.condition.entity.nb_overdue_fine_dic;
import com.ets.business.init.condition.entity.nb_price_max_dic;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:48:24
 * @version :
 * 
 */
@Service
@Transactional
public class ConditionService {

	@Resource
	AccountOpenerDicDao accountOpenerDicDao;
	@Resource
	OverdueFineDicDao overdueFineDicDao;
	@Resource
	PriceMaxDicDao priceMaxDicDao;
	
	public void insetPriceMax(nb_price_max_dic entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			priceMaxDicDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			priceMaxDicDao.add(entity);
		}
	}
	
	public nb_price_max_dic infoPriceMax(String customerId)
	{
		return priceMaxDicDao.info(customerId);
	}
	
	
	public void insetOverdueFine(nb_overdue_fine_dic entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			overdueFineDicDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			overdueFineDicDao.add(entity);
		}
	}
	
	public nb_overdue_fine_dic infoOverdueFine(String customerId)
	{
		return overdueFineDicDao.info(customerId);
	}
	
	public void insetAccountOpener(nb_account_opener_dic entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			accountOpenerDicDao.update(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			accountOpenerDicDao.add(entity);
		}
	}
	
	public nb_account_opener_dic infoAccountOpener(String customerId)
	{
		return accountOpenerDicDao.info(customerId);
	}
}
