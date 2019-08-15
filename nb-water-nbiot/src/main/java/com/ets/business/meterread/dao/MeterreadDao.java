package com.ets.business.meterread.dao;

import java.util.Map;
import com.ets.business.meterread.entity.nb_meterread;
/**
 * 
 * @ClassName:     MeterreadDao.java 
 * @Description:   最新抄表记录数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:37:08
 */
public interface MeterreadDao {

	/**
	 * 
	* @Title: insertMeterread 
	* @Description: 新增抄表记录
	* @param: @param entity  抄表记录对象
	* @return: void    
	* @Date: 2019年7月25日 下午7:35:44  
	* @throws
	 */
	public void insertMeterread(nb_meterread entity);

	/**
	 * 
	* @Title: infoMeterReadByWaterID 
	* @Description: 根据水表ID查询抄表记录
	* @param: @param map（id：水表ID）
	* @param: @return    
	* @return: nb_meterread    
	* @Date: 2019年7月25日 下午7:35:16  
	* @throws
	 */
	public nb_meterread infoMeterReadByWaterID(Map<String, Object> map);

	/**
	 * 
	* @Title: queryMeterRead 
	* @Description: 根据客户ID和水表ID查询该水表最新抄表数据
	* @param: @param map（customercode：客户Id,watermeterid:水表ID）
	* @return: nb_meterread    
	* @Date: 2019年7月25日 下午7:36:38  
	* @throws
	 */
	public nb_meterread queryMeterRead(Map<String, Object> map);
	
}
