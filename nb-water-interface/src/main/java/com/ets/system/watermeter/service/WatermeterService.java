package com.ets.system.watermeter.service;

import com.ets.system.watermeter.dao.WatermeterDao;
import com.ets.system.watermeter.entity.nb_watermeter_dict;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 * @ClassName:     WatermeterService.java 
 * @Description:   水表类型业务操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:37:48
 */
@Service
@Transactional
public class WatermeterService {
	
	@Resource
    WatermeterDao watermeterDao;
	
	/**
	 * 
	* @Title: queryWMTypeCusId 
	* @Description: 查询水表信息类型
	* @param: @param map(imei:水表唯一号，customerId：客户秘钥)
	* @return: nb_watermeter_dict    
	* @Date: 2019年7月25日 下午2:37:39  
	 */
	public nb_watermeter_dict queryWMTypeCusId(Map<String, String> map) {
		try {
			return watermeterDao.selectWMTypeCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
