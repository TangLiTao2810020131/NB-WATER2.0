package com.ets.business.record.waterchange.web;

import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.equipment.entity.EquipmentModel;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.meter.meterread.service.MeterreadService;
import com.ets.business.meter.meterreadlog.service.MeterreadlogService;
import com.ets.business.owner.entity.OwnerModel;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.record.waterchange.entity.WaterChangeModel;
import com.ets.business.record.waterchange.entity.nb_watermeter_changerecord;
import com.ets.business.record.waterchange.service.WaterchangeService;
import com.ets.common.ConstantConfig;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.dictionary.watermeter.entity.nb_watermeter_dict;
import com.ets.dictionary.watermeter.service.WatermeterService;
import com.ets.system.cus.entity.nb_cus;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 水表更换
 * @author wh
 *
 */
@Controller
@RequestMapping("waterchange")
public class WaterchangeController {

	@Resource
    WaterchangeService waterchangeService;
	@Resource
    EquipmentService equipmentService;
	@Resource
    WatermeterService watermeterService;
	@Resource
    CustomerUserService customerUserService;
	@Resource
    OwnerService ownerService;
	@Resource
    MeterreadService meterreadService;
	@Resource
    MeterreadlogService meterreadlogService;
	@Resource
    LogOprCustomerService logOprCustomerService;
	
	@Resource
	RestTemplate restTemplate;

	@Resource
	ConstantConfig constantConfig;
	
	String baseUrl = "business/record/waterchange/";
	
	String modulename = "客户管理-设备管理-换表记录";
	String modulename_customer="客户管理-设备管理-水表管理";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		//将"访问换表记录列表"信息记录到操作日志
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("访问换表记录列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"waterchange-list";
	}

	
	@RequestMapping("input")
	public String input(HttpServletRequest request,String id)
	{
		Map<?,?> loginMap = (Map<?,?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cid = (String)loginMap.get("CID");
		EquipmentModel equipment = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			equipment = equipmentService.infoEquipmentM(map);
			String doorid = equipment.getDoornumid();
			map = new HashMap<String,Object>();
			map.put("doornumid", doorid);
			map.put("customerId", cid);
			OwnerModel owner = ownerService.ownerInfo(map);
			request.setAttribute("owner", owner);
		}
		List<nb_watermeter_dict> waterList = watermeterService.getWatermeterAll();//水表型号
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cid", cid);
		map.put("type", "3");
		map.put("isopen", "1");
		List<nb_customer_user> userList = customerUserService.getCustomerUsersType(map);
		request.setAttribute("waterList", waterList);
		request.setAttribute("userList", userList);
		request.setAttribute("equipment", equipment);
		return baseUrl+"waterchange-input";
	}
	
	/**
	 * 跳转到水表设备详情查看页面
	 * @param request 请求对象
	 * @param id 水表设备ID
	 * @return equipment-info.jsp
	 */
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_watermeter_changerecord changerecord = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("customerId", customerId);
			changerecord = waterchangeService.infoWaterchange(map);//查询水表设备信息

			tb_log_opr_customer log = new tb_log_opr_customer();//日志对象
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);//模块名
			log.setOprcontent("查看户号["+changerecord.getUseraccount()+"]新水表["+changerecord.getNewwatermetercode()+"]记录");//模块内容
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);//添加日志
		}
		request.setAttribute("changerecord", changerecord);
		return baseUrl+"waterchange-info";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String useraccount,String idcard)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle
			map.put("customerId", customerId);
			
			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			
			map.put("startdate", startdate);
			map.put("enddate", enddate);			
			map.put("useraccount", useraccount);			
			map.put("idcard", idcard);
			List<nb_watermeter_changerecord> Changerecord = waterchangeService.getWaterchange(map);
			long count = waterchangeService.getCount(map);


			PageListData<nb_watermeter_changerecord> pageData = new PageListData<nb_watermeter_changerecord>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(Changerecord);

			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request, WaterChangeModel c, String oldwatermetercode)
	{

		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename_customer);
		log.setOprcontent("将用户[IMEI:"+oldwatermetercode+"]水表更换为[IMEI:"+c.getWatermetercode()+"]");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);


		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			
			nb_watermeter_equipment addEquipment = setAddEquipment(c,customerId);//查询水表设备信息
			
			nb_watermeter_changerecord changerecord = setAddWaterchange(c,addEquipment, customerId);
			
			equipmentService.opentionEquipment(addEquipment);
			
			waterchangeService.addWaterchange(changerecord);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", addEquipment.getId());
			EquipmentModel equipmentDB = equipmentService.infoEquipmentM(map);//查询水表设备信息
			
			String deviceId = restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/OpentionEquipmentNBLOT.action", equipmentDB, String.class);//调用NB-IOT项目接口注册设备
			if(!"".equals(deviceId) && deviceId != null){
				addEquipment.setDeviceid(deviceId);
				equipmentService.opentionEquipment(addEquipment);
			}
			
			
			meterreadService.addMeterread(addEquipment,"-1");
			meterreadlogService.addMeterreadlog(addEquipment,"-1");
			
			String ids[] = {c.getEquipmentid()};
			equipmentService.deleteEquipment(ids);
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	
	private nb_watermeter_changerecord setAddWaterchange(WaterChangeModel c, nb_watermeter_equipment equipment, String customerId){
		nb_customer_user customerUser = customerUserService.infoCustomerUser(c.getOptionuser());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customerId", customerId);
		map.put("id", c.getOwnerid());
		OwnerModel owner = ownerService.ownerInfo(map);
		map = new HashMap<String,Object>();
		map.put("customerId", customerId);
		map.put("id", c.getEquipmentid());
		EquipmentModel equipmentDB = equipmentService.infoEquipmentM(map);//查询水表设备信息
		nb_watermeter_changerecord changerecord = new nb_watermeter_changerecord();
		changerecord.setCustomercode(customerId);
		changerecord.setAddress(owner.getAddress());
		changerecord.setUseraccount(owner.getUseraccount());
		changerecord.setUsername(owner.getUsername());
		changerecord.setOldwatermetercode(equipmentDB.getWatermetercode());
		changerecord.setOldbasenum(c.getOldbasenum());
		changerecord.setNewwatermetercode(c.getWatermetercode());
		changerecord.setNewbasenum(c.getBasenum());
		changerecord.setChangtime(c.getChangtime());
		changerecord.setOptionuser(c.getOptionuser());
		changerecord.setChangreason(c.getChangreason());
		changerecord.setPhone(customerUser.getTel());
		changerecord.setDescribe(c.getDescribe());
		return changerecord;
	}
	
	/**
	 * 填充新水表设备信息 删除注册的水表情况设备ID
	 * @param c
	 * @param equipmentDB
	 * @param customerId
	 * @return
	 */
	private nb_watermeter_equipment setAddEquipment(WaterChangeModel c, String customerId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customerId", customerId);
		map.put("id", c.getEquipmentid());
		EquipmentModel equipmentDB = equipmentService.infoEquipmentM(map);//查询水表设备信息
		nb_watermeter_equipment equipment = new nb_watermeter_equipment();
		equipment.setWatermetercode(c.getWatermetercode());
		equipment.setCustomercode(customerId);
		equipment.setBasenum(c.getBasenum());
		equipment.setWatermeterid(c.getWatermeterid());
		equipment.setInstalldate(c.getChangtime());
		equipment.setStatus(equipmentDB.getStatus());
		equipment.setDoornumid(equipmentDB.getDoornumid());
		equipment.setOptionuser(c.getOptionuser());
		equipment.setIsonline("0");
		List<String> listDeviceId = new ArrayList<String>();
		listDeviceId.add(equipmentDB.getDeviceid());
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeleteEquipmentNBLOT.action", listDeviceId, String.class);//调用NB-IOT项目接口注册设备
		return equipment;
	}
}
