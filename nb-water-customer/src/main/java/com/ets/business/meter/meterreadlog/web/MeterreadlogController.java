package com.ets.business.meter.meterreadlog.web;

import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.meter.meterreadlog.entity.MeterreadLogModel;
import com.ets.business.meter.meterreadlog.entity.nb_meterread_log;
import com.ets.business.meter.meterreadlog.service.MeterreadlogService;
import com.ets.common.DateTimeUtils;
import com.ets.common.JxlsUtils;
import com.ets.common.PageListData;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("meterreadlog")
public class MeterreadlogController {



	@Resource
	MeterreadlogService meterreadlogService;
	@Resource
	LogOprCustomerService logOprCustomerService;
	@Resource
    AreaService areaService;
	@Resource
    CityService cityService;
	@Resource
    ProvinceService provinceService;
	

	String baseUrl = "business/meter/meterreadlog/";
	
	String modulename = "客户管理-抄表管理-抄表记录";

	//将"省份,城市,地区"拼接在一起
	String provinceCityArea;
	
	
    @RequestMapping("tree")
    public String tree(HttpServletRequest request)
    {
		return baseUrl+"meterreadlog-tree";
    }
	
	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid,String parentId)
	{
		tb_area area=areaService.infoArea(areaid);
		tb_city city=cityService.infoCityid(parentId);
		String proid=cityService.findProvinceIdByCityId(parentId);
		tb_province province=provinceService.infoProvinceid(proid);
		provinceCityArea="["+province.getProvince()+","+city.getCity()+","+area.getArea()+"]";

		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);
		log.setOprcontent("查看区域列表");
		logOprCustomerService.addLog(log);

		request.setAttribute("areaid",areaid);
		return baseUrl+"meterreadlog-list";
	}
	
	@RequestMapping("historylist")
	public String historylist(HttpServletRequest request,String equipmentid,String xqname,String building,String doornum)
	{
		//将查看"抄表历史记录"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename("客户管理-抄表管理-最新抄表[历史抄表记录]");
		log.setOprcontent("查看[小区:"+xqname+";楼栋号:"+building+";门牌号:"+doornum+"]历史抄表记录");
		logOprCustomerService.addLog(log);
		request.setAttribute("equipmentid",equipmentid);
		return baseUrl+"meterreadlog-history";
	}
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String yhname,String type,String areaid,String equipmentid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("customercode", customerId);
		map.put("areacode", areaid);//
		map.put("equipmentid", equipmentid);//
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		
		if(startdate != null && !"".equals(startdate)){
			startdate += " 00:00:00";
		}
		
		if(enddate != null && !"".equals(enddate)){
			enddate += " 23:59:59";
		}
		
		map.put("startdate", startdate);//
		map.put("enddate", enddate);//
		map.put("yhname", yhname);//
		map.put("type", type);//

		
		List<MeterreadLogModel> meterreadlog = meterreadlogService.getMeterreadlog(map);
		long count = meterreadlogService.getCount(map);
		
		
		PageListData<MeterreadLogModel> pageData = new PageListData<MeterreadLogModel>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(meterreadlog);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}
	
	@RequestMapping("input")
	public String meterreadinput(String id,HttpServletRequest request)
	{
		nb_meterread_log meterreadlog = null;
		if(id!=null && !id.equals(""))
		{
			meterreadlog = meterreadlogService.infoMeterreadlog(id);
		}
		request.setAttribute("meterreadlog", meterreadlog);
		return baseUrl+"meterreadlog-input";
	}
	
	@RequestMapping("info")
	public String meterreadinfo(String id,HttpServletRequest request)
	{
		nb_meterread_log meterread = null;
		if(id!=null && !id.equals(""))
		{
			meterread = meterreadlogService.infoMeterreadlog(id);
		}
		request.setAttribute("meterread", meterread);
		return baseUrl+"meterreadlog-info";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(nb_meterread_log meterreadlog)
	{
		Gson gson = new Gson();
		meterreadlog.setCtime(DateTimeUtils.getnowdate());
		meterreadlogService.insetMeterreadlog(meterreadlog);
		return gson.toJson("操作成功");
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		meterreadlogService.deleteMeterreadlog(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}
	

	/**
	 * 导出报表
	 * @return
	 */
	@RequestMapping(value="export" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void export(HttpServletRequest request,HttpServletResponse response,String startdate,String enddate,String yhname,String type,String areaid,String equipmentid) throws Exception {

		tb_area area = areaService.infoArea(areaid);
		tb_city city = cityService.infoCityid(area.getFather());
		tb_province province = provinceService.infoProvinceid(city.getFather());

		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("customercode", customerId);
		map.put("areacode", areaid);//
		
		if(startdate != null && !"".equals(startdate)){
			startdate += " 00:00:00";
		}else{
			startdate = null;
		}
		
		if(enddate != null && !"".equals(enddate)){
			enddate += " 23:59:59";
		}else{
			enddate = null;
		}
		
		if("".equals(yhname)){
			yhname = null;
		}
		
		if("".equals(type)){
			type = null;
		}
		map.put("startdate", startdate);//
		map.put("enddate", enddate);//
		map.put("yhname", yhname);//
		map.put("type", type);//

		
		List<MeterreadLogModel> meterreadlog = meterreadlogService.getMeterreadlogEX(map);
		String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/meter_reading_record_output.xls";
		OutputStream os = new FileOutputStream(path);
		Map<String , Object> model=new HashMap<String , Object>();
		model.put("meterreadlog", meterreadlog);
		model.put("nowdate", new Date());
		JxlsUtils.exportExcel("meter_reading_record_template.xls", os, model);
		os.close();
		String fileName = province.getProvince() + city.getCity() + area.getArea() + DateTimeUtils.getTimestamp1() + "抄表记录" + ".xls";
		JxlsUtils.doDownLoad(path, fileName, response,request);

		/*	
		tb_area area = areaService.infoArea(areaid);
		tb_city city = cityService.infoCityid(area.getFather());
		tb_province province = provinceService.infoProvinceid(city.getFather());

		//获取数据
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customerCode", customerId);
		map.put("areacode", areaid);


		map.put("balancemonth", balancemonth); 
		map.put("useraccount", useraccount); 

		List<nb_balance_dict> list = balanceService.getBalanceExport(map);

		//excel标题
		String[] title = {"户号","户名","起始读数","起始读表日期","截至读数","截至读表日期","结算月份","用水量"};

		String [][] content = new String[list.size()][];

		//excel文件名
		String fileName = province.getProvince() + "-" + city.getCity() + "-" + area.getArea() + "-抄表结算" + DateTimeUtils.getnowdate() + ".xls";

		//sheet名
		String sheetName = province.getProvince() + "-" + city.getCity() + "-" + area.getArea();

		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			nb_balance_dict obj = list.get(i);
			content[i][0] = obj.getUseraccount();
			content[i][1] = obj.getUsername();
			content[i][2] = obj.getStartnum();
			content[i][3] = obj.getStartnumdate();
			content[i][4] = obj.getEndnum();
			content[i][5] = obj.getEndnumdate();
			content[i][6] = obj.getBalancemonth();
			double waternum = Double.valueOf(obj.getEndnum()) - Double.valueOf(obj.getStartnum());
			content[i][7] = String.valueOf(waternum);

		}

		//创建HSSFWorkbook 
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);*/

		//响应到客户端
		/*try {
			this.setResponseHeader(response, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
