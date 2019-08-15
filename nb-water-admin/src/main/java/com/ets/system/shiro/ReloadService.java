package com.ets.system.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 由于使用了缓存，在系统启动时会一次读取全部资源，当资源、权限、角色有增删改动作时，必须重启服务才有效果。
 * 
 * 在需要更新权限资源缓存的地方，通过注入此类，调用reloadFilterChains方法，刷新新资源缓存。用户重新登录后生效。
 * @author Windows User
 *
 */
@Service
public class ReloadService{

	@Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;
	@Autowired
    private LoadFilter permissFactory;
	@Autowired
	public ShiroUtility shiroUtility;
	
	
	public void reloadFilterChains() {
		// TODO Auto-generated method stub
		synchronized (permissFactory) {   //强制同步，控制线程安全
			
			AbstractShiroFilter shiroFilter = null;
			try {
				shiroFilter = (AbstractShiroFilter)shiroFilterFactoryBean.getObject();
				PathMatchingFilterChainResolver filterChainResolver =(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
				DefaultFilterChainManager manager =(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
				//清空老的权限控制
			    manager.getFilterChains().clear();
			      
			    shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
			    shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroUtility.getMap());
			}
			catch (Exception e) {
                e.printStackTrace();
            }
			
		}
	}
	
	
	
	
}
