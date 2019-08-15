package com.ets.business.record.ownertransfer.web;

import com.ets.business.init.price.entity.nb_price_dic;
import com.ets.business.init.price.service.PriceDicService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.owner.entity.OwnerModel;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.record.ownertransfer.entity.nb_owner_transferrecord;
import com.ets.business.record.ownertransfer.service.OwnertransferService;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.dictionary.pay.entity.nb_pay_dict;
import com.ets.dictionary.pay.service.PayService;
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
 * 用户过户
 * @author wh
 *
 */
@Controller
@RequestMapping("transferrecord")
public class OwnertransferController {

	@Resource
	OwnertransferService ownertransferService;
	@Resource
	OwnerService ownerService;
	@Resource
    PayService payService;
	@Resource
	PriceDicService priceSercice;
	@Resource
	LogOprCustomerService logOprCustomerService;



	String baseUrl = "business/record/ownertransfer/";
	
	String modulename = "客户管理-用户中心-过户记录";

    @RequestMapping("tree")
    public String tree(HttpServletRequest request)
    {
		return baseUrl+"ownertransfer-tree";
    }
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid)
	{
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("访问过户记录列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		request.setAttribute("areaid",areaid);
		return baseUrl+"ownertransfer-list";
	}
	
	
	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		OwnerModel owner = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("id", id);
			owner = ownerService.ownerInfo(map);
		}
		List<nb_price_dic> moneyList = priceSercice.getListPrices(customerId);
		List<nb_pay_dict> payList = payService.getPayAll();
		request.setAttribute("payList", payList);
		request.setAttribute("moneyList", moneyList);
		request.setAttribute("owner", owner);
		return baseUrl+"ownertransfer-input";
	}

	

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String areaid,String startdate,String enddate,String olduseraccount,String oldidcard,String newuseraccount,String newidcard)
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
			/*map.put("areacode", areaid);//区县ID
*/			
			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			
			map.put("startdate", startdate);
			map.put("enddate", enddate);			
			map.put("olduseraccount", olduseraccount);			
			map.put("oldidcard", oldidcard);
			map.put("newuseraccount", newuseraccount);			
			map.put("newidcard", newidcard);
			List<nb_owner_transferrecord> transferrecord = ownertransferService.getTransferrecord(map);
			long count = ownertransferService.getCount(map);


			PageListData<nb_owner_transferrecord> pageData = new PageListData<nb_owner_transferrecord>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(transferrecord);

			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	
}
