package com.ets.business.init.condition.dao;

import com.ets.business.init.condition.entity.nb_price_max_dic;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:37:13
 * @version :
 * 
 */
public interface PriceMaxDicDao {

	public void update(nb_price_max_dic entity);
	public void add(nb_price_max_dic entity);
	public nb_price_max_dic info(String customerId);
}
