package com.ets.business.timertask.task.web;

import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.timertask.task.entity.nb_schedule_job;
import com.ets.business.timertask.task.service.TimerTaskService;
import com.ets.business.timertask.taskrecord.entity.nb_schedule_job_log;
import com.ets.business.timertask.taskrecord.service.ScheduleJobLogService;
import com.ets.common.*;
import com.ets.system.cus.entity.nb_cus;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/timertask")
public class TimerTaskController {
	
	// 日志记录器
	public final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TimerTaskService taskService;

	@Autowired
	ScheduleJobLogService jobLogService;

	@Resource
	LogOprCustomerService logOprCustomerService;
	
	@Resource
	RestTemplate restTemplate;
	
	@Resource
	ConstantConfig constantConfig;

	String baseUrl = "business/timertask/task/";
	String modulename="客户管理-定时任务-任务配置";

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_schedule_job job = new nb_schedule_job();
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			job = taskService.infoJob(map);
		}
		request.setAttribute("job", job);

		return baseUrl+"timertask-input";
	}

	@RequestMapping("info")
	public String info(HttpServletRequest request,String jobid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_schedule_job job = new nb_schedule_job();
		nb_schedule_job_log joblog = new nb_schedule_job_log();

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		job = taskService.infoJob(map);

		map.put("jobid", job.getJobid());
		map.put("ctime", DateTimeUtils.getMonth());
		joblog = jobLogService.infoJobLog(map);
		List<String> list = DateTimeUtils.getDateList();
		request.setAttribute("job", job);
		request.setAttribute("joblog", joblog);
		request.setAttribute("list", list);

		//将查看"任务配置列表"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("查看任务配置列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"timertask-info";
	}


	@RequestMapping("list")
	public String taskList(HttpServletRequest request) {

		return baseUrl+"timertask-list";
	}


	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String watermetercode,String status)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			/*			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			map.put("customercode", customerId);
			map.put("startdate", startdate);
			map.put("enddate", enddate);
			map.put("watermetercode", watermetercode);
			map.put("status", status);*/
			map.put("customercode", customerId);
			List<nb_schedule_job> taskList = taskService.getAllTask(map);
			map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			long count = 2l;//水表设备总条数
			PageListData<nb_schedule_job> pageData = new PageListData<nb_schedule_job>();//分业集合对象
			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(taskList);
			String listJson = gson.toJson(pageData);//转换为JSON字符串
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}


	@RequestMapping(value="isCkeckJobName" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckJobName(nb_schedule_job job) {
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			int num = 0;
			if("".equals(job.getJobid())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("customercode", customerId);//客户ID
				map.put("jobName", job.getJobname());//户号
				num = taskService.isCkeckJobName(map);
			}
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request, nb_schedule_job job) {

		String cronExpression = CronExpressionUtil.getCronExpression(job.getExecutordate());

		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();

		try {
			if(job.getJobid() != null && "".equals(job.getJobid())){
				job.setJobid(UUIDUtils.getUUID());
				job.setJobname("自动结算");
				job.setJobgroup("Balance"+job.getExecutordate() + customerId);
				job.setCronexpression(cronExpression);
				job.setIsconcurrent(ObjectCode.TASK_CONCURRENT_NOT);
				job.setCustomercode(customerId);
				job.setJobstatus(ObjectCode.TASK_STATUS_NOT_RUNNING);
				job.setSpringid("task");
				job.setBeanclass("com.ets.business.timertask.task.Task");
				job.setMethodname("runBalance");
				taskService.addTask(job);
			}else{
				job.setJobgroup("Balance"+job.getExecutordate()+customerId);
				job.setCronexpression(cronExpression);
				job = taskService.updateCron(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("0","保存失败！"));
		}

		//将"修改水表信息"保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("修改水表信息[执行日期:每月"+job.getExecutordate()+"号]");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		return gson.toJson(new Message("0","保存成功！定时任务[自动结算]于每月"+job.getExecutordate()+"号(00时00分:00秒)执行!"));
	}


	@RequestMapping("changeJobStatus")
	@ResponseBody
	public String changeJobStatus(HttpServletRequest request, String jobId, String cmd) {
		Gson gson = new Gson();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("jobid", jobId);
			map.put("type", cmd);
			restTemplate.postForObject(constantConfig.getQuarztPreHost() + "/api/changeJob.action", map, Boolean.class);
			taskService.changeStatus(jobId, cmd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return gson.toJson(new Message("0","任务状态改变失败！"));
		}

		//将查看"操作日志列表"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("修改任务状态["+cmd+"]");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		return gson.toJson(new Message("1","任务状态改变成功！"));
	}

}
