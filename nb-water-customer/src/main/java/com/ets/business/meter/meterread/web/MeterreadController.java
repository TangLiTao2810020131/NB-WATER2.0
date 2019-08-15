package com.ets.business.meter.meterread.web;

import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.meter.meterread.entity.MeterreadModel;
import com.ets.business.meter.meterread.entity.nb_meterread;
import com.ets.business.meter.meterread.service.MeterreadService;
import com.ets.business.meter.meterreadlog.entity.nb_meterread_log;
import com.ets.business.meter.meterreadlog.service.MeterreadlogService;
import com.ets.common.DateTimeUtils;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import com.ets.system.cus.entity.nb_cus;
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


@Controller
@RequestMapping("meterread")
public class MeterreadController {


	@Resource
	MeterreadService meterreadService;
	@Resource
	CustomerUserService customerUserService;
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

	

	String baseUrl = "business/meter/meterread/";
	
	String modulename = "客户管理-抄表管理-最新抄表";

	//将"省份,城市,地区"拼接在一起
	String provinceCityArea;


	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String xqname,String building,String doornum)
	{
		Map<?,?> loginMap = (Map<?,?>) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cid = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cid", cid);
		map.put("type", "3");
		map.put("isopen", "1");
		List<nb_customer_user> userList = customerUserService.getCustomerUsersType(map);
		MeterreadModel meterread = meterreadService.meterreadIfo(id);
		request.setAttribute("meterread",meterread);
		request.setAttribute("userList", userList);
		request.setAttribute("xqname", xqname);
		request.setAttribute("building", building);
		request.setAttribute("doornum", doornum);
		return baseUrl+"meterread-input";
	}
	
    @RequestMapping("tree")
    public String tree(HttpServletRequest request)
    {
    	return baseUrl+"meterread-tree";
    }

	@RequestMapping("list")
		public String list(HttpServletRequest request,String areaid,String parentId)
		{


			tb_area area=areaService.infoArea(areaid);
			tb_city city=cityService.infoCityid(parentId);
			String proid=cityService.findProvinceIdByCityId(parentId);
			tb_province province=provinceService.infoProvinceid(proid);
			provinceCityArea="["+province.getProvince()+","+city.getCity()+","+area.getArea()+"]";


			//将查看"区域列表"信息保存到操作日志
			tb_log_opr_customer log=new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename+provinceCityArea);
			log.setOprcontent("查看区域列表");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

		request.setAttribute("areaid",areaid);
		return baseUrl+"meterread-list";
	}
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String yhname,String type,String areaid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("customercode", customerId);
		map.put("areacode", areaid);//
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
		List<MeterreadModel> meterread = meterreadService.getMeterread(map);
		long count = meterreadService.getCount(map);
		
		
		PageListData<MeterreadModel> pageData = new PageListData<MeterreadModel>();
		
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(meterread);
		
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}

	
	@RequestMapping("info")
	public String meterreadinfo(String id,HttpServletRequest request)
	{
		nb_meterread meterread = null;
		if(id!=null && !id.equals(""))
		{
		meterread = meterreadService.infoMeterread(id);

		//将查看"最新抄表列表"信息保存到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);
		log.setOprcontent("查看["+meterread.getType()+"]信息");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
	}
		request.setAttribute("meterread", meterread);
		return baseUrl+"meterread-info";
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_meterread meterread,String watermetercode,String xqname,String optionuser,String building,String doornum)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			meterread.setType("2");
			meterread.setCtime(DateTimeUtils.getnowdate());
			meterreadService.insetMeterread(meterread);
			nb_meterread_log meterreadlog = new nb_meterread_log();
			meterreadlog.setIssuccess("1");
			meterreadlog.setMessgae("");
			meterreadlog.setWatermeterid(meterread.getWatermeterid());
			meterreadlog.setOptionuser(meterread.getOptionuser());
			meterreadlog.setType("2");
			meterreadlog.setValue(meterread.getValue());
			meterreadlog.setOptiontime(meterread.getOptiontime());
			meterreadlog.setCustomercode(customerId);
			meterreadlogService.insetMeterreadlog(meterreadlog);

			tb_log_opr_customer log=new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename+provinceCityArea);
			log.setOprcontent("为[小区:"+xqname+";楼栋号:"+building+";门牌号:"+doornum+"][IMEI:"+watermetercode+"]表手动抄表");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

			return gson.toJson(new Message("1","操作成功!"));

		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(String id[])
	{
		meterreadService.deleteMeterread(id);
		Gson gson = new Gson();
		return gson.toJson("");
	}
	

}
