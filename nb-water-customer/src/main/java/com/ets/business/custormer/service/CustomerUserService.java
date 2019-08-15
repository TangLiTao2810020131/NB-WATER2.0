package com.ets.business.custormer.service;

import com.ets.business.custormer.dao.CustomerUserDao;
import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.common.UUIDUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月6日 上午9:46:55
 * @version :
 * 
 */
@Service
@Transactional
public class CustomerUserService {

	@Resource
    CustomerUserDao customerUserDao;
	
	public List<nb_customer_user> getCustomerUsers(Map map)
	{
		return customerUserDao.getCustomerUsers(map);
	}
	
	public void insetCustomerUser(nb_customer_user entity)
	{
		if(entity.getId()!=null && !entity.getId().equals(""))
		{
			//编辑更新 不能更新用户的账号和密码
			customerUserDao.updateCustomerUser(entity);
		}
		else
		{
			//新增对密码进行md5加密
			entity.setId(UUIDUtils.getUUID());
			//MD5对密码加密
			String md5pass = new SimpleHash("MD5", entity.getPassword(), "1024").toHex();
			entity.setPassword(md5pass);
			entity.setLoginnum("0");
			customerUserDao.addCustomerUser(entity);
		}
	}
	
	public nb_customer_user infoCustomerUser(String id)
	{
		return customerUserDao.infoCustomerUser(id);
	}
	
	public void deleteCustomerUser(String id[])
	{
		customerUserDao.deleteCustomerUser(id);
	}
	
	
	public long getCount(Map map)
	{
		return customerUserDao.getCount(map);
	}
	
	public List<Map> login(Map map)
	{
		return customerUserDao.login(map);
	}
	
	public List<nb_customer_user> getCustomerUsersType(Map<?,?> map)
	{
		return customerUserDao.selectCustomerUsersType(map);
	}

	public int isCheckCustomerUser(Map map){
		int i = customerUserDao.isCheckCustomerUser(map);
		return i;
	}
	
	public void editCustomerUser(nb_customer_user entity)
	{
		customerUserDao.editCustomerUser(entity);
	}
	
	public void editCustomerUserPass(nb_customer_user entity)
	{
		customerUserDao.updateCustomerUserPass(entity);
	}

	public void restPassword(String[] id){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id",id);
		String password=new SimpleHash("MD5", "123456", "1024").toHex();
		map.put("password",password);
		customerUserDao.restPassword(map);
	}

}
