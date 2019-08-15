package com.ets.system.authority.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.resource.entity.tb_resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.PageListData;
import com.ets.system.authority.entity.tb_authority;
import com.ets.system.authority.service.AuthorityService;
import com.ets.system.authority_resource.entity.tb_authority_resource;
import com.ets.system.authority_resource.service.AuthorityResourceService;
import com.ets.system.resource.entity.ZtreeNode;
import com.ets.system.resource.service.ResourceService;
import com.ets.system.shiro.cache.RedisClientTemplate;
import com.google.gson.Gson;

/**
 * @author: 姚轶文
 * @date:2018年8月28日 下午2:01:53
 * @version :
 * 
 */
@Controller
@RequestMapping("authority")
public class AuthorityController {
	
	@Resource
	AuthorityResourceService authorityResourceService;
	@Resource
    RedisClientTemplate redisClientTemplate;
	@Resource
	AuthorityService authorityService;
	@Resource
	ResourceService resourceService;
	@Resource
	AuthorityResourceService arService;
	@Resource
	LogOprService logService;


	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将用户"查看权限管理列表"信息保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("系统管理-权限管理");
		log.setOprcontent("查看权限管理列表");
		logService.addLog(log);

		return "system/authority/authority-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
		//		map.put("page", (page-1)*limit);//mysql
		//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle

		List<tb_authority> authority = authorityService.getAuthoritys(map);
		long count = authorityService.getCount();


		PageListData<tb_authority> pageData = new PageListData<tb_authority>();

		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(authority);

		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}

	@RequestMapping("info")
	public String authorityinfo(String id,HttpServletRequest request)
	{
		//System.out.println(id);
		List<tb_authority_resource> arList = arService.getResourceByAuthorityId(id);
		if(arList != null && arList.size()>0)
		{
			Gson gson = new Gson();
			List<ZtreeNode> resourceList = resourceService.getZtreeNodeList();
			resourceList = arService.checkTreeNode(resourceList, arList);
			String tree = gson.toJson(resourceList);
			request.setAttribute("tree", tree);
		}

		tb_authority authority = authorityService.infoAuthority(id);
		request.setAttribute("authority", authority);

		//将用户查看"权限管理"列表中"xxx权限信息"保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("系统管理-权限管理");
		log.setOprcontent("查看"+"["+authority.getAuthorityname()+"]"+"权限");
		logService.addLog(log);

		return "system/authority/authority-info";
	}

	@RequestMapping("input")
	public String authorityinput(String id,HttpServletRequest request)
	{
		tb_authority authority = null;
		if(id!=null && !id.equals(""))
		{
			authority = authorityService.infoAuthority(id);
		}
		request.setAttribute("authority", authority);

		return "system/authority/authority-input";
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(tb_authority authority)
	{
		tb_log_opr log=new tb_log_opr();
		//如果权限的ID不存在将"新增XXX权限保存到操作日志",否则将"编辑XXX权限保存到操作日志"
		if(authority.getId()==null||authority.getId().equals(""))
		{
			log.setModulename("系统管理-权限管理");
			log.setOprcontent("新增"+"["+authority.getAuthorityname()+"]"+"权限");

		}else{
			log.setModulename("系统管理-权限管理");
			log.setOprcontent("编辑"+"["+authority.getAuthorityname()+"]"+"权限");
		}
		logService.addLog(log);

		Gson gson = new Gson();
		System.out.println(authority);
		//判断是否存在权限名称
		int result= authorityService.insetResource(authority);

		return gson.toJson(""+result);

	}

	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{

		//将删除"xxx权限"保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("系统管理-权限管理");
		StringBuilder sb=new StringBuilder();
		for(String str:id)
		{
			tb_authority authority=authorityService.infoAuthority(str);
			sb.append(authority.getAuthorityname()+",");
		}
		log.setOprcontent("删除"+"["+sb.substring(0,sb.length()-1)+"]"+"权限");
		logService.addLog(log);

		authorityService.deleteAuthority(id);
		for (int i = 0; i < id.length; i++) {
			authorityResourceService.deleteResourceByAuthorityId(id[i]);
		}
		Gson gson = new Gson();

		return gson.toJson("");
	}

	@RequestMapping(value="inResource" )
	public String inResource(HttpServletRequest request , String id)
	{
		Gson gson = new Gson();
		List<ZtreeNode> resourceList = resourceService.getZtreeNodeList();

		List<tb_authority_resource> arList = arService.getResourceByAuthorityId(id);
		if(arList != null && arList.size()>0)
		{
			resourceList = arService.checkTreeNode(resourceList, arList);
		}


		String tree = gson.toJson(resourceList);
		request.setAttribute("tree", tree);
		request.setAttribute("aid", id);
		return "system/authority/authority-inputResource";
	}

	@RequestMapping(value="saveResource" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveResource(String[] ids,String aid)
	{
		//System.out.println(ids);
		arService.deleteResourceByAuthorityId(aid);
		for (String id : ids) {
			tb_authority_resource ar = new tb_authority_resource();
			ar.setAuthorityId(aid);
			ar.setResourceId(id);
			arService.save(ar);
		}
		Gson gson = new Gson();

		//为xxx权限分配"[xxx,xxx,xxx]"资源信息保存到操作日志  例如:为"经理"分配"[6个员工,2台笔记本]"资源
		tb_log_opr log=new tb_log_opr();
		log.setModulename("系统管理-权限管理");
		StringBuilder sb=new StringBuilder();
		for(String id:ids)
		{
			tb_resource resource = resourceService.infoResource(id);
			sb.append(resource.getResourcename() + ",");
		}
		tb_authority  ta = authorityService.infoAuthority(aid);
		log.setOprcontent("为" + ta.getAuthorityname() + "分配[" + sb.substring(0,sb.length()-1) + "]资源");
		logService.addLog(log);
		
		redisClientTemplate.delSession();

		return gson.toJson("操作成功");
	}
}
