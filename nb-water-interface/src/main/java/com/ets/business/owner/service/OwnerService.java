package com.ets.business.owner.service;

import com.ets.business.owner.dao.OwnerDao;
import com.ets.business.owner.entity.nb_owner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:     OwnerService.java 
 * @Description:   业主业务操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午12:00:15
 */
@Service
@Transactional
public class OwnerService {
	
	@Resource
	OwnerDao ownerDao;

	/**
	 * 
	* @Title: queryOwnerCusId 
	* @Description: 根据客户id和户号查询业主信息,若户号为空则查询全部业主信息
	* @param: @param map
	* @return: List<nb_owner>    
	* @Date: 2019年7月25日 下午12:00:33  
	 */
	public List<nb_owner> queryOwnerCusId(Map<String, String> map) {
		try {
			return ownerDao.queryOwnerCusId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
