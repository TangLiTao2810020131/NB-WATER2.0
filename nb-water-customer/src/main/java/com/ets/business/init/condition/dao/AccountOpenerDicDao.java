package com.ets.business.init.condition.dao;

import com.ets.business.init.condition.entity.nb_account_opener_dic;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:38:04
 * @version :
 * 
 */
public interface AccountOpenerDicDao {

	public void update(nb_account_opener_dic entity);
	public void add(nb_account_opener_dic entity);
	public nb_account_opener_dic info(String customerId);
}
