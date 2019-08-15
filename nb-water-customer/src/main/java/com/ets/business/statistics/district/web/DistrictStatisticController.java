package com.ets.business.statistics.district.web;

import com.ets.business.statistics.district.entity.nb_district_water_statistics;
import com.ets.business.statistics.district.service.DistrictStatisticService;
import com.ets.common.DateTimeUtils;
import com.ets.common.JxlsUtils;
import com.ets.common.PageListData;
import com.ets.common.dtree.DtreeEntity;
import com.ets.dictionary.district.entity.AreaListPageData;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.service.AreaService;
import com.ets.system.cus_region.entity.tb_cus_region_area;
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;
import com.ets.system.cus_region.service.CusRegionSerivce;
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
@RequestMapping("districtstatistic")
public class DistrictStatisticController {
	
	private String baseUrl = "business/statistics/district/";
	
	@Resource
	DistrictStatisticService districtStatisticService;
	
    @Resource
    CusRegionSerivce cusRegionSerivce;
    
	@Resource
    AreaService areaService;
	
	@RequestMapping("tree")
	public String input(HttpServletRequest request)
	{
		return baseUrl + "district-tree";
	}

	@RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public DtreeEntity tree(HttpServletRequest request, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
	{
		Map loginMap = (Map) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cusid = (String)loginMap.get("CID"); //从session里获取客户ID
		
		List<tb_cus_region_province> regionP = cusRegionSerivce.queryCusRegionProvince(cusid);
		
		List<tb_cus_region_city> regionC = cusRegionSerivce.queryCusRegionCity(cusid,regionP.get(0).getProvinceid());
		
		parentId = regionP.get(0).getProvinceid();
		
		DtreeEntity dtreeEntity = null;
		
		dtreeEntity = districtStatisticService.getCitys(parentId,regionC);

		return dtreeEntity;
	}
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String father)
	{
		Map loginMap = (Map) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cusid = (String)loginMap.get("CID"); //从session里获取客户ID
		
		List<tb_cus_region_area> regionA = cusRegionSerivce.queryCusRegionArea(cusid,father);
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		map.put("father", father);
		
		List<tb_area> areaqx = new ArrayList<tb_area>();
		List<tb_area> area = areaService.getAreaAll(map);
		
		for (tb_cus_region_area a : regionA) {
			for (com.ets.dictionary.district.entity.tb_area tb_area : area) {
				if(a.getAreaid().equals(tb_area.getId())){
					areaqx.add(tb_area);
				}
			}
		}
		
        int currentPage = page; //当前第几页数据
        
        int totalRecord = areaqx.size(); // 一共多少条记录
        
        int totalPage = totalRecord % limit; // 一共多少页
        if (totalPage > 0) {
            totalPage = totalRecord / limit + 1;
        } else {
            totalPage = totalRecord / limit;
        }
		
        currentPage = totalPage < page ? totalPage : page;
        
        int fromIndex = limit * (currentPage - 1);
        
        int toIndex = limit * currentPage > totalRecord ? totalRecord : limit * currentPage;
        
        List<tb_area> dataList = new ArrayList<tb_area>();
        dataList = areaqx.subList(fromIndex, toIndex);
        
		AreaListPageData pageData = new AreaListPageData();
		
		pageData.setCode("0");
		pageData.setCount(totalRecord);
		pageData.setMessage("");
		pageData.setData(dataList);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	
	@RequestMapping("list")
	public String list(String areaId,HttpServletRequest request,String parentId)
	{
		Map loginMap = (Map) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cusid = (String)loginMap.get("CID"); //从session里获取客户ID
		List<tb_cus_region_province> regionP = cusRegionSerivce.queryCusRegionProvince(cusid);
		List<tb_cus_region_city> regionC = cusRegionSerivce.queryCusRegionCity(cusid,regionP.get(0).getProvinceid());
		areaId = regionC.get(0).getCityid();
		request.setAttribute("areaId",areaId);
		return baseUrl + "district-list";
	}
	
	@RequestMapping("tubiao")
	public String tubiao(HttpServletRequest request,String areacode)
	{
		request.setAttribute("areacode", areacode);
		return baseUrl + "district-tubiao";
	}
	
	/**
	 * @Description 主页曲线图数据
	 * @param rootId
	 * @return
	 */
	@RequestMapping("/mainLineDay")
    @ResponseBody
    public Map<String, Object> getMainLine(String areacode,String stime,String etime,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("areacode",areacode);
		map.put("stime",stime);
		map.put("etime",etime);
		
		List<nb_district_water_statistics> list = new ArrayList<nb_district_water_statistics>();
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		
		if(!"".equals(stime)){
			list = districtStatisticService.queryDistrictStatisticDay(map);
		}else{
			list = districtStatisticService.queryDistrictStatisticDay(areacode);
		}
		
		for (int i = 0; i < list.size(); i++) {
			nb_district_water_statistics rws = list.get(i);
			categories.add(rws.getCtime());
			series.add(rws.getDegrees());
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
    public Map<String, Object> mainLineMonth(String areacode,String year,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		map.put("areacode",areacode);
		if("".equals(year)){
			year = String.valueOf(DateTimeUtils.getYear());
			map.put("year",String.valueOf(DateTimeUtils.getYear()));
		}else{
			map.put("year",year);
		}
		Map<String, Object> mainLine = new HashMap<String, Object>();
		List<String> categories = new ArrayList<String>();
		List<String> series = new ArrayList<String>();
		List<nb_district_water_statistics> list = districtStatisticService.queryDistrictStatisticMonth(map);
		for (int i = 0; i < list.size(); i++) {
			nb_district_water_statistics ows = list.get(i);
			categories.add(ows.getCtime());
			series.add(ows.getDegrees());
		}
		mainLine.put("categories", categories);
		mainLine.put("series", series);
		mainLine.put("year", year);
		return mainLine;
	}
	
	@RequestMapping("liebiao")
	public String liebiao(HttpServletRequest request,String areacode)
	{
		request.setAttribute("areacode", areacode);
		return baseUrl + "district-liebiao";
	}
	
	@RequestMapping(value="listDataDay" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listDataDay(int page,int limit,String stime,String etime,String areacode)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("areacode",areacode);
			map.put("stime",stime);
			map.put("etime",etime);
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			List<nb_district_water_statistics> list = districtStatisticService.queryDistrictStatisticDayPage(map);
			long count = districtStatisticService.getCountDay(map);
			PageListData<nb_district_water_statistics> pageData = new PageListData<nb_district_water_statistics>();
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
	public void exportDay(String stime,String etime,String areacode,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("areacode",areacode);
			map.put("stime",stime);
			map.put("etime",etime);
			map.put("customercode", customerId);//oracle
			
			tb_area area = areaService.infoArea(areacode);
			if("".equals(stime) || "".equals(etime)){
				content = area.getArea() + "所有日用水量";
			}else{
				content = area.getArea() + "从" + stime + "到" + etime +"日用水量";
			}
			
			List<nb_district_water_statistics> list = null;
			
			List<nb_district_water_statistics> listAll = new ArrayList<nb_district_water_statistics>();
			
			long totalRecord = districtStatisticService.getCountDay(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("areacode",areacode);
				map.put("stime",stime);
				map.put("etime",etime);
				map.put("customercode", customerId);//oracle
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = districtStatisticService.queryDistrictStatisticDayPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", area.getArea() + "每日用水量统计");
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
	public void exportYear(String year,String areacode,HttpServletRequest request,HttpServletResponse response)
	{
		String content = "";
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("areacode",areacode);
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			
			tb_area area = areaService.infoArea(areacode);
			
			content = area.getArea() + year + "年,所有月用水量";	
			
			long totalRecord = districtStatisticService.getCountYear(map);
			
			long totalPage = (totalRecord + 10 -1) / 10;
			
			List<nb_district_water_statistics> list = null;
			
			List<nb_district_water_statistics> listAll = new ArrayList<nb_district_water_statistics>();
			for (int i = 1; i <= totalPage; i++) {
				map = new HashMap<String,Object>();
				map.put("areacode",areacode);
				map.put("year",year);
				map.put("customercode", customerId);//oracle
				map.put("page", (i) * 10);//oracle
				map.put("limit", (i - 1) * 10);//oracle
				list = districtStatisticService.queryDistrictStatisticMonthPage(map);
				listAll.addAll(list);
			}
			
			String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/statistics_output.xls";
			OutputStream os = new FileOutputStream(path);
			Map<String , Object> model=new HashMap<String , Object>();
			model.put("ownerWaterStatistics", listAll);
			model.put("content", content);
			model.put("title", area.getArea() + "每月用水量统计");
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
	public String listDataYear(int page,int limit,String year,String areacode)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("areacode",areacode);
			if("".equals(year)){
				year = String.valueOf(DateTimeUtils.getYear());
				map.put("year", year);
			}else{
				map.put("year",year);
			}
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			
			List<nb_district_water_statistics> list = districtStatisticService.queryDistrictStatisticMonthPage(map);
			long count = districtStatisticService.getCountYear(map);
			PageListData<nb_district_water_statistics> pageData = new PageListData<nb_district_water_statistics>();
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
	
}
