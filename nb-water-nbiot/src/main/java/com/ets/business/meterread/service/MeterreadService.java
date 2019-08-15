package com.ets.business.meterread.service;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.meterread.dao.MeterreadDao;
import com.ets.business.meterread.entity.nb_meterread;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName:     MeterreadService.java 
 * @Description:   最新抄表记录业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:37:58
 */
@Service
@Transactional
public class MeterreadService {

	@Resource
    MeterreadDao meterreadDao;
	
	/**
	 * 
	* @Title: insetMeterread 
	* @Description: 新增抄表记录
	* @param: @param entity  抄表记录对象
	* @return: void    
	* @Date: 2019年7月25日 下午7:33:49  
	 */
	public void insetMeterread(nb_meterread entity)
	{
		entity.setId(UUIDUtils.getUUID());
		meterreadDao.insertMeterread(entity);
	}
	
	/**
	 * 
	* @Title: infoMeterreadByWaterID 
	* @Description: 根据水表ID查询抄表记录
	* @param: @param map（id：水表ID）
	* @return: nb_meterread    
	* @Date: 2019年7月25日 下午7:34:12  
	 */
	public nb_meterread infoMeterreadByWaterID(Map<String,Object> map) {
		try {
			return meterreadDao.infoMeterReadByWaterID(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: AddMeterRead 
	* @Description: 更新水表最新读书并添加自动抄表记录
	* @param: @param deviceId 设备ID
	* @param: @param readnum 表读数
	* @param: @param equipmentDB 设备详情
	* @return: void    
	* @Date: 2019年7月25日 下午5:36:30  
	* @throws
	 */
	public void AddMeterRead(String deviceId, String readnum, nb_watermeter_equipment equipmentDB){
		Map<String,Object> map = new HashMap<String, Object>(); 
		map.put("id", equipmentDB.getId());
		nb_meterread m = infoMeterreadByWaterID(map);
		m.setValue(readnum);
		m.setOptionuser("");
		m.setOptiontime(DateTimeUtils.getnowdate());
		m.setType("1");
		insetMeterread(m);
	}
	
	/**
	 * 
	* @Title: queryMeterRead 
	* @Description: 根据客户ID和水表ID查询该水表最新抄表数据
	* @param: @param map（customercode：客户Id,watermeterid:水表ID）
	* @return: nb_meterread    
	* @Date: 2019年7月25日 下午5:33:56  
	 */
	public nb_meterread queryMeterRead(Map<String,Object> map){
		return meterreadDao.queryMeterRead(map);
	}
}
