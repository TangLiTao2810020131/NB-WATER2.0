package com.ets.system.log.login.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.PageListData;
import com.ets.system.log.login.entity.tb_log_login;
import com.ets.system.log.login.service.LogLoginService;
import com.google.gson.Gson;

/**
 * @author: 姚轶文
 * @date:2018年9月5日 上午10:00:01
 * @version :
 * 
 */
@Controller
@RequestMapping("logLogin")
public class LogLoginController {
	
	@Resource
	LogLoginService logLoginService;
	@Resource
	LogOprService logService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将查看"登录日志列表"信息保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("系统管理-登录日志");
		log.setOprcontent("查看登录日志列表");
		logService.addLog(log);

		return "system/log/login/log-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,tb_log_login logLogin)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", logLogin.getUsername());
		map.put("ipaddress", logLogin.getIpaddress());
		map.put("logintime", logLogin.getLogintime());
		map.put("region", logLogin.getRegion());
		map.put("city", logLogin.getCity());
		map.put("isp", logLogin.getIsp());
		map.put("ostype", logLogin.getOstype());
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		
		List<tb_log_login> logs = logLoginService.getLogs(map);
		long count = logLoginService.getCount(map);
		
		
		PageListData<tb_log_login> pageData = new PageListData<tb_log_login>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(logs);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	@RequestMapping("info")
	public String loginfo(String id,HttpServletRequest request)
	{
		//System.out.println(id);
		tb_log_login loginfo = logLoginService.infoLog(id);
		
		request.setAttribute("loginfo", loginfo);
		return "system/log/login/log-info";
	}
}
