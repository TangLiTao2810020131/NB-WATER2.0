package com.ets.business.statistics.owner.web;

import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.statistics.owner.entity.nb_owner_water_statistics;
import com.ets.business.statistics.owner.service.OwnerStatisticService;
import com.ets.common.DateTimeUtils;
import com.ets.common.JxlsUtils;
import com.ets.common.PageListData;
import com.ets.common.dtree.DtreeEntity;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhao
 * @create 2018- 11-20 11:15
 */
@Controller
@RequestMapping("ownerstatistic")
public class OwnerStatisticController {
	
	private String baseUrl = "business/statistics/owner/";
	
	@Resource
	OwnerStatisticService ownerStatisticService;
	
	@Resource
	OwnerService ownerService;
	
	@RequestMapping("tree")
	public String input(HttpServletRequest request)
	{
		return baseUrl + "owner-tree";
	}

	@RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public DtreeEntity tree(HttpServletRequest request, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
	{

		DtreeEntity dtreeEntity = null;

		if(level==null)
		{
			dtreeEntity = ownerStatisticService.getProvinces();
		}
		else if(level.equals("1")){
			dtreeEntity = ownerStatisticService.getCitys(parentId);
		}
		else if(level.equals("2"))
		{
			dtreeEntity = ownerStatisticService.getAreas(parentId);
		}

		return dtreeEntity;
	}
	
	
	@RequestMapping("list")
	public String list(String areaId,HttpServletRequest request,String parentId)
	{

		request.setAttribute("areaId",areaId);
		return baseUrl + "owner-list";
	}
	
	@RequestMapping("tubiao")
	public String tubiao(HttpServletRequest request,String imei)
	{
		request.setAttribute("imei", imei);
		return baseUrl + "owner-tubiao";
	}
	
	/**
	 * @Description 主页曲线图数据
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/mainLineDay")
    @ResponseBody
    public Map<String, Object> getMainLine(String imei,String stime,String etime,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imei",imei);
		map.put("stime",stime);
		map.put("etime",etime);
		
		List<nb_owner_water_statistics> list = new ArrayList<nb_owner_water_statistics>();
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		
		if(!"".equals(stime)){
			list = ownerStatisticService.queryOwnerStatisticDay(map);
		}else{
			list = ownerStatisticService.queryOwnerStatisticDay(imei);
		}
		
		for (int i = 0; i < list.size(); i++) {
			nb_owner_water_statistics ows = list.get(i);
			categories.add(ows.getCtime().split(" ")[0]);
			series.add(ows.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		return mainLine;
	}
	
	/**
	 * @Description 主页曲线图数据
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/mainLineMonth")
    @ResponseBody
    public Map<String, Object> mainLineMonth(String imei,String year,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("imei",imei);
		if("".equals(year)){
			year = String.valueOf(DateTimeUtils.getYear());
			map.put("year",String.valueOf(DateTimeUtils.getYear()));
		}else{
			map.put("year",year);
		}
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		List<nb_owner_water_statistics> list = ownerStatisticService.queryOwnerStatisticMonth(map);
		for (int i = 0; i < list.size(); i++) {
			nb_owner_water_statistics ows = list.get(i);
			categories.add(ows.getCtime());
			series.add(ows.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		mainLine.put("year", year);
		return mainLine;
	}
	
	@RequestMapping("liebiao")
	public String liebiao(HttpServletRequest request,String imei)
	{
		request.setAttribute("imei", imei);
		return baseUrl + "owner-liebiao";
	}
	
	@RequestMapping(value="listDataDay" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listDataDay(int page,int limit,String stime,String etime,String imei)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("imei",imei);
			map.put("stime",stime);
			map.put("etime",etime);
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			List<nb_owner_water_statistics> list = ownerStatisticService.queryOwnerStatisticDayMapPage(map);
			long count = ownerStatisticService.getCount(map);
			PageListData<nb_owner_water_statistics> pageData = new PageListData<nb_owner_water_statistics>();
			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(list);
			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="listDataYear" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listDataYear(int page,int limit,String year,String imei)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("imei",imei);
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			List<nb_owner_water_statistics> list = ownerStatisticService.queryOwnerStatisticMonthPage(map);
			long count = ownerStatisticService.getCountYear(map);
			PageListData<nb_owner_water_statistics> pageData = new PageListData<nb_owner_water_statistics>();
			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(list);
			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="exportDay" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void exportDay(String stime,String etime,String imei,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("imei",imei);
			map.put("customercode", customerId);//oracle
			nb_owner owner = ownerService.queryOwner(map);
			if("".equals(stime) || "".equals(etime)){
				content = "业主" + owner.getUsername() + "所有日用水量";
			}else{
				content = "业主" + owner.getUsername() + "从" + stime + "到" + etime +"日用水量";
			}
			map.put("stime",stime);
			map.put("etime",etime);
			
			List<nb_owner_water_statistics> list = null;
			
			List<nb_owner_water_statistics> listAll = new ArrayList<nb_owner_water_statistics>();
			
			long totalRecord = ownerStatisticService.getCount(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("imei",imei);
				map.put("stime",stime);
				map.put("etime",etime);
				map.put("customercode", customerId);//oracle
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = ownerStatisticService.queryOwnerStatisticDayMapPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", "业主" + owner.getUsername() + "每日用水量统计");
			JxlsUtils.exportExcel("statistics_template.xls", os, model);
			os.close();
			String fileName = content + DateTimeUtils.getTimestamp1() + ".xls";
			JxlsUtils.doDownLoad(path, fileName, response,request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="exportYear" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void exportYear(String year,String imei,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("imei",imei);
			map.put("customercode", customerId);//oracle
			nb_owner owner = ownerService.queryOwner(map);

			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			content = "业主" + owner.getUsername() + year + "年,所有月用水量";			
			long totalRecord = ownerStatisticService.getCountYear(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			List<nb_owner_water_statistics> list = null;
			
			List<nb_owner_water_statistics> listAll = new ArrayList<nb_owner_water_statistics>();
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("imei",imei);
				map.put("year",year);
				map.put("customercode", customerId);//oracle
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = ownerStatisticService.queryOwnerStatisticMonthPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", "业主" + owner.getUsername() + "每月用水量统计");
			JxlsUtils.exportExcel("statistics_template.xls", os, model);
			os.close();
			String fileName = content + DateTimeUtils.getTimestamp1() + ".xls";
			JxlsUtils.doDownLoad(path, fileName, response,request);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
