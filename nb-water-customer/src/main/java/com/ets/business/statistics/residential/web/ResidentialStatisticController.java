package com.ets.business.statistics.residential.web;

import com.ets.business.region.building.entity.nb_residential_building;
import com.ets.business.region.building.service.BuildingService;
import com.ets.business.region.residential.entity.nb_residential_init;
import com.ets.business.region.residential.service.ResidentialService;
import com.ets.business.statistics.residential.entity.LdModel;
import com.ets.business.statistics.residential.entity.nb_residential_water_statistics;
import com.ets.business.statistics.residential.service.ResidentialStatisticService;
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
@RequestMapping("residentialstatistic")
public class ResidentialStatisticController {
	
	private String baseUrl = "business/statistics/residential/";
	
	@Resource
    ResidentialStatisticService residentialStatisticService;
	
	@Resource
    ResidentialService residentialService;
	
	@Resource
    BuildingService buildingService;
	
	@RequestMapping("tree")
	public String input(HttpServletRequest request)
	{
		return baseUrl + "residential-tree";
	}

	@RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public DtreeEntity tree(HttpServletRequest request, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
	{

		DtreeEntity dtreeEntity = null;

		if(level==null)
		{
			dtreeEntity = residentialStatisticService.getProvinces();
		}
		else if(level.equals("1")){
			dtreeEntity = residentialStatisticService.getCitys(parentId);
		}
		else if(level.equals("2"))
		{
			dtreeEntity = residentialStatisticService.getAreas(parentId);
		}

		return dtreeEntity;
	}
	
	
	@RequestMapping("list")
	public String list(String areaId,HttpServletRequest request,String parentId)
	{

		request.setAttribute("areaId",areaId);
		return baseUrl + "residential-list";
	}
	
	@RequestMapping("tubiao")
	public String tubiao(HttpServletRequest request,String id)
	{
		request.setAttribute("id",id);
		return baseUrl + "residential-tubiao";
	}


	/**
	 * 主页曲线图数据
	 * @param id 小区ID
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param session
	 * @return
	 */
	@RequestMapping("/mainLineDay")
    @ResponseBody
    public Map<String, Object> getMainLine(String id,String stime,String etime,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",id);
		map.put("stime",stime);
		map.put("etime",etime);
		
		List<nb_residential_water_statistics> list = new ArrayList<nb_residential_water_statistics>();
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		
		if(!"".equals(stime)){
			list = residentialStatisticService.queryResidentialStatisticDay(map);
		}else{
			list = residentialStatisticService.queryResidentialStatisticDay(id);
		}
		
		for (int i = 0; i < list.size(); i++) {
			nb_residential_water_statistics rws = list.get(i);
			categories.add(rws.getCtime());
			series.add(rws.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		return mainLine;
	}

	/**
	 * 主页曲线图数据
	 * @param id 小区ID
	 * @param year 查询年份 默认当前年份
	 * @param session
	 * @return
	 */
	@RequestMapping("/mainLineMonth")
    @ResponseBody
    public Map<String, Object> mainLineMonth(String id,String year,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",id);
		if("".equals(year)){
			year = String.valueOf(DateTimeUtils.getYear());
			map.put("year",String.valueOf(DateTimeUtils.getYear()));
		}else{
			map.put("year",year);
		}
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		List<nb_residential_water_statistics> list = residentialStatisticService.queryResidentialStatisticMonth(map);
		for (int i = 0; i < list.size(); i++) {
			nb_residential_water_statistics ows = list.get(i);
			categories.add(ows.getCtime());
			series.add(ows.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		mainLine.put("year", year);
		return mainLine;
	}
	
	@RequestMapping("liebiao")
	public String liebiao(HttpServletRequest request,String id)
	{
		request.setAttribute("id",id);
		return baseUrl + "residential-liebiao";
	}
	
	@RequestMapping(value="listDataDay" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listDataDay(int page,int limit,String stime,String etime,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("id",id);
			map.put("stime",stime);
			map.put("etime",etime);
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			List<nb_residential_water_statistics> list = residentialStatisticService.queryResidentialStatisticDayPage(map);
			long count = residentialStatisticService.getCountDay(map);
			PageListData<nb_residential_water_statistics> pageData = new PageListData<nb_residential_water_statistics>();
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
	public void exportDay(String stime,String etime,String id,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("id",id);
			map.put("customercode", customerId);//oracle
			nb_residential_init residential = residentialService.infoResidential(id);
			if("".equals(stime) || "".equals(etime)){
				content = residential.getName() + "小区所有日用水量";
			}else{
				content = residential.getName() + "小区从" + stime + "到" + etime +"日用水量";
			}
			
			map.put("stime",stime);
			map.put("etime",etime);
			
			List<nb_residential_water_statistics> list = null;
			
			List<nb_residential_water_statistics> listAll = new ArrayList<nb_residential_water_statistics>();
			
			long totalRecord = residentialStatisticService.getCountDay(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("id",id);
				map.put("stime",stime);
				map.put("etime",etime);
				map.put("customercode", customerId);
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = residentialStatisticService.queryResidentialStatisticDayPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", residential.getName() + "小区每日用水量统计");
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
	public void exportYear(String year,String id,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("id",id);
			nb_residential_init residential = residentialService.infoResidential(id);
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			
			content = residential.getName() + "小区" + year + "年,所有月用水量";		
			
			long totalRecord = residentialStatisticService.getCountYear(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			List<nb_residential_water_statistics> list = null;
			
			List<nb_residential_water_statistics> listAll = new ArrayList<nb_residential_water_statistics>();
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("id",id);
				map.put("year",year);
				map.put("customercode", customerId);//oracle
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = residentialStatisticService.queryResidentialStatisticMonthPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", residential.getName() + "小区每月用水量统计");
			JxlsUtils.exportExcel("statistics_template.xls", os, model);
			os.close();
			String fileName = content + DateTimeUtils.getTimestamp1() + ".xls";
			JxlsUtils.doDownLoad(path, fileName, response,request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="listDataYear" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listDataYear(int page,int limit,String year,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("id",id);
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			List<nb_residential_water_statistics> list = residentialStatisticService.queryResidentialStatisticMonthPage(map);
			long count = residentialStatisticService.getCountYear(map);
			PageListData<nb_residential_water_statistics> pageData = new PageListData<nb_residential_water_statistics>();
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
	
	@RequestMapping("ldlist")
	public String ldlist(HttpServletRequest request,String id)
	{
		request.setAttribute("id",id);
		return baseUrl + "loudong-list";
	}

	@RequestMapping(value="ldlistData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String ldlistData(HttpServletRequest request, int page, int limit, String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("id", id);//小区ID
		map.put("customercode", customerId);//oracle
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle


		List<LdModel> ldData = residentialStatisticService.getLDStatistic(map);
		long count = residentialStatisticService.getLDCount(map);

		PageListData<LdModel> pageData = new PageListData<LdModel>();
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(ldData);
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);

		return listJson;
	}

	@RequestMapping("ldliebiao")
	public String ldliebiao(HttpServletRequest request,String id)
	{
		request.setAttribute("id",id);
		return baseUrl + "loudong-liebiao";
	}

	@RequestMapping(value="ldlistDataDay" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String ldlistDataDay(int page,int limit,String stime,String etime,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("ldid",id);
			map.put("stime",stime);
			map.put("etime",etime);
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			List<nb_residential_water_statistics> list = residentialStatisticService.getLDStatisticDayPage(map);
			long count = residentialStatisticService.getLDCountDay(map);
			PageListData<nb_residential_water_statistics> pageData = new PageListData<nb_residential_water_statistics>();
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
	
	@RequestMapping(value="ldExportDay" ,produces = "application/json; charset=utf-8")
	public void ldExportDay(String stime,String etime,String id,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("ldid",id);
			nb_residential_building b = buildingService.infoBuilding(id);
			nb_residential_init residential = residentialService.infoResidential(b.getResidentialid());
			
			if("".equals(stime) || "".equals(etime)){
				content = residential.getName() + "小区," + b.getBuilding() + "栋日用水量";
			}else{
				content = residential.getName() + "小区," + b.getBuilding() + "栋,从" + stime + "到" + etime +"日用水量";
			}
			map.put("stime",stime);
			map.put("etime",etime);
			map.put("customercode", customerId);//oracle
			
			List<nb_residential_water_statistics> list = null;
			
			List<nb_residential_water_statistics> listAll = new ArrayList<nb_residential_water_statistics>();
			
			long totalRecord = residentialStatisticService.getLDCountDay(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("ldid",id);
				map.put("stime",stime);
				map.put("etime",etime);
				map.put("customercode", customerId);
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = residentialStatisticService.getLDStatisticDayPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", residential.getName() + "小区," + b.getBuilding() + "栋,每日用水量统计");
			JxlsUtils.exportExcel("statistics_template.xls", os, model);
			os.close();
			String fileName = content + DateTimeUtils.getTimestamp1() + ".xls";
			JxlsUtils.doDownLoad(path, fileName, response,request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="ldExportYear" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void ldExportYear(String year,String id,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("ldid",id);
			
			nb_residential_building b = buildingService.infoBuilding(id);
			nb_residential_init residential = residentialService.infoResidential(b.getResidentialid());
			
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			
			content = residential.getName() + "小区," + b.getBuilding() + "栋," + year + "年,所有月用水量";	
			
			List<nb_residential_water_statistics> list = null;
			
			List<nb_residential_water_statistics> listAll = new ArrayList<nb_residential_water_statistics>();
			
			long totalRecord =  residentialStatisticService.getLDCountYear(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("ldid",id);
				map.put("year",year);
				map.put("customercode", customerId);
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = residentialStatisticService.getLDStatisticMonthPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", residential.getName() + "小区," + b.getBuilding() + "栋,每月用水量统计");
			JxlsUtils.exportExcel("statistics_template.xls", os, model);
			os.close();
			String fileName = content + DateTimeUtils.getTimestamp1() + ".xls";
			JxlsUtils.doDownLoad(path, fileName, response,request);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="ldlistDataYear" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String ldlistDataYear(int page,int limit,String year,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("ldid",id);
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			List<nb_residential_water_statistics> list = residentialStatisticService.getLDStatisticMonthPage(map);
			long count = residentialStatisticService.getLDCountYear(map);
			PageListData<nb_residential_water_statistics> pageData = new PageListData<nb_residential_water_statistics>();
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

	@RequestMapping("ldtubiao")
	public String ldtubiao(HttpServletRequest request,String id)
	{
		request.setAttribute("id",id);
		return baseUrl + "loudong-tubiao";
	}

	/**
	 * 楼栋曲线图数据
	 * @param id 楼栋id
	 * @param stime 开始时间
	 * @param etime 结束时间
	 * @param session
	 * @return
	 */
	@RequestMapping("/ldLineDay")
	@ResponseBody
	public Map<String, Object> ldLineDay(String id,String stime,String etime,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ldid",id);
		map.put("stime",stime);
		map.put("etime",etime);

		List<nb_residential_water_statistics> list = new ArrayList<nb_residential_water_statistics>();
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();

		if(!"".equals(stime)){
			list = residentialStatisticService.getLDStatisticDay(map);
		}else{
			list = residentialStatisticService.getLDStatisticDay(id);
		}

		for (int i = 0; i < list.size(); i++) {
			nb_residential_water_statistics rws = list.get(i);
			categories.add(rws.getCtime());
			series.add(rws.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		return mainLine;
	}

	/**
	 * 楼栋曲线图数据
	 * @param id 楼栋ID
	 * @param year 查询年份 默认当前年份
	 * @param session
	 * @return
	 */
	@RequestMapping("/ldLineMonth")
	@ResponseBody
	public Map<String, Object> ldLineMonth(String id,String year,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ldid",id);
		if("".equals(year)){
			year = String.valueOf(DateTimeUtils.getYear());
			map.put("year",String.valueOf(DateTimeUtils.getYear()));
		}else{
			map.put("year",year);
		}
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		List<nb_residential_water_statistics> list = residentialStatisticService.getLDStatisticMonth(map);
		for (int i = 0; i < list.size(); i++) {
			nb_residential_water_statistics ows = list.get(i);
			categories.add(ows.getCtime());
			series.add(ows.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		mainLine.put("year", year);
		return mainLine;
	}
}
