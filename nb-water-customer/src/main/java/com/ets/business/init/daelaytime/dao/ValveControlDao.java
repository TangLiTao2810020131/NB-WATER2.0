package com.ets.business.init.daelaytime.dao;

import com.ets.business.init.daelaytime.entity.nb_delay_time_valvecontrol;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:37:13
 * @version :
 * 
 */
public interface ValveControlDao {

	public void update(nb_delay_time_valvecontrol entity);
	public void add(nb_delay_time_valvecontrol entity);
	public nb_delay_time_valvecontrol info(String customerId);
}
