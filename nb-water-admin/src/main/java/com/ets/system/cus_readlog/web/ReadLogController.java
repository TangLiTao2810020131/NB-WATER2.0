package com.ets.system.cus_readlog.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.cus_readlog.entity.nb_read_log;
import com.ets.system.cus_readlog.service.ReadLogService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;

/**
 * 户主操作类
 * @author wh
 *
 */
@Controller
@RequestMapping("readlog")
public class ReadLogController {


	@Resource
    ReadLogService readLogService;
	@Resource
    LogOprService logService;


	String baseUrl = "system/cus_readlog/";

	String modulename_1 = "客户管理-数据上报记录";
	String modulename_2 = "客户管理-设备管理-上报记录";//客户端

	@RequestMapping("clist")
	public String clist(HttpServletRequest request,String areaid)
	{
		tb_log_opr log = new tb_log_opr();
		log.setModulename(modulename_1);
		log.setOprcontent("查看数据上报记录列表");
		logService.addLog(log);
		return baseUrl+"readlog-clist";
	}


	@RequestMapping("cinfo")
	public String cinfo(HttpServletRequest request,String id)
	{
		nb_read_log readlog = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			readlog = readLogService.info(map);
			tb_log_opr log = new tb_log_opr();
			log.setModulename(modulename_1);
			log.setOprcontent("查看数据上报详情");
			logService.addLog(log);
		}
		request.setAttribute("readlog", readlog);
		return baseUrl+"readlog-cinfo";
	}


	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="clistData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String clistData(int page,int limit,String startdate,String enddate,String deviceid,String commandid,String status)
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
			map.put("deviceid", deviceid);//设备ID
			List<nb_read_log> comsend = readLogService.getReadLog(map);
			long count = readLogService.getCount(map);


			PageListData<nb_read_log> pageData = new PageListData<nb_read_log>();

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

		request.setAttribute("areaid",areaid);
		return baseUrl+"readlog-list";
	}
	
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_read_log readlog = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("id", id);
			readlog = readLogService.info(map);
		}
		request.setAttribute("readlog", readlog);
		return baseUrl+"readlog-info";
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
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			map.put("customercode", customerId);//客户ID

			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			
			map.put("startdate", startdate);//开始发送时间
			map.put("enddate", enddate);//结束发送时间
			map.put("imei", imei);//设备IMEI号
			List<nb_read_log> comsend = readLogService.getReadLog(map);
			long count = readLogService.getCount(map);


			PageListData<nb_read_log> pageData = new PageListData<nb_read_log>();

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
}
