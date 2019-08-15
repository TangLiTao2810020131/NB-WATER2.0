package com.ets.business.init.condition.dao;

import com.ets.business.init.condition.entity.nb_overdue_fine_dic;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:37:50
 * @version :
 * 
 */
public interface OverdueFineDicDao {

	public void update(nb_overdue_fine_dic entity);
	public void add(nb_overdue_fine_dic entity);
	public nb_overdue_fine_dic info(String customerId);
}
