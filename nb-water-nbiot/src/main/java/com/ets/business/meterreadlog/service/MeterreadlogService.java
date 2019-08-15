package com.ets.business.meterreadlog.service;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.meterreadlog.dao.MeterreadlogDao;
import com.ets.business.meterreadlog.entity.nb_meterread_log;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

/**
 * 
 * @ClassName:     MeterreadlogService.java 
 * @Description:   最新抄表记录日志业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:41:18
 */
@Service
@Transactional
public class MeterreadlogService {

	@Resource
    MeterreadlogDao meterreadlogDao;

	/**
	 * 
	* @Title: insetMeterreadlog 
	* @Description: 新增最新抄表日志记录
	* @param: @param entity 最新抄表日志对象 
	* @return: void    
	* @Date: 2019年7月25日 下午7:40:44  
	* @throws
	 */
	public void insetMeterreadlog(nb_meterread_log entity)
	{
		entity.setId(UUIDUtils.getUUID());
		entity.setCtime(DateTimeUtils.getnowdate());
		meterreadlogDao.insertMeterreadlog(entity);
	}
	
	
	/**
	 * 
	* @Title: AddMeterReadLog 
	* @Description: 更新水表最新读书并添加自动抄表记录日志
	* @param: @param deviceId 设备ID
	* @param: @param readnum 表读数
	* @param: @param equipmentDB 设备详情  
	* @return: void    
	* @Date: 2019年7月25日 下午5:37:28  
	 */
	public void AddMeterReadLog(String deviceId, String readnum, nb_watermeter_equipment equipmentDB){
		nb_meterread_log mlog = new nb_meterread_log();
		mlog.setWatermeterid(equipmentDB.getId());
		mlog.setValue(readnum);
		mlog.setType("1");
		mlog.setOptionuser("无");
		mlog.setOptiontime(DateTimeUtils.getnowdate());
		mlog.setMessgae("无");
		mlog.setIssuccess("1");
		mlog.setCustomercode(equipmentDB.getCustomercode());
		insetMeterreadlog(mlog);
	}

}
