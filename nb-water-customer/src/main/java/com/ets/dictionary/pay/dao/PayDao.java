package com.ets.dictionary.pay.dao;

import com.ets.dictionary.pay.entity.nb_pay_dict;

import java.util.List;
import java.util.Map;

/**
 * 付费模式字典表链接数据库操作类
 * @author WH
 *
 */
public interface PayDao {

	/**
	 * 根据条件查询付费模式字典表
	 * @param map
	 * @return
	 */
	List<nb_pay_dict> selectPay(Map<String, Object> map);

	/**
	 * 查询付费模式字典表总数
	 * @return
	 */
	long selectCount();

	/**
	 * 更新付费模式字典表
	 * @param access
	 */
	void updatePay(nb_pay_dict pay);

	/**
	 * 添加付费模式字典表
	 * @param access
	 */
	void insertPay(nb_pay_dict pay);

	/**
	 * 根据ID查询付费模式字典表信息
	 * @param id
	 * @return
	 */
	nb_pay_dict infoPay(String id);

	/**
	 * 根据ID删除付费模式字典表信息
	 * @param id
	 */
	void deletePay(String[] id);

	/**
	 * 根据ID删除付费模式字典表信息
	 * @param id
	 */
	List<nb_pay_dict> infoPayList(String[] id);

	List<nb_pay_dict> selectPayAll();
	//查询支付方式个数根据支付方式的类型名
	long fingPayDictype(String paymode);



}
