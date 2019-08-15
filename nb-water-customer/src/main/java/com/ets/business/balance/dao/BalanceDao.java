package com.ets.business.balance.dao;

import com.ets.business.balance.entity.BalanceModel;
import com.ets.business.balance.entity.nb_balance_dict;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author WH
 *
 */
public interface BalanceDao {

	List<nb_balance_dict> selectBalance(Map<String, Object> map);
	
	List<nb_balance_dict> selectBalanceExport(Map<String, Object> map);
	
	long selectCount(Map<String, Object> map);

	void insertBalance(nb_balance_dict balance);

	int checkBalanceDate(Map<String, Object> map);

	BalanceModel infoBalance(Map<String, Object> map);

	nb_balance_dict selectLastBalance(Map<String, Object> map);

}
