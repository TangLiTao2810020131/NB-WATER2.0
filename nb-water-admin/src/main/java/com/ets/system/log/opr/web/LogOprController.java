package com.ets.system.log.opr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.PageListData;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;

/**
 * @author: 濮氳蕉鏂�
 * @date:2018骞�9鏈�5鏃� 涓嬪崍4:34:09
 * @version :
 * 
 */
@Controller
@RequestMapping("logOpr")
public class LogOprController {

	@Resource
	LogOprService logOprService;
	@Resource
	LogOprService logService;

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将查看"操作日志列表"信息保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("系统管理-操作日志");
		log.setOprcontent("查看操作日志列表");
		logService.addLog(log);

		return "system/log/opr/log-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,tb_log_opr oprLogin)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", oprLogin.getUsername());
		map.put("oprtime", oprLogin.getOprtime());
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle

		List<tb_log_opr> logs = logOprService.getLogs(map);
		long count = logOprService.getCount(map);


		PageListData<tb_log_opr> pageData = new PageListData<tb_log_opr>();

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
		tb_log_opr loginfo = logOprService.infoLog(id);

		request.setAttribute("loginfo", loginfo);
		return "system/log/opr/log-info";
	}
}
