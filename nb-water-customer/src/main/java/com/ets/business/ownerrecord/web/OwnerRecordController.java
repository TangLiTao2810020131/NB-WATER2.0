package com.ets.business.ownerrecord.web;

import com.ets.business.ownerrecord.entity.nb_owner_record;
import com.ets.business.ownerrecord.serivce.OwnerRecordService;
import com.ets.common.PageListData;
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
 * @author 吴浩
 * @create 2019-02-22 14:33
 */
@Controller
@RequestMapping("ownerRecord")
public class OwnerRecordController {
	
	String baseUrl = "business/ownerRecord/";
	
	@Resource
	OwnerRecordService ownerRecordService;
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request){
		return baseUrl + "ownerRecord-list";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id){
		
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);//客户ID
		map.put("id", id);
		
		nb_owner_record record = ownerRecordService.queryOwnerRecord(map);
		
		request.setAttribute("record", record);
		
		return baseUrl + "ownerRecord-info";
	}
	
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String useraccount)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		Gson gson = new Gson();
		
		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			map.put("customercode", customerId);//客户ID
			
			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			map.put("startdate", startdate);
			map.put("enddate", enddate);
			map.put("useraccount", useraccount);
			List<nb_owner_record> list= ownerRecordService.queryOwnerRecordList(map);
			long count = ownerRecordService.queryCount(map);
			
			PageListData<nb_owner_record> pageData = new PageListData<nb_owner_record>();
			
			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(list);
			
			String json = gson.toJson(pageData);
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
