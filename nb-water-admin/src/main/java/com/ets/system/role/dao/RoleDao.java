package com.ets.system.role.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.role.entity.tb_role;

/**
 * @author: 姚轶文
 * @date:2018年8月31日 下午4:04:48
 * @version :
 * 
 */
public interface RoleDao {

	public List<tb_role> getRoles(Map map);
	public void addRole(tb_role entity);
	public void deleteRole(String id[]);
	public void updateRole(tb_role entity);
	public tb_role infoRole(String id);
	public long getCount();
	public List<tb_role> getAllRoles();
	//查询资源名称的个数
	public long findRole(String roleName);

}
