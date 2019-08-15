package com.ets.business.init.price.dao;

import com.ets.business.init.price.entity.nb_price_dic;

import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月7日 下午3:31:57
 * @version :
 * 
 */
public interface PriceDicDao {

	public List<nb_price_dic> getPrices(Map map);
	public long getCount(String customerId);
	public void addPrice(nb_price_dic entity);
	public void deletePrice(String id[]);
	public void updatePrice(nb_price_dic entity);
	public nb_price_dic infoPrice(String id);
	public List<nb_price_dic> selectListPrices(String customerId);
	//检查类型的名称
	public int isCkeckType(Map map);
}
