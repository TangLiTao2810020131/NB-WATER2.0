package com.ets.system.user_role.dao;

import java.util.List;

import com.ets.system.role.entity.tb_role;
import com.ets.system.user_role.entity.tb_user_role;

/**
 * @author: 姚轶文
 * @date:2018年9月3日 下午1:15:41
 * @version :
 * 
 */
public interface UserRoleDao {

	public List<tb_user_role> getRoleByUserId(String id);
	
	public void save(tb_user_role entity);
	
	public void deleteRoleByUserId(String id);
	
	public List<String> getRoleNameByUserId(String userId);
	
	public List<tb_role> getRoles(String userId);
}
