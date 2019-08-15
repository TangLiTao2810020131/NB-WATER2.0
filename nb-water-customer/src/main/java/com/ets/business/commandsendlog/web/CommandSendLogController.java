package com.ets.business.commandsendlog.web;

import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.business.commandsendlog.service.CommandSendLogService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
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
 * 户主操作类
 * @author wh
 *
 */
@Controller
@RequestMapping("commandsendlog")
public class CommandSendLogController {


	@Resource
    CommandSendLogService commandSendLogService;
	@Resource
    LogOprService logService;
	@Resource
    LogOprCustomerService logOprCustomerService;


	String baseUrl = "business/commandsend/";

	String modulename_1 = "客户管理-命令记录";
	String modulename_2 ="客户管理-设备管理-命令记录";

	@RequestMapping("clist")
	public String clist(HttpServletRequest request,String areaid)
	{
		tb_log_opr log = new tb_log_opr();
		log.setModulename(modulename_1);
		log.setOprcontent("查看命令记录列表");
		logService.addLog(log);
		request.setAttribute("areaid",areaid);
		return baseUrl+"commandsend-clist";
	}


	@RequestMapping("cinfo")
	public String cinfo(HttpServletRequest request,String id)
	{
		nb_command_send_log cmdsend = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			cmdsend = commandSendLogService.info(map);
			tb_log_opr log = new tb_log_opr();
			log.setModulename(modulename_1);
			log.setOprcontent("查看命令ID为["+cmdsend.getCommandid()+"]" +
					"设备ID为["+cmdsend.getDeviceid()+"]命令详情");
			logService.addLog(log);
		}
		request.setAttribute("cmdsend", cmdsend);
		return baseUrl+"commandsend-cinfo";
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
			map.put("commandid", commandid);//命令ID
			map.put("status", status);//状态
			List<nb_command_send_log> comsend = commandSendLogService.getCommandSendLog(map);

			long count = commandSendLogService.getCount(map);


			PageListData<nb_command_send_log> pageData = new PageListData<nb_command_send_log>();

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
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setModulename(modulename_2);
		log.setOprcontent("访问命令记录列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		request.setAttribute("areaid",areaid);
		return baseUrl+"commandsend-list";
	}
	
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_command_send_log cmdsend = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("id", id);
			cmdsend = commandSendLogService.info(map);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename_2);
			log.setOprcontent("查看命令ID为["+cmdsend.getCommandid()+"]" +
					"设备ID为["+cmdsend.getDeviceid()+"]命令详情");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		}
		request.setAttribute("cmdsend", cmdsend);
		return baseUrl+"commandsend-info";
	}
	
	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String imei,String status)
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
			map.put("imei", imei);//设备IMEI
			map.put("status", status);//状态
			List<nb_command_send_log> comsend = commandSendLogService.getCommandSendLog(map);
			long count = commandSendLogService.getCount(map);


			PageListData<nb_command_send_log> pageData = new PageListData<nb_command_send_log>();

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
