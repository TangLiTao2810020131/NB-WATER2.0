package com.ets.system.cus_quartzlog.web;

import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.cus_quartzlog.entity.qrtz_triggers;
import com.ets.system.cus_quartzlog.service.QuartzService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("quartzlogcontroller")
public class QuartzController {
	
	@Resource
	QuartzService quartzService;

	String baseUrl = "system/cus_quartzlog/";
	
	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		return baseUrl + "quarzlog-list";
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

			List<qrtz_triggers> triggers = quartzService.getQrtzTriggers(map);
			long count = quartzService.getCount();


			PageListData<qrtz_triggers> pageData = new PageListData<qrtz_triggers>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(triggers);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
}
