package com.ets.business.alarm.web;

import com.ets.business.alarm.entity.nb_equipment_alarm;
import com.ets.business.alarm.service.EquipmentAlarmService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.cus.entity.nb_cus;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wh
 *
 */
@Controller
@RequestMapping("alarm")
public class EquipmentAlarmController {

	@Resource
    EquipmentAlarmService alarmService;

	@Resource
	LogOprCustomerService logOprCustomerService;

	String baseUrl = "business/alarm/";
	
	String modulename = "客户管理-设备告警";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将用户"查看设备告警列表"信息保存到操作日志
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));
		log.setModulename(modulename);
		log.setOprcontent("查看设备告警列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"alarm-list";
	}

	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
			nb_equipment_alarm entity = null;
			if(id!=null && !id.equals(""))
			{
				Map<String,Object> map = new HashMap<String,Object>();
				
				map.put("id", id);
				entity = alarmService.infoAlarm(map);


				tb_log_opr_customer log = new tb_log_opr_customer();
				log.setIp(logOprCustomerService.getIp(request));
				log.setModulename(modulename);
				log.setOprcontent("查看户名为"+"["+entity.getUsername()+"]设备报警信息");
				nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
				log.setCustomercode(cus.getId());
				logOprCustomerService.addLog(log);
			}
			request.setAttribute("entity", entity);
			return baseUrl+"alarm-info";
	}

	/**
	 * 分页查询水表口径数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String useraccount,String alarmtype,String imei)
	{
		Gson gson = new Gson();
		try {
				Map<?,?> loginMap = (Map<?,?>) SecurityUtils.getSubject().getSession().getAttribute("userSession");
				String cid = (String)loginMap.get("CID");
				//System.out.println("page="+page+",limit="+limit);
				Map<String,Object> map = new HashMap<String,Object>();
				//			map.put("page", (page-1)*limit);//mysql
				//			map.put("limit", limit);//mysql
				map.put("page", (page)*limit);//oracle
				map.put("limit", (page-1)*limit);//oracle
				map.put("useraccount", useraccount);
				map.put("watermetercode", imei);
				map.put("alarmtype", alarmtype);
				map.put("customercode", cid);

				List<nb_equipment_alarm> entity = alarmService.getAlarm(map);
				long count = alarmService.getCount(map);

				PageListData<nb_equipment_alarm> pageData = new PageListData<nb_equipment_alarm>();

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
	public String save(HttpServletRequest request,nb_equipment_alarm entity)
	{
		Gson gson = new Gson();
		try {
				tb_log_opr_customer log = new tb_log_opr_customer();
				log.setModulename(modulename);
			    log.setIp(logOprCustomerService.getIp(request));

				if(entity.getId() == null || entity.getId().equals(""))
				{
					log.setOprcontent("新增告警"+"["+entity.getAlarmtype()+"]");
				}
				nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
				log.setCustomercode(cus.getId());
			    logOprCustomerService.addLog(log);
				alarmService.opentionAlarm(entity);
				return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
}
