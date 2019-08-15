package com.ets.business.equipment.web;

import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.equipentrecord.entity.nb_watermeter_record;
import com.ets.business.equipentrecord.service.EquipmentRecordService;
import com.ets.business.equipment.entity.EquipmentModel;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.meter.meterread.service.MeterreadService;
import com.ets.business.meter.meterreadlog.service.MeterreadlogService;
import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.ownerrecord.entity.nb_owner_record;
import com.ets.business.ownerrecord.serivce.OwnerRecordService;
import com.ets.business.region.building.entity.nb_residential_building;
import com.ets.business.region.building.service.BuildingService;
import com.ets.business.region.door.entity.nb_residential_door;
import com.ets.business.region.door.service.DoorService;
import com.ets.business.region.residential.entity.nb_residential_init;
import com.ets.business.region.residential.service.ResidentialService;
import com.ets.common.ConstantConfig;
import com.ets.common.DateTimeUtils;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.common.UUIDUtils;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
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
 * 水表设备控制类
 * @author wuhao
 *
 */
@Controller
@RequestMapping("equipment")
public class EquipmentController {
	
	@Resource
	OwnerRecordService ownerRecordService;
	@Resource
	EquipmentRecordService equipmentRecordService;
	@Resource
	CustomerUserService customerUserService;
	@Resource
    AreaService areaService;
	@Resource
    CityService cityService;
	@Resource
	ResidentialService residentialService;
	@Resource
	BuildingService buildingailsService;
	@Resource
    DoorService doorService;
	@Resource
    ProvinceService provinceService;
	@Resource
    WatermeterService watermeterService;
	@Resource
	EquipmentService equipmentService;
	@Resource
	MeterreadService meterreadService;
	@Resource
	MeterreadlogService meterreadlogService;
	@Resource
	LogOprCustomerService logOprCustomerService;
	@Resource
	OwnerService ownerService;
	@Resource
	RestTemplate restTemplate;

	@Resource
	ConstantConfig constantConfig;
	
	String baseUrl = "business/equipment/";

	String modulename = "客户管理-设备管理-水表管理";
	
	
    @RequestMapping("tree")
    public String tree(HttpServletRequest request)
    {
    	//访问用户管理列表
		tb_log_opr_customer log = new tb_log_opr_customer();
        log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("访问用户管理列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
    	return baseUrl+"equipment-tree";
    }
	

	/**
	 * 跳转到水表设备列表页面
	 * @return equipment-list.jsp
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid,String parentId)
	{

		//将"访问水表管理列表"信息保存到操作日志
		tb_log_opr_customer log = new tb_log_opr_customer();//日志对象
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);//模块名
		log.setOprcontent("访问水表管理列表");//模块内容
		request.setAttribute("areaid",areaid);
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);//添加日志
		return baseUrl+"equipment-list";
	}


	/**
	 * 跳转到水表设备新增或修改页面
	 * @param request 请求对象
	 * @param id 水表设备ID
	 * @return equipment-input.jsp
	 */
	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String areaid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_watermeter_equipment equipment = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("customercode", customerId);
			equipment = equipmentService.infoEquipment(map);//查询水表设备信息
			String doorId = equipment.getDoornumid();//门牌号ID
			this.editEquipment(request, doorId);
		}
		this.setEquipmentInFo(request);
		request.setAttribute("equipment", equipment);
		
		Map<String,String> mapAre = new HashMap<String,String>();
		mapAre.put("customerId",customerId);
		mapAre.put("areaid",areaid);

		List<nb_residential_init> rlist = residentialService.getListResidential(mapAre);
		request.setAttribute("rlist", rlist);
		
		return baseUrl+"equipment-input";
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
		EquipmentModel equipment = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("customercode", customerId);
			equipment = equipmentService.infoEquipmentM(map);//查询水表设备信息

			tb_log_opr_customer log = new tb_log_opr_customer();//日志对象
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);//模块名
			log.setOprcontent("查看IMEI:"+"["+equipment.getWatermetercode()+"]水表");//模块内容
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);//添加日志
		}
		request.setAttribute("equipment", equipment);
		return baseUrl+"equipment-info";
	}

	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String startdate,String enddate,String watermetercode,String status,String isonline,String areaid)
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
			map.put("customercode", customerId);
			map.put("areacode", areaid);//区县ID
			
			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			
			map.put("startdate", startdate);
			map.put("enddate", enddate);
			map.put("watermetercode", watermetercode);
			map.put("status", status);
			map.put("isonline", isonline);
			List<nb_watermeter_equipment> equipment = equipmentService.getEquipment(map);//水表设备列表集合
			long count = equipmentService.getCount(map);//水表设备总条数
			PageListData<nb_watermeter_equipment> pageData = new PageListData<nb_watermeter_equipment>();//分业集合对象
			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(equipment);
			String listJson = gson.toJson(pageData);//转换为JSON字符串
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="isCheckIMEI" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckIMEI(nb_watermeter_equipment equipment){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			int num = 0;
			if("".equals(equipment.getId())){
				Map<String,Object> map = new HashMap<String,Object>();
				//map.put("customercode", customerId);//客户ID
				map.put("watermetercode", equipment.getWatermetercode());//户号
				num = equipmentService.isCheckIMEI(map);
			}
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@RequestMapping(value="isCheckWaterNum" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public boolean isCheckWaterNum(nb_watermeter_equipment equipment){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		String maxNum =  (String)loginMap.get("MAXNUM");
		boolean fig = true;
		try {
			int num = 0;
			if("".equals(equipment.getId())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("customercode", customerId);//客户ID
				num = equipmentService.isCheckWaterNum(map);
				if(Integer.valueOf(num) > Integer.valueOf(maxNum)){
					fig = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fig;
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_watermeter_equipment equipment,String control,String type)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");//获取登录用户对象
		String customerId = (String)loginMap.get("CID");//用户ID
		equipment.setCustomercode(customerId);//绑定登录用户ID
		
		Gson gson = new Gson();
		try {
			tb_log_opr_customer log = new tb_log_opr_customer();//日志对象
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);//模块
			if(equipment.getId() == null || equipment.getId().equals(""))
			{
				log.setOprcontent("新增IMEL"+"["+equipment.getWatermetercode()+"]水表");//日志内容
			}
			else
			{
				log.setOprcontent("修改IMEL"+"["+equipment.getWatermetercode()+"]水表");//日志内容
			}
			if(control.equals("0")){
				equipment.setStatus("-1");
			}
			equipment.setIsonline("0");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);//添加日志
			
			equipmentService.opentionEquipment(equipment);//添加或修改水表设备对象
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", equipment.getId());
			EquipmentModel equipmentDB = equipmentService.infoEquipmentM(map);//查询水表设备信息
			
			String deviceId = restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/OpentionEquipmentNBLOT.action", equipmentDB, String.class);//调用NB-IOT项目接口注册设备
			//String deviceId = OpentionEquipmentNBLOT(equipmentDB);
			if(!"".equals(deviceId) && deviceId != null){
				equipment.setDeviceid(deviceId);
				equipmentService.opentionEquipment(equipment);
			}else{
				String[] id = {equipment.getId()};
				equipmentService.deleteEquipment(id);
				return gson.toJson(new Message("3","注册设备失败!"));
			}
			
			if("add".equals(type)){
				//添加设备注册记录
				AddEquipment(equipment);
				meterreadService.addMeterread(equipment,"0");
				meterreadlogService.addMeterreadlog(equipment,"0");
			}
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}


	private void AddEquipment(nb_watermeter_equipment equipment){
		nb_watermeter_record record  = new nb_watermeter_record();
		
		//查询同一门牌号下的业主相关信息，
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", equipment.getCustomercode());
		map.put("doornumid", equipment.getDoornumid());
		nb_owner owner = ownerService.infoOwner(map);
		String doornumid = "";
		if(owner != null){
			//查询是否有业主信息，若投则同步相关信息
			record.setUseraccount(owner.getUseraccount());
			record.setUsername(owner.getUsername());
			doornumid = owner.getDoornumid();
			
			map = new HashMap<String,Object>();
			map.put("customercode", equipment.getCustomercode());
			map.put("ownerid", owner.getId());
			map.put("doornumid", equipment.getDoornumid());
			//查询同一门牌号下的业主信息，若有则同步更新业主设备唯一号
			nb_owner_record ownerRecord = ownerRecordService.queryOwnerRecord(map);
			if(ownerRecord != null){
				ownerRecord.setWatermetercode(equipment.getWatermetercode());
				ownerRecordService.editOwnerRecord(ownerRecord);
			}
		}else{
			//若无则在新建业主的时候，更新并同步该设备记录
			record.setUseraccount("");
			record.setUsername("");
			doornumid = equipment.getDoornumid();
		}
		
		record.setId(UUIDUtils.getUUID());
		record.setCustomercode(equipment.getCustomercode());
		record.setWatermeterid(equipment.getId());
		record.setWatermetercode(equipment.getWatermetercode());
		
		nb_watermeter_dict dict = watermeterService.infoWatermeter(equipment.getWatermeterid());
		String watermeter = (dict.getType() + "(口径:" + dict.getCaliber());
		if("1".equals(dict.getMagnetism())){
			watermeter += ",有磁,";
		}else{
			watermeter += ",无磁,";
		}
		if("1".equals(dict.getControl())){
			watermeter += "有阀控)";
		}else{
			watermeter += "无阀控)";
		}
		record.setWatermeter(watermeter);
		record.setBasenum(equipment.getBasenum());
		
		nb_customer_user c = customerUserService.infoCustomerUser(equipment.getOptionuser());
		
		record.setOptionuser(c.getRealname());
		
		nb_residential_door door = doorService.infoDoor(doornumid);
		String buildingId = door.getBuildingid();
		
		nb_residential_building build = buildingailsService.infoBuilding(buildingId);
		String residentialId = build.getResidentialid();
		
		nb_residential_init residential = residentialService.infoResidential(residentialId);
		
		String provinceId = residential.getProvincecode();
		String cityId = residential.getCitycode();
		String areaId = residential.getAreacode();
		
		tb_area area = areaService.infoArea(areaId);
		tb_city city = cityService.infoCity(cityId);
		tb_province province = provinceService.infoProvince(provinceId);
		
		record.setAddress(province.getProvince() + city.getCity() + area.getArea() + residential.getName() + build.getBuilding() + "栋" + door.getDoornum() + "室");
		
		record.setInstalldate(equipment.getInstalldate());
		record.setDoornumid(equipment.getDoornumid());
		record.setCtime(DateTimeUtils.getnowdate());
		
		equipmentRecordService.addEquipmentRecord(record);
	}

	
	/**
	 * 删除水表设备对象
	 * @param id 水表设备对象ID集合
	 * @return JSON字符串
	 */
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String id[])
	{
		Gson gson = new Gson();
		try {
			
			List<nb_watermeter_equipment> list = equipmentService.infoEquipmentDeviceId(id);
			
			List<String> listDeviceId = new ArrayList<String>();
			
			List<String> doorIds=new ArrayList<String>();

			for (nb_watermeter_equipment equipment : list) {
				listDeviceId.add(equipment.getDeviceid());
				doorIds.add(equipment.getDoornumid());
			}
			
			restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeleteEquipmentNBLOT.action", listDeviceId, String.class);//调用NB-IOT项目接口注册设备
			String info=ownerService.infoUser(doorIds.toArray(new String[]{}));
			String watermetercode = equipmentService.infoEquipment(id);//根据水表设备ID查询集合数据
			equipmentService.deleteEquipment(id);//删除水表设备对象

			tb_log_opr_customer log = new tb_log_opr_customer();//日志
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);//模块
			if(info!=null)
			{
				log.setOprcontent("删除IMEI:"+"["+watermetercode+"]关联用户:"+"["+info+"]");//模块内容
			}else{
				log.setOprcontent("删除IMEI:"+"["+watermetercode+"],无关联用户!");//模块内容
			}
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);//添加日志
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
	
	
	/**
	 * 设置省份，水表型号列表
	 * @param request
	 */
	private void setEquipmentInFo(HttpServletRequest request){
		Map<?,?> loginMap = (Map<?,?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cid = (String)loginMap.get("CID");
		List<nb_watermeter_dict> waterList = watermeterService.getWatermeterAll();//水表型号
		List<tb_province> pList = provinceService.getTreeProvince();//省份集合
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cid", cid);
		map.put("type", "3");
		map.put("isopen", "1");
		List<nb_customer_user> userList = customerUserService.getCustomerUsersType(map);
		request.setAttribute("userList", userList);
		request.setAttribute("pList", pList);
		request.setAttribute("waterList", waterList);
	}
	
	/**
	 * 查询水表设备相关数据
	 * @param request
	 * @param doorId
	 */
	private void editEquipment(HttpServletRequest request,String doorId){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		nb_residential_door door = doorService.infoDoor(doorId);//门牌号对象
		String buildingId = door.getBuildingid();//小区栋ID
		nb_residential_building build = buildingailsService.infoBuilding(buildingId);//小区栋对象
		String residentialId = build.getResidentialid();//小区ID
		nb_residential_init residential = residentialService.infoResidential(residentialId);//小区对象
		String provinceId = residential.getProvincecode();//省份ID
		String cityId = residential.getCitycode();//市区ID
		String areaId = residential.getAreacode();//区（县）ID
		tb_province p = provinceService.infoProvince(provinceId);//省份对象
		tb_city c = cityService.infoCity(cityId);//市区对象
		List<tb_city> cityList = cityService.getListCitys(p.getProvinceid());//市区对象集合
		List<tb_area> areaList = areaService.getListByfather(c.getCityid());//区县对象集合


		Map<String, String> mapAre = new HashMap<String,String>();
		mapAre.put("customerId",customerId);
		mapAre.put("areaid",areaId);
		List<nb_residential_init> rlist = residentialService.getListResidential(mapAre);

		Map<String, String> mapRes = new HashMap<String,String>();
		mapRes.put("customercode",customerId);
		mapRes.put("residentialid",residentialId);


		List<nb_residential_building> bList = buildingailsService.getListBuilding(mapRes);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		map.put("buildingid", buildingId);
		List<nb_residential_door> dList = doorService.getListDoor(map);//门牌号集合
		request.setAttribute("provinceId", p.getProvinceid());
		request.setAttribute("cityId", c.getCityid());
		request.setAttribute("areaId", areaId);
		request.setAttribute("cityList", cityList);
		request.setAttribute("areaList", areaList);
		request.setAttribute("buildingId", buildingId);
		request.setAttribute("residentialId", residentialId);
		request.setAttribute("rlist", rlist);
		request.setAttribute("bList", bList);
		request.setAttribute("dList", dList);	
	}
}
