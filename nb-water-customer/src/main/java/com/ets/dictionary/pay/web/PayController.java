package com.ets.dictionary.pay.web;

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
import com.ets.dictionary.pay.entity.nb_pay_dict;
import com.ets.dictionary.pay.service.PayService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;

/**
 * 付费模式数据字典
 * @author wh
 *
 */
@Controller
@RequestMapping("pay")
public class PayController {

	@Resource
	PayService payService;
	@Resource
    LogOprService logService;

	String baseUrl = "dictionary/pay/";
	
	String modulename = "数据字典-付费模式";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		tb_log_opr log = new tb_log_opr();
		log.setModulename(modulename);
		log.setOprcontent("访问付费模式列表");
		logService.addLog(log);
		return baseUrl+"pay-list";
	}

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
		nb_pay_dict pay = null;
		if(id!=null && !id.equals(""))
		{
			pay = payService.infoPay(id);
		}
		request.setAttribute("pay", pay);
		return baseUrl+"pay-input";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		nb_pay_dict pay = null;
		if(id!=null && !id.equals(""))
		{
			pay = payService.infoPay(id);
			tb_log_opr log = new tb_log_opr();
			log.setModulename(modulename);
			log.setOprcontent("查看"+"["+pay.getPaymode()+"]"+"付费类型");
			logService.addLog(log);
		}
		request.setAttribute("pay", pay);
		return baseUrl+"pay-info";
	}

	/**
	 * 分页查询付费模式数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit)
	{
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			List<nb_pay_dict> pay = payService.getPay(map);
			long count = payService.getCount();


			PageListData<nb_pay_dict> pageData = new PageListData<nb_pay_dict>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(pay);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(nb_pay_dict pay)
	{
		Gson gson = new Gson();
		try {
			tb_log_opr log = new tb_log_opr();
			log.setModulename(modulename);
			if(pay.getId() == null || pay.getId().equals(""))
			{
				log.setOprcontent("新增"+"["+pay.getPaymode()+"]"+"付费类型");
			}
			else
			{
				log.setOprcontent("修改"+"["+pay.getPaymode()+"]"+"付费类型");
			}
			logService.addLog(log);
			int i = payService.opentionPay(pay);
			return gson.toJson(""+i);
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson("");
		}
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		Gson gson = new Gson();
		try {
			String paymode = payService.infoPay(id);
			payService.deletePay(id);
			tb_log_opr log = new tb_log_opr();
			log.setModulename(modulename);
			log.setOprcontent("删除"+"["+paymode+"]"+"付费类型");
			logService.addLog(log);
			
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
}
