package com.ets.system.watermeter.dao;

import java.util.Map;
import com.ets.system.watermeter.entity.nb_watermeter_dict;

/**
 * 
 * @ClassName:     WatermeterDao.java 
 * @Description:   水表类型数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:39:21
 */
public interface WatermeterDao {

	/**
	 * 
	* @Title: selectWMTypeCusId 
	* @Description: 查询水表信息类型
	* @param: @param map(imei:水表唯一号，customerId：客户秘钥)  
	* @return: nb_watermeter_dict    
	* @Date: 2019年7月25日 下午2:39:10  
	 */
	nb_watermeter_dict selectWMTypeCusId(Map<String, String> map);

}
