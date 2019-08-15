package com.ets.system.sysdaelaytime.dao;

import com.ets.system.sysdaelaytime.entity.sys_delay_time;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:37:13
 * @version :
 * 
 */
public interface SysDelayTimeDao {

	public sys_delay_time selectSysDaelayTime();

	public void update(sys_delay_time sysDelayTime);
	
}
