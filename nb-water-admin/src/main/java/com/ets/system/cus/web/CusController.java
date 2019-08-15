package com.ets.system.cus.web;

import com.ets.common.DateTimeUtils;
import com.ets.common.PageListData;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus.service.CusService;
import com.ets.system.cus_region.entity.tb_cus_region_area;
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;
import com.ets.system.cus_region.service.CusRegionSerivce;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
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
 * @create 2019-01-22 20:23
 */
@Controller
@RequestMapping("cusController")
public class CusController {

	String baseUrl = "system/cus/";

	@Resource
	ProvinceService provinceService;

	@Autowired
	CityService cityService;

	@Autowired
	AreaService areaService;

	@Resource
	CusService cusService;

	@Resource
	CusRegionSerivce cusRegionSerivce;

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{

		return baseUrl +"cus-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String customerName,String customerStatus)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
		//		map.put("page", (page-1)*limit);//mysql
		//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		map.put("startdate", startdate);//
		map.put("enddate", enddate);//
		map.put("customerName", customerName);//
		map.put("customerStatus", customerStatus);//

		List<nb_cus> customer = cusService.getCustomers(map);
		long count = cusService.getCount();


		PageListData<nb_cus> pageData = new PageListData<nb_cus>();

		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(customer);

		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}

	@RequestMapping("input")
	public String customerinput(String id, HttpServletRequest request)
	{
		nb_cus customer = null;
		if(id!=null && !id.equals(""))
		{
			customer = cusService.infoCustomer(id);
		}
		request.setAttribute("customer", customer);

		return baseUrl +"cus-input";
	}

	@RequestMapping("info")
	public String customerinfo(String id,HttpServletRequest request)
	{
		nb_cus customer = null;
		if(id!=null && !id.equals(""))
		{
			customer = cusService.infoCustomer(id);

		}
		request.setAttribute("customer", customer);
		return baseUrl +"cus-info";
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(nb_cus customer)
	{
		Gson gson = new Gson();
		customer.setCtime(DateTimeUtils.getnowdate());
		if("".equals(customer.getCustomerStatus()) || customer.getCustomerStatus() == null){
			customer.setCustomerStatus("0");
		}

		cusService.insetCustomer(customer);


		return gson.toJson("操作成功");
	}

	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		cusService.deleteCustomer(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}
	//验证客户名称的唯一性
	@RequestMapping(value="isCheckCustomerName" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public long isCheckCustomerName(nb_cus customer){
		return cusService.isCheckCustomerName(customer.getCustomerName());

	}


	@RequestMapping(value="inRegion" )
	public String inResource(HttpServletRequest request , String id)
	{
		List<tb_province> provinceList = provinceService.getTreeProvince();
		request.setAttribute("cusid", id);
		request.setAttribute("pList", provinceList);
		return baseUrl +"cus-inputRegion";
	}

	@RequestMapping(value="saveInRegion" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveInRegion(String province,String city,String area,String cusid)
	{
		
		//首先查询已经分配的区域，若有则删除，若无则不做任何操作
		cusRegionSerivce.deleteRegion(cusid);

		tb_cus_region_province regionP = new tb_cus_region_province();
		regionP.setCusid(cusid);
		regionP.setProvinceid(province);
		cusRegionSerivce.addCusRegionProvince(regionP);
		
		tb_cus_region_city regionC = new tb_cus_region_city();
		regionC.setCusid(cusid);
		regionC.setCityid(city);
		regionC.setProvinceid(province);
		cusRegionSerivce.addCusRegionCity(regionC);

		tb_cus_region_area regionA = null;
		if("all".equals(area)){
			List<tb_area> areaList =  areaService.getListArea(city);
			for (tb_area tb_area : areaList) {
				regionA = new tb_cus_region_area();
				regionA.setCusid(cusid);
				regionA.setAreaid(tb_area.getId());
				regionA.setCityid(city);
				cusRegionSerivce.addCusRegionArea(regionA);
			}

		}else{
			regionA = new tb_cus_region_area();
			regionA.setCusid(cusid);
			regionA.setAreaid(area);
			regionA.setCityid(city);
			cusRegionSerivce.addCusRegionArea(regionA);
		}
		Gson gson = new Gson();


		return gson.toJson("操作成功");
	}

}
