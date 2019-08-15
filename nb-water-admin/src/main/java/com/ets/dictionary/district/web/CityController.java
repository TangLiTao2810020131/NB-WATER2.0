package com.ets.dictionary.district.web;

import com.ets.dictionary.district.entity.CityListPageData;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.CityService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月2日 上午11:11:08
 * @version :
 * 
 */
@Controller
@RequestMapping("city")
public class CityController {

	@Resource
    CityService cityService;
	@Resource
    LogOprService logService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,String father)
	{
		//将"查看地市列表"信息保存到操作日志
		tb_province province=cityService.findProvinceByCode(father);
		tb_log_opr log=new tb_log_opr();
		log.setModulename("数据字典-行政区域-设置地市");
		log.setOprcontent("查看"+province.getProvince()+"地市列表");
		logService.addLog(log);

		//将省份信息保存在session中
		HttpSession session=request.getSession();
		session.setAttribute("provinceSession",province);

		request.setAttribute("father", father);
		return "dictionary/district/city-list";
	}
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String father)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		map.put("father", father);
		
		List<tb_city> city = cityService.getCitys(map);
		long count = cityService.getCount(father);
		
		
		CityListPageData pageData = new CityListPageData();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(city);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	@RequestMapping("input")
	public String cityinput(String id,String father, HttpServletRequest request)
	{
		tb_city city = null;
		if(id!=null && !id.equals(""))
		{
			city = cityService.infoCity(id);
		}
		request.setAttribute("city", city);
		request.setAttribute("father", father);
		return "dictionary/district/city-input";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(tb_city city,HttpServletRequest request)
	{
		Gson gson = new Gson();
		cityService.insetCity(city);

		//从session中拿到省份信息
		HttpSession session=request.getSession();
		tb_province province=(tb_province)session.getAttribute("provinceSession");

		tb_log_opr log=new tb_log_opr();
		log.setModulename("数据字典-行政区域-设置地市");
		//如果城市的id不存在,则将"新增城市xxx"信息保存到操作日志,否则将"编辑城市xxx"信息保存到操作日志
		if(city.getId()==null||city.getId().equals(""))
		{
			log.setOprcontent("为["+province.getProvince()+"]新增城市["+city.getCity()+"]");
		}else {

			log.setOprcontent("编辑["+province.getProvince()+"]["+city.getCity()+"]信息");
		}
		logService.addLog(log);

		return gson.toJson("操作成功");
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[],HttpServletRequest request)
	{
		//从session中拿到省份信息
		HttpSession session=request.getSession();
		tb_province province=(tb_province)session.getAttribute("provinceSession");

		//将"删除[xxx,xxx]城市信息"保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("数据字典-行政区域-设置地市");
		StringBuilder sb=new StringBuilder();
		for(String str:id)
		{
			tb_city city=new tb_city();
			sb.append(city.getCity()+",");
		}
		log.setOprcontent("删除["+province.getProvince()+"]所属的城市["+sb.substring(0,sb.length()-1)+"]");
		logService.addLog(log);

		cityService.deleteCity(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}
	
	
	@RequestMapping(value="getCity" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCity(String father)
	{
		List<tb_city> city = cityService.getListCitys(father);//市级对象
		Gson gson = new Gson();
		String listJson = gson.toJson(city);
		return listJson;
	}
	@RequestMapping(value="isCkeckCityid" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckCityid(tb_city city){
		return  cityService.isCkeckCityid(city.getCityid());
	}
	
}
