package com.ets.dictionary.district.web;

import com.ets.dictionary.district.entity.ProvinceListPageData;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.ProvinceService;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年11月1日 下午6:34:08
 * @version :
 * 
 */  
@Controller
@RequestMapping("province")
public class ProvinceController {

	@Resource
	ProvinceService provinceService;
	@Resource
    LogOprService logService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{

		//将用户访问"行政区域列表"信息记录到操作日志中
		tb_log_opr log=new tb_log_opr();
		log.setModulename("数据字典-行政区域");
		log.setOprcontent("查看行政区域列表");
		logService.addLog(log);

		return "dictionary/district/province-list";
	}
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		
		List<tb_province> province = provinceService.getProvinces(map);
		long count = provinceService.getCount();
		
		ProvinceListPageData pageData = new ProvinceListPageData();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(province);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	@RequestMapping("input")
	public String provinceinput(String id,HttpServletRequest request)
	{
		tb_province province = null;
		if(id!=null && !id.equals(""))
		{
			province = provinceService.infoProvince(id);
		}
		request.setAttribute("province", province);
		return "dictionary/district/province-input";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(tb_province province)
	{
		Gson gson = new Gson();
		provinceService.insetProvince(province);

		tb_log_opr log=new tb_log_opr();

		//如果区域的ID不存在则将"新增xxx区域"信息保存到操作日志,否则将"编辑xxx区域"信息保存到操作日志
		if(province.getId()==null||province.getId().equals(""))
		{
			log.setModulename("数据字典-行政区域");
			log.setOprcontent("新增"+"["+province.getProvince()+"("+province.getProvinceid()+")"+"]"+"区域");

		}else{
			log.setModulename("数据字典-行政区域");
			log.setOprcontent("编辑"+"["+province.getProvince()+"("+province.getProvinceid()+")"+"]"+"区域");
		}
		logService.addLog(log);

		return gson.toJson("操作成功");
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{

		//"删除xxx区域"信息保存到操作日志
		tb_log_opr log=new tb_log_opr();
		log.setModulename("数据字典-行政区域");
		StringBuilder sb=new StringBuilder();
		for(String str:id)
		{
			tb_province province=provinceService.infoProvince(str);
			sb.append(province.getProvince()+"("+province.getProvinceid()+")"+",");
		}
		log.setOprcontent("删除"+"["+sb.substring(0,sb.length()-1)+"]"+"区域");
		logService.addLog(log);

		provinceService.deleteProvince(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}

	@RequestMapping(value="isCkeckProvinceid" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckProvinceid(tb_province province){

		int i=provinceService.isCkeckProvinceid(province.getProvinceid());
		return i;
	}

}
