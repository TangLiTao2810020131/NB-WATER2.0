package com.ets.system.shiro;



import com.ets.system.authority.service.AuthorityService;
import com.ets.system.authority_resource.service.AuthorityResourceService;
import com.ets.system.resource.entity.tb_resource;
import com.ets.system.resource.service.ResourceService;
import com.ets.system.role.entity.tb_role;
import com.ets.system.role.service.RoleService;
import com.ets.system.role_authority.entity.tb_role_authority;
import com.ets.system.role_authority.service.RoleAuthorityService;
import com.ets.system.user.entity.tb_user;
import com.ets.system.user.service.UserService;
import com.ets.system.user_role.service.UserRoleService;
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
	public ResourceService resourceService;

	@Autowired
	public UserService userService;
	@Resource
	@Autowired
	public RoleService roleService;

	@Autowired
	public UserRoleService userRoleService;

	@Autowired
	public RoleAuthorityService roleAuthorityService;

	@Autowired
	public AuthorityResourceService authorityResourceService;

	@Autowired
	public AuthorityService authorityService;



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
		if(obj instanceof tb_user)
		{
			addPermission(info,(tb_user)obj);
		}
		else if(obj instanceof Map)
		{
			addCustormetUserRole(info,(Map)obj);
		}


		return info;
	}

	public void addPermission(SimpleAuthorizationInfo info,tb_user user)
	{
		List<tb_role> roleList = userRoleService.getRoles(user.getId());
		if(roleList!=null && roleList.size()>0)
		{
			for(int i=0 ; i<roleList.size() ; i++)
			{
				tb_role role = roleList.get(i);
				List<tb_role_authority> authorityList = roleAuthorityService.getAuthorityByRoleId(role.getId());
				if(authorityList!=null && authorityList.size()>0)
				{
					for(int j=0 ; j<authorityList.size() ; j++)
					{
						tb_role_authority roleAuthority = (tb_role_authority)authorityList.get(j);

						List<tb_resource> resourceList = authorityResourceService.getResources(roleAuthority.getAuthorityId());
						if(resourceList!=null && resourceList.size()>0)
						{
							for(int k=0 ; k<resourceList.size() ; k++)
							{
								tb_resource resource = resourceList.get(k);
								info.addStringPermission(resource.getResourcename());
							}
						}
					}
				}
				info.addRole(role.getRolename());
			}
		}
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
