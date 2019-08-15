package com.ets.business.owner.service;

import com.ets.business.owner.dao.OwnerDao;
import com.ets.business.owner.entity.nb_owner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 * @ClassName:     OwnerService.java 
 * @Description:   业主操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午8:42:57
 */
@Service
@Transactional
public class OwnerService {
	
	@Resource
	OwnerDao ownerDao;

	/**
	 * 
	* @Title: infoOwner 
	* @Description: 查询业主信息
	* @param: @param map
	* @return: nb_owner    
	* @Date: 2019年7月24日 下午8:43:19  
	 */
	public nb_owner infoOwner(Map<String, Object> map) {
		try {
			return ownerDao.infoOwner(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
