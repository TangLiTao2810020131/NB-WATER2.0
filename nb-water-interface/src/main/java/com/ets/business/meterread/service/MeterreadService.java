package com.ets.business.meterread.service;

import com.ets.business.meterread.dao.MeterreadDao;
import com.ets.business.meterread.entity.nb_meterread;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     MeterreadService.java 
 * @Description:   最新抄表记录业务操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午2:28:02
 */
@Service
@Transactional
public class MeterreadService {

	@Resource
    MeterreadDao meterreadDao;
	
	/**
	 * 
	* @Title: queryWMHistoryNumCusId 
	* @Description: 根据客户id和户号查询水表最新表读数,若户号为空则查询全部水表最新表读数
	* @param: @param map(imei:水表唯一号，customerId：客户ID)
	* @param: @return    
	* @return: List<nb_meterread>    
	* @Date: 2019年7月25日 下午2:26:08  
	* @throws
	 */
	public List<nb_meterread> queryWMHistoryNumCusId(Map<String,String> map){
		return meterreadDao.selectWMHistoryNumCusId(map);
	}
	
}
