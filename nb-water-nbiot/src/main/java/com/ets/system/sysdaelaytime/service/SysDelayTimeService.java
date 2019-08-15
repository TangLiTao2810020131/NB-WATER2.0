package com.ets.system.sysdaelaytime.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ets.system.sysdaelaytime.dao.SysDelayTimeDao;
import com.ets.system.sysdaelaytime.entity.sys_delay_time;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午10:48:24
 * @version :
 * 
 */
@Service
@Transactional
public class SysDelayTimeService {

	@Resource
	SysDelayTimeDao sysDelayTimeDao;

	/**
	 * 
	* @Title: getSysDaelayTime 
	* @Description: 查询命令下发延迟时间
	* @return: sys_delay_time    
	* @Date: 2019年7月25日 下午5:38:12  
	 */
	public sys_delay_time getSysDaelayTime(){
		return sysDelayTimeDao.selectSysDaelayTime();
	}


	public void update(sys_delay_time sysDelayTime) {
		sysDelayTimeDao.update(sysDelayTime);
		
	}
}
