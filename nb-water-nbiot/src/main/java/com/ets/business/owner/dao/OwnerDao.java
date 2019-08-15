package com.ets.business.owner.dao;

import com.ets.business.owner.entity.OwnerModel;
import java.util.Map;

/**
 * 
 * @ClassName:     OwnerDao.java 
 * @Description:   业主数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:48:02
 */
public interface OwnerDao {

	/**
	 * 
	* @Title: ownerInfo 
	* @Description: 根据customercode和doornumid查询业主信息
	* @param: @param map(customercode:客户ID，doornumid门牌号)
	* @return: OwnerModel    
	* @Date: 2019年7月25日 下午6:15:46  
	 */
	OwnerModel ownerInfo(Map<String, Object> map);

}
