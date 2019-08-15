package com.ets.business.balance.service;

import com.ets.business.balance.dao.BalanceDao;
import com.ets.business.balance.entity.BalanceModel;
import com.ets.business.balance.entity.nb_balance_dict;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 接入类型字典表操作数据库类
 * @author Administrator
 *
 */
@Service
@Transactional
public class BalanceService {
	
	@Resource
	BalanceDao balanceDao;

	/**
	 * 根据条件欻查询结算信息
	 * @param map
	 * @return
	 */
	public List<nb_balance_dict> getBalance(Map<String, Object> map) {
		try {
			return balanceDao.selectBalance(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据条件欻查询结算信息
	 * @param map
	 * @return
	 */
	public List<nb_balance_dict> getBalanceExport(Map<String, Object> map) {
		try {
			return balanceDao.selectBalanceExport(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 根据条件欻性能接入类型字典表总数
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		try {
			return balanceDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	public void opentionBalance(nb_balance_dict balance) {
		try {
			balance.setId(UUIDUtils.getUUID());
			balance.setCtime(DateTimeUtils.getnowdate());
			balanceDao.insertBalance(balance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int checkBalanceDate(Map<String, Object> map) {
		int num = 0;
		try {
			num = balanceDao.checkBalanceDate(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}


	public BalanceModel infoBalance(Map<String, Object> map) {
		try {
			return balanceDao.infoBalance(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}



	public nb_balance_dict queryLastBalance(Map<String, Object> map) {
		
		return balanceDao.selectLastBalance(map);
	}










}
