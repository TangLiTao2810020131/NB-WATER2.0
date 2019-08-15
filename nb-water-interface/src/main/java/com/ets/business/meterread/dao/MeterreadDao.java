package com.ets.business.meterread.dao;

import java.util.List;
import java.util.Map;
import com.ets.business.meterread.entity.nb_meterread;

/**
 * 
 * @ClassName:     MeterreadDao.java 
 * @Description:   最新抄表记录数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:27:33
 */
public interface MeterreadDao {

	/**
	 * 
	* @Title: selectWMHistoryNumCusId 
	* @Description: 根据客户id和户号查询水表最新表读数,若户号为空则查询全部水表最新表读数
	* @param: @param map
	* @param: @return    
	* @return: List<nb_meterread>    
	* @Date: 2019年7月25日 下午2:27:21  
	* @throws
	 */
	public List<nb_meterread> selectWMHistoryNumCusId(Map<String, String> map);
}
