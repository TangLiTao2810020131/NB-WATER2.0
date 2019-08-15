package com.ets.system.shiro;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;



/**
 * 系统启动时，执行此方法，读取数据库tb_resource资源表，将资源名称作为权限名称，url作为被拦截的对象，拥有资源权限的用户可以访问。
 * @author Windows User
 *
 */

public class LoadFilter {	
	
	@Autowired
	public ShiroUtility shiroUtility;
	
	
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = shiroUtility.getMap();
		//System.out.println("加载全部资源访问权限！");
		return map;
	}
	
	

	
}
