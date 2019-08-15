package com.ets.system.batch.web;

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
import com.ets.system.batch.entity.tb_sys_batch;
import com.ets.system.batch.service.BatchService;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;

/**
 * 水表批次
 * @author wh
 *
 */
@Controller
@RequestMapping("batch")
public class BatchController {

	@Resource
	BatchService batchService;
	
	@Resource
	LogOprService logService;

	String baseUrl = "system/batch/";
	
	String modulename = "水表管理-批次管理";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		List<tb_sys_batch> list = batchService.getAll();
		request.setAttribute("list", list);
		return baseUrl + "batch-list";
	}

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
		tb_sys_batch batch = null;
		if(id != null && !id.equals(""))
		{
			batch = batchService.info(id);
		}
		request.setAttribute("batch", batch);
		return baseUrl + "batch-input";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		tb_sys_batch batch = null;
		if(id != null && !id.equals(""))
		{
			batch = batchService.info(id);
		
		}
		request.setAttribute("batch", batch);
		return baseUrl + "batch-info";
	}

	/**
	 * 分页查询付费模式数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String batchname)
	{
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			map.put("batchname", batchname);//oracle

			List<tb_sys_batch> batch = batchService.getListData(map);
			long count = batchService.getCount(map);


			PageListData<tb_sys_batch> pageData = new PageListData<tb_sys_batch>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(batch);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(tb_sys_batch batch)
	{
		Gson gson = new Gson();
		try {

			batchService.opention(batch);
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		Gson gson = new Gson();
		try {
			
			batchService.delete(id);
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
}
