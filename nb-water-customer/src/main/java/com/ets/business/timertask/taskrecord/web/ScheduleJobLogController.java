package com.ets.business.timertask.taskrecord.web;

import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.timertask.taskrecord.entity.nb_schedule_job_log;
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
 * 接入类型数据字典
 * @author wh
 *
 */
@Controller
@RequestMapping("schedulejoblog")
public class ScheduleJobLogController {

	@Resource
    com.ets.business.timertask.taskrecord.service.ScheduleJobLogService ScheduleJobLogService;
	@Resource
    LogOprCustomerService logOprCustomerService;

	String baseUrl = "business/timertask/taskrecord/";
	
	String modulename = "数据字典-接入类型";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename("客户管理-定时任务-执行记录");
		log.setOprcontent("查看执行记录列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"JobLog-list";
	}

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
		nb_schedule_job_log ScheduleJobLog = null;
		if(id!=null && !id.equals(""))
		{
			ScheduleJobLog = ScheduleJobLogService.infoScheduleJobLog(id);
		}
		request.setAttribute("ScheduleJobLog", ScheduleJobLog);
		return baseUrl+"JobLog-input";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		nb_schedule_job_log ScheduleJobLog = null;
		if(id!=null && !id.equals(""))
		{
			ScheduleJobLog = ScheduleJobLogService.infoScheduleJobLog(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);
			//log.setOprcontent("查看"+modulename+"["+ScheduleJobLog.getJobname()+"]");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		}
		request.setAttribute("ScheduleJobLog", ScheduleJobLog);
		return baseUrl+"JobLog-info";
	}

	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate)
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
			
			map.put("customercode", customerId);
			
			if(startdate != null && !"".equals(startdate)){
				map.put("startdate", startdate + " 00:00:00");
			}
			
			if(enddate != null && !"".equals(enddate)){
				map.put("enddate", enddate + "23:59:59");
			}

			List<nb_schedule_job_log> ScheduleJobLog = ScheduleJobLogService.getScheduleJobLog(map);
			long count = ScheduleJobLogService.getCount(map);


			PageListData<nb_schedule_job_log> pageData = new PageListData<nb_schedule_job_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(ScheduleJobLog);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_schedule_job_log ScheduleJobLog)
	{
		Gson gson = new Gson();
		try {
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);
			//if(ScheduleJobLog.getId() == null || ScheduleJobLog.getId().equals(""))
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
				logOprCustomerService.addLog(log);
			ScheduleJobLogService.opentionScheduleJobLog(ScheduleJobLog);
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String id[])
	{
		Gson gson = new Gson();
		try {
			ScheduleJobLogService.deleteScheduleJobLog(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
}
