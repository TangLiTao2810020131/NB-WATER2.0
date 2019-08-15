package com.ets.business.init.daelaytime.dao;

import com.ets.business.init.daelaytime.entity.nb_delay_time_delivery;


/**
 * @author wuhao
 *
 */
public interface DeliveryDao {

	public void update(nb_delay_time_delivery entity);
	public void add(nb_delay_time_delivery entity);
	public nb_delay_time_delivery info(String customerId);
}
