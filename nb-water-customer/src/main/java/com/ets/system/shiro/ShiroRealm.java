package com.ets.system.shiro;


import com.ets.business.custormer.service.CustomerUserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author Windows User
 *
 */

public class ShiroRealm extends AuthorizingRealm{


	@Autowired
	public CustomerUserService customerUserService;

	//@Autowired
	//public UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String username = upToken.getUsername();
		String password = new String(upToken.getPassword());


		Object principal = username;
		Object credentials = password;

		String realmName = getName();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials , realmName);

		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Subject subject = SecurityUtils.getSubject();
		Object obj = subject.getSession().getAttribute("userSession");

		addCustormetUserRole(info,(Map)obj);


		return info;
	}

	


	public void addCustormetUserRole(SimpleAuthorizationInfo info,Map map)
	{
		String str = (String) map.get("TYPE");
		int type = Integer.parseInt(str);
		if(type <=3) info.addRole("职员");
		if(type <=2) info.addRole("管理员");
		if(type <=1) info.addRole("创建者");
	}
}
