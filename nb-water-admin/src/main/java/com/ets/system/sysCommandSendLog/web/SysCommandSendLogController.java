package com.ets.system.sysCommandSendLog.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ets.system.sysCommandSendLog.entity.tb_sys_command_send_log;
import com.ets.system.sysCommandSendLog.service.SysCommandSendLogService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.google.gson.Gson;

/**
 * 命令记录
 * @author wh
 *
 */
@Controller
@RequestMapping("syscommandsendlog")
public class SysCommandSendLogController {


	@Resource
	SysCommandSendLogService sysCommandSendLogService;

	String baseUrl = "system/sysCommandSendLog/";

	@RequestMapping("clist")
	public String clist(HttpServletRequest request)
	{
		return baseUrl + "syscommandsend-clist";
	}


	@RequestMapping("cinfo")
	public String cinfo(HttpServletRequest request,String id)
	{
		tb_sys_command_send_log cmdsend = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			cmdsend = sysCommandSendLogService.info(map);

		}
		request.setAttribute("cmdsend", cmdsend);
		return baseUrl + "syscommandsend-cinfo";
	}


	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="clistData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String clistData(int page,int limit,String startdate,String enddate,String deviceid,String status)
	{
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			map.put("startdate", startdate);//开始发送时间
			map.put("enddate", enddate);//结束发送时间
			map.put("deviceid", deviceid);//设备ID
			map.put("status", status);//状态
			List<tb_sys_command_send_log> comsend = sysCommandSendLogService.getSysCommandSendLog(map);

			long count = sysCommandSendLogService.getCount(map);


			PageListData<tb_sys_command_send_log> pageData = new PageListData<tb_sys_command_send_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(comsend);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid)
	{
		return baseUrl + "syscommandsend-list";
	}
	
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{

		tb_sys_command_send_log cmdsend = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			cmdsend = sysCommandSendLogService.info(map);
	
		}
		request.setAttribute("cmdsend", cmdsend);
		return baseUrl + "syscommandsend-info";
	}
	
	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String imei,String commandid,String status)
	{

		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			
			map.put("startdate", startdate);//开始发送时间
			map.put("enddate", enddate);//结束发送时间
			map.put("commandid", commandid);//命令ID
			map.put("imei", imei);//设备IMEI号
			map.put("status", status);//状态
			List<tb_sys_command_send_log> comsend = sysCommandSendLogService.getSysCommandSendLog(map);
			long count = sysCommandSendLogService.getCount(map);


			PageListData<tb_sys_command_send_log> pageData = new PageListData<tb_sys_command_send_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(comsend);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	@RequestMapping("history-syscmd-list")
	public String historySysCmdList(String deviceid,HttpServletRequest request)
	{
		request.setAttribute("deviceid",deviceid);
		return baseUrl + "syscommandsend-history-cmd";
	}
	
	@RequestMapping(value="history-syscmd-data" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deviceHistorySysCmd(HttpServletRequest request , String deviceid ,int page,int limit)
	{
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			map.put("deviceid", deviceid);//设备ID
			List<tb_sys_command_send_log> comsend = sysCommandSendLogService.getSysCommandSendLog(map);
			long count = sysCommandSendLogService.getCount(map);


			PageListData<tb_sys_command_send_log> pageData = new PageListData<tb_sys_command_send_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(comsend);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
