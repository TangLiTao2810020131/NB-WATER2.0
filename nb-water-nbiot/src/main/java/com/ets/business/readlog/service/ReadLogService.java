package com.ets.business.readlog.service;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.readlog.dao.ReadLogDao;
import com.ets.business.readlog.entity.nb_read_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.nb_iot.model.ReceiveEquipment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     ReadLogService.java 
 * @Description:   上报数据业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:49:59
 */
@Service
@Transactional
public class ReadLogService {
	
	@Resource
	ReadLogDao readLogDao;
	
	/**
	 * 
	* @Title: addReadLog 
	* @Description: 上报数据新增记录
	* @param: @param entity 上报数据对象   
	* @return: void    
	* @Date: 2019年7月25日 下午7:50:42  
	 */
	public void addReadLog(nb_read_log entity){
		try {
			readLogDao.insertReadLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	* @Title: AddReadLog 
	* @Description: 接收到上报数据 存入数据库
	* @param: @param date上报的数据
	* @param: @param equipmentReceive 设备信息
	* @param: @param equipment 设备详情
	* @param: @param ip 地址 
	* @return: void    
	* @Date: 2019年7月25日 下午5:20:03  
	 */
	public void AddReadLog(String date,ReceiveEquipment equipmentReceive,nb_watermeter_equipment equipment,String ip){
		nb_read_log entity = new nb_read_log();
		entity.setId(UUIDUtils.getUUID());
		entity.setCtime(DateTimeUtils.getnowdate());
		entity.setImei(equipment.getWatermetercode());
		entity.setContent(date);
		entity.setDeviceId(equipmentReceive.getDeviceId());
		entity.setCustomercode(equipment.getCustomercode());
		entity.setIpaddress(ip);
		addReadLog(entity);
	}

}
