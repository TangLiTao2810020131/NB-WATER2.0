package com.ets.business.init.condition.web;

import com.ets.business.init.condition.entity.nb_account_opener_dic;
import com.ets.business.init.condition.entity.nb_overdue_fine_dic;
import com.ets.business.init.condition.entity.nb_price_max_dic;
import com.ets.business.init.condition.service.ConditionService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
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
@RequestMapping("condition")
public class ConditionController {
	@Resource
    ConditionService conditionService;
	@Resource
    LogOprCustomerService logOprCustomerService;


	String modulename="客户管理-限制金额";

	@RequestMapping("info")
	public String info(HttpServletRequest request)
	{
		Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		nb_account_opener_dic accountOpener = conditionService.infoAccountOpener(customerId);
		nb_overdue_fine_dic overdueFine = conditionService.infoOverdueFine(customerId);
		nb_price_max_dic priceMax = conditionService.infoPriceMax(customerId);
		
		request.setAttribute("accountOpener", accountOpener);
		request.setAttribute("overdueFine", overdueFine);
		request.setAttribute("priceMax", priceMax);


		//将查看"限制金额列表"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("查看限制金额列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		
		return "business/init/condition/condition-info";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,float maxprice ,float overdueFilePrice , float accountOpenerPrice,String pid,String oid,String aid)
	{
		Gson gson = new Gson();
		Map loginMap = (Map)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		
		nb_account_opener_dic accountOpener = new nb_account_opener_dic();
		nb_overdue_fine_dic overdueFine = new nb_overdue_fine_dic();
		nb_price_max_dic priceMax = new nb_price_max_dic();
		
		
		accountOpener.setId(aid);
		accountOpener.setPrice(accountOpenerPrice);
		accountOpener.setCustomerId(customerId);
		
		overdueFine.setId(oid);
		overdueFine.setPrice(overdueFilePrice);
		overdueFine.setCustomerId(customerId);
		
		priceMax.setId(pid);
		priceMax.setMaxprice(maxprice);
		priceMax.setCustomerId(customerId);
		
		conditionService.insetAccountOpener(accountOpener);
		conditionService.insetOverdueFine(overdueFine);
		conditionService.insetPriceMax(priceMax);

		//将查看"修改限制金额"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("修改限制金额");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		
		return gson.toJson("操作成功");
	}
}
