package com.ets.business.balance.service;

import com.ets.business.balance.dao.BalanceDao;
import com.ets.business.balance.entity.nb_balance;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 
 * @ClassName:     BalanceService.java 
 * @Description:   水表用水量结算实体类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午8:39:56
 */
@Service
@Transactional
public class BalanceService {
	
	@Resource
	BalanceDao balanceDao;

	/**
	 * 
	* @Title: queryLastBalance 
	* @Description: 查询上月用水量
	* @param: @param map
	* @param: @return    
	* @return: nb_balance    
	* @Date: 2019年7月24日 下午8:41:11  
	 */
	public nb_balance queryLastBalance(Map<String, Object> map) {
		
		return balanceDao.selectLastBalance(map);
	}

	/**
	 * 
	* @Title: addBalance 
	* @Description: 添加结算记录
	* @param: @param balance    
	* @return: void    
	* @Date: 2019年7月24日 下午8:50:30  
	 */
	public void addBalance(nb_balance balance) {
		try {
			balance.setId(UUIDUtils.getUUID());
			balance.setCtime(DateTimeUtils.getnowdate());
			balanceDao.insertBalance(balance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}







}
