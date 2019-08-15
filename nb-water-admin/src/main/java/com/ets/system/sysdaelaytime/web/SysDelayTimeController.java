package com.ets.system.sysdaelaytime.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.system.sysdaelaytime.entity.sys_delay_time;
import com.ets.system.sysdaelaytime.service.SysDelayTimeService;
import com.google.gson.Gson;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午11:01:35
 * @version :
 * 
 */
@Controller
@RequestMapping("sysdelaytime")
public class SysDelayTimeController {
	
	String baseUrl = "system/sysdelaytime/";
	
	@Resource
	SysDelayTimeService sysDelayTimeService;

	
	@RequestMapping("info")
	public String info(HttpServletRequest request)
	{
		sys_delay_time sysDelayTime = sysDelayTimeService.getSysDaelayTime();
		
		request.setAttribute("time", sysDelayTime);
		
		return baseUrl + "sysdelaytime-info";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,String id,String basicnumtime,String deliverytime,String valvecontroltime)
	{
		Gson gson = new Gson();
		sys_delay_time sysDelayTime = new sys_delay_time();
		sysDelayTime.setId(id);
		sysDelayTime.setBasicnumtime(basicnumtime);
		sysDelayTime.setDeliverytime(deliverytime);
		sysDelayTime.setValvecontroltime(valvecontroltime);
		sysDelayTimeService.update(sysDelayTime);
		
		return gson.toJson(new Message("1","操作成功!"));
	}
}
