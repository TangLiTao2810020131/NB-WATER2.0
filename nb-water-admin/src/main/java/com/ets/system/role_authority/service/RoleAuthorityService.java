package com.ets.system.role_authority.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.UUIDUtils;
import com.ets.system.role_authority.dao.RoleAuthorityDao;
import com.ets.system.role_authority.entity.tb_role_authority;

/**
 * @author: 姚轶文
 * @date:2018年9月3日 上午10:37:22
 * @version :
 * 
 */
@Service
@Transactional
public class RoleAuthorityService {

	@Resource
	RoleAuthorityDao raDao;
	
	public List<tb_role_authority> getAuthorityByRoleId(String id)
	{
		return raDao.getAuthorityByRoleId(id); 
	}
	
	public void save(tb_role_authority entity)
	{
		entity.setId(UUIDUtils.getUUID());
		raDao.save(entity);
	}
	
	public void deleteAythorityByRoleId(String id)
	{
		raDao.deleteAythorityByRoleId(id);
	}
	
	public List<String> getAuthorityNameByRoleId(String roleId)
	{
		return raDao.getAuthorityNameByRoleId(roleId);
	}
}
