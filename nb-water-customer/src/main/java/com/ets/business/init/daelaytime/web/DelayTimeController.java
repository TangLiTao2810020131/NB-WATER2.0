package com.ets.business.init.daelaytime.web;

import com.ets.business.init.daelaytime.entity.nb_delay_time_basicnum;
import com.ets.business.init.daelaytime.entity.nb_delay_time_delivery;
import com.ets.business.init.daelaytime.entity.nb_delay_time_valvecontrol;
import com.ets.business.init.daelaytime.service.DelayTimeService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.common.Message;
import com.ets.system.cus.entity.nb_cus;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月8日 上午11:01:35
 * @version :
 * 
 */
@Controller
@RequestMapping("delaytime")
public class DelayTimeController {
	@Resource
	DelayTimeService delayTimeService;
	@Resource
	LogOprCustomerService logOprCustomerService;

	String modulename="客户管理-命令延迟设置";
	@RequestMapping("info")
	public String info(HttpServletRequest request)
	{
		Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		nb_delay_time_basicnum basicnum = delayTimeService.infoBasicNum(customerId);
		nb_delay_time_delivery delivery = delayTimeService.infoDelivery(customerId);
		nb_delay_time_valvecontrol valvecontrol = delayTimeService.infoValveControl(customerId);
		
		request.setAttribute("basicnum", basicnum);
		request.setAttribute("delivery", delivery);
		request.setAttribute("valvecontrol", valvecontrol);

		//将查看"命令延迟设置列表"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("查看命令延迟设置列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		
		return "business/init/delaytime/delaytime-info";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,String basicnumid,String basicnumtime,String deliveryid,String deliverytime,String valvecontrolid,String valvecontroltime)
	{
		Gson gson = new Gson();
		Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		
		nb_delay_time_basicnum basicnum = new nb_delay_time_basicnum();
		nb_delay_time_delivery delivery = new nb_delay_time_delivery();
		nb_delay_time_valvecontrol valvecontrol = new nb_delay_time_valvecontrol();
		
		
		basicnum.setId(basicnumid);
		basicnum.setValue(basicnumtime);
		basicnum.setCustomerId(customerId);
		
		delivery.setId(deliveryid);
		delivery.setValue(deliverytime);
		delivery.setCustomerId(customerId);
		
		valvecontrol.setId(valvecontrolid);
		valvecontrol.setValue(valvecontroltime);
		valvecontrol.setCustomerId(customerId);
		
		delayTimeService.insetBasicNum(basicnum);
		delayTimeService.insetDelivery(delivery);
		delayTimeService.insetValveControl(valvecontrol);

		//将"修改命令延迟参数"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		log.setOprcontent("修改命令延迟参数[表读数延迟时间:"+basicnum.getValue()+";上报周期延迟时间:"+delivery.getValue()+";阀控延迟时间:"+valvecontrol.getValue()+"](秒)");
		logOprCustomerService.addLog(log);
		
		return gson.toJson(new Message("1","操作成功!"));
	}
}
