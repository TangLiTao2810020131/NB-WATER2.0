package com.ets.business.logopr.web;

import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
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
 * @author: 濮氳蕉鏂�
 * @date:2018骞�9鏈�5鏃� 涓嬪崍4:34:09
 * @version :
 * 
 */
@Controller
@RequestMapping("logCustomerOpr")
public class LogOprCustomerController {

	@Resource
    LogOprCustomerService logOprCustomerService;

	String modulename="客户管理-操作日志";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将查看"操作日志列表"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));
        log.setModulename(modulename);
        log.setOprcontent("查看操作日志列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		return "business/logopr/log-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page, int limit, tb_log_opr_customer oprLogin)
		{
			Map<?,?> loginMap = (Map<?,?>) SecurityUtils.getSubject().getSession().getAttribute("userSession");
			String cid = (String)loginMap.get("CID");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("username", oprLogin.getUsername());
			map.put("oprtime", oprLogin.getOprtime());
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
			map.put("customercode", cid);
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			List<tb_log_opr_customer> logs = logOprCustomerService.getLogs(map);
			long count = logOprCustomerService.getCount(map);


		PageListData<tb_log_opr_customer> pageData = new PageListData<tb_log_opr_customer>();
		
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
		tb_log_opr_customer loginfo = logOprCustomerService.infoLog(id);
		
		request.setAttribute("loginfo", loginfo);
		return "business/logopr/log-info";
	}
}
