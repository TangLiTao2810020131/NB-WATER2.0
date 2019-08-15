package com.ets.system.role_authority.dao;

import java.util.List;

import com.ets.system.role_authority.entity.tb_role_authority;

/**
 * @author: 姚轶文
 * @date:2018年9月3日 上午10:30:22
 * @version :
 * 
 */
public interface RoleAuthorityDao {

	public List<tb_role_authority> getAuthorityByRoleId(String id);
	
	public void save(tb_role_authority entity);
	
	public void deleteAythorityByRoleId(String id);
	
	public List<String> getAuthorityNameByRoleId(String roleId);
}
