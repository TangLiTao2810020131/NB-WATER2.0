package com.ets.business.init.price.service;

import com.ets.business.init.price.dao.PriceDicDao;
import com.ets.business.init.price.entity.nb_price_dic;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月7日 下午3:53:41
 * @version :
 * 
 */
@Service
@Transactional
public class PriceDicService {

	@Resource
	PriceDicDao priceDao;
	
	public List<nb_price_dic> getPrices(Map map)
	{
		return priceDao.getPrices(map);
	}
	
	public long getCount(String customerId)
	{
		return priceDao.getCount(customerId);
	}
	
	public void insetPrice(nb_price_dic entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			priceDao.updatePrice(entity);
		}
		else
		{
			entity.setId(UUIDUtils.getUUID());
			priceDao.addPrice(entity);
		}
	}
	
	public nb_price_dic infoPrice(String id)
	{
		return priceDao.infoPrice(id);
	}
	
	public void deletePrice(String id[])
	{
		priceDao.deletePrice(id);
	}
	
	public List<nb_price_dic> getListPrices(String customerId)
	{
		try {
			return priceDao.selectListPrices(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//检查类型名称的唯一性
	public int isCkeckType(Map map){
        int ckeckType = priceDao.isCkeckType(map);
        return ckeckType;
	}
	
}
