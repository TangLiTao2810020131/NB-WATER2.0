package com.ets.system.sysReadLog.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.ets.system.cus.entity.nb_cus;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.sysReadLog.entity.tb_sys_read_log;
import com.ets.system.sysReadLog.service.SysReadLogService;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.google.gson.Gson;

/**
 * 上报记录
 * @author wh
 *
 */
@Controller
@RequestMapping("sysreadlog")
public class SysReadLogController {


	@Resource
	SysReadLogService sysReadLogService;


	String baseUrl = "system/sysReadLog/";


	@RequestMapping("clist")
	public String clist(HttpServletRequest request)
	{

		return baseUrl + "sysreadlog-clist";
	}


	@RequestMapping("cinfo")
	public String cinfo(HttpServletRequest request,String id)
	{
		tb_sys_read_log readlog = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			readlog = sysReadLogService.info(map);

		}
		request.setAttribute("readlog", readlog);
		return baseUrl + "sysreadlog-cinfo";
	}


	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="clistData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String clistData(int page,int limit,String startdate,String enddate,String startrdate,String endrdate,String deviceid)
	{
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			map.put("startdate", startdate);//数据上报开始时间
			map.put("enddate", enddate);//数据上报结束时间
			map.put("startrdate", startrdate);//设备注册开始时间
			map.put("endrdate", endrdate);//设备注册结束时间
			map.put("deviceid", deviceid);//设备ID
			List<tb_sys_read_log> comsend = sysReadLogService.getReadLog(map);
			long count = sysReadLogService.getCount(map);


			PageListData<tb_sys_read_log> pageData = new PageListData<tb_sys_read_log>();

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
		return baseUrl + "sysreadlog-list";
	}
	
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{

		tb_sys_read_log readlog = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			readlog = sysReadLogService.info(map);

		}
		request.setAttribute("readlog", readlog);
		return baseUrl + "sysreadlog-info";
	}
	
	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String startrdate,String endrdate,String imei)
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
			map.put("startrdate", startrdate);//设备注册开始时间
			map.put("endrdate", endrdate);//设备注册结束时间
			map.put("imei", imei);//设备ID
			List<tb_sys_read_log> comsend = sysReadLogService.getReadLog(map);
			long count = sysReadLogService.getCount(map);


			PageListData<tb_sys_read_log> pageData = new PageListData<tb_sys_read_log>();

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
	
	@RequestMapping("history-sysreadlog-list")
	public String historySysreadlogList(String deviceid,HttpServletRequest request)
	{
		request.setAttribute("deviceid",deviceid);
		return baseUrl + "sysreadlog-history-data";
	}
	
	@RequestMapping(value="history-sysreadlog-data" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deviceHistorySysreadlogData(HttpServletRequest request , String deviceid ,int page,int limit)
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
			List<tb_sys_read_log> comsend = sysReadLogService.getReadLog(map);
			long count = sysReadLogService.getCount(map);


			PageListData<tb_sys_read_log> pageData = new PageListData<tb_sys_read_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(comsend);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
}
