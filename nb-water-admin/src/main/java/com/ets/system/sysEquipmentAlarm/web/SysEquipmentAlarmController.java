package com.ets.system.sysEquipmentAlarm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.sysEquipmentAlarm.entity.tb_sys_equipment_alarm;
import com.ets.system.sysEquipmentAlarm.service.SysEquipmentAlarmService;
import com.google.gson.Gson;

/**
 * @author wh
 *
 */
@Controller
@RequestMapping("sysalarm")
public class SysEquipmentAlarmController {

	@Resource
	SysEquipmentAlarmService sysAlarmService;


	String baseUrl = "system/sysEquipmentAlarm/";
	
	String modulename = "水表管理-设备告警";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将用户"查看设备告警列表"信息保存到操作日志
		return baseUrl + "sysalarm-list";
	}

	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
			tb_sys_equipment_alarm entity = null;
			if(id!=null && !id.equals(""))
			{
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("id", id);
				entity = sysAlarmService.infoSysAlarm(map);
			}
			request.setAttribute("entity", entity);
			return baseUrl + "sysalarm-info";
	}

	/**
	 * 分页查询水表口径数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String imei,String alarmtype)
	{
		Gson gson = new Gson();
		try {

				//System.out.println("page="+page+",limit="+limit);
				Map<String,Object> map = new HashMap<String,Object>();
				//			map.put("page", (page-1)*limit);//mysql
				//			map.put("limit", limit);//mysql
				map.put("page", (page)*limit);//oracle
				map.put("limit", (page-1)*limit);//oracle
				map.put("imei", imei);
				
				map.put("startdate", startdate);//开始发送时间
				map.put("enddate", enddate);//结束发送时间
				map.put("imei", imei);//imei
				map.put("alarmtype", alarmtype);

				List<tb_sys_equipment_alarm> entity = sysAlarmService.getAlarm(map);
				long count = sysAlarmService.getCount(map);

				PageListData<tb_sys_equipment_alarm> pageData = new PageListData<tb_sys_equipment_alarm>();

				pageData.setCode("0");
				pageData.setCount(count);
				pageData.setMessage("");
				pageData.setData(entity);


				String listJson = gson.toJson(pageData);
				return listJson;
		} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,tb_sys_equipment_alarm entity)
	{
		Gson gson = new Gson();
		try {
				sysAlarmService.opentionSysAlarm(entity);
				return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
}
