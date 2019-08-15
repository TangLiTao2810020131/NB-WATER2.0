package com.ets.business.custormer.dao;

import com.ets.business.custormer.entity.nb_customer_user;

import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月6日 上午9:41:40
 * @version :
 * 
 */
public interface CustomerUserDao {

	public List<nb_customer_user> getCustomerUsers(Map map); 
	public void addCustomerUser(nb_customer_user entity);
	public void deleteCustomerUser(String id[]);
	public void updateCustomerUser(nb_customer_user entity);
	public nb_customer_user infoCustomerUser(String id);
	public long getCount(Map map);
	public List<Map> login(Map map);
	public List<nb_customer_user> selectCustomerUsersType(Map<?, ?> map);
	//检查用户账号的唯一性
	public int isCheckCustomerUser(Map map);
	public void editCustomerUser(nb_customer_user entity);
	public void updateCustomerUserPass(nb_customer_user entity);
	//重置密码
	public void restPassword(Map map);


}
