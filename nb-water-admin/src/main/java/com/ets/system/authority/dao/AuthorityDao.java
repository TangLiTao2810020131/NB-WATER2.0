package com.ets.system.authority.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.authority.entity.tb_authority;

/**
 * @author: 姚轶文
 * @date:2018年8月28日 下午1:45:26
 * @version :
 * 
 */
public interface AuthorityDao {

	public List<tb_authority> getAuthoritys(Map map); 
	public void addAuthority(tb_authority entity);
	public void deleteAuthority(String id[]);
	public void updateAuthority(tb_authority entity);
	public tb_authority infoAuthority(String id);
	public long getCount();
	public List<tb_authority> getAllAuthority();
	//查看权限名的个数
	public long findAuthority(String authorityname);


}
