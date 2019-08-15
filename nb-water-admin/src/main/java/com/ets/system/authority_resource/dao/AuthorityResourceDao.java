package com.ets.system.authority_resource.dao;

import java.util.List;

import com.ets.system.authority_resource.entity.tb_authority_resource;
import com.ets.system.resource.entity.tb_resource;

/**
 * @author: 姚轶文
 * @date:2018年8月29日 下午3:16:52
 * @version :
 * 
 */
public interface AuthorityResourceDao {

	public List<tb_authority_resource> getResourceByAuthorityId(String id);
	public void save(tb_authority_resource ar);
	public void deleteResourceByAuthorityId(String authorityId);
	
	public List<tb_resource> getResources(String authorityId);
}
