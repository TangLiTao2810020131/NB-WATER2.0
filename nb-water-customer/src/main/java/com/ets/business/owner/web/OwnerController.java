package com.ets.business.owner.web;

import com.ets.business.equipentrecord.entity.nb_watermeter_record;
import com.ets.business.equipentrecord.service.EquipmentRecordService;
import com.ets.business.equipment.entity.EquipmentModel;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.init.price.entity.nb_price_dic;
import com.ets.business.init.price.service.PriceDicService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.owner.entity.OwnerModel;
import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.ownerrecord.entity.nb_owner_record;
import com.ets.business.ownerrecord.serivce.OwnerRecordService;
import com.ets.business.record.ownertransfer.entity.nb_owner_transferrecord;
import com.ets.business.record.ownertransfer.service.OwnertransferService;
import com.ets.business.region.building.entity.nb_residential_building;
import com.ets.business.region.building.service.BuildingService;
import com.ets.business.region.door.entity.nb_residential_door;
import com.ets.business.region.door.service.DoorService;
import com.ets.business.region.residential.entity.nb_residential_init;
import com.ets.business.region.residential.service.ResidentialService;
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
import com.ets.dictionary.pay.entity.nb_pay_dict;
import com.ets.dictionary.pay.service.PayService;
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

/**
 * 户主操作类
 * @author wh
 *
 */
@Controller
@RequestMapping("owner")
public class OwnerController {
	
	@Resource
	EquipmentService equipmentService;
	@Resource
    OwnertransferService transferrecordService;
	@Resource
    OwnerRecordService ownerRecordService;
	@Resource
	EquipmentRecordService equipmentRecordService;
	@Resource
    PayService payService;
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
    PriceDicService priceSercice;

	@Resource
    ProvinceService provinceService;
	@Resource
    OwnerService ownerService;
	@Resource
    LogOprCustomerService logOprCustomerService;

	String baseUrl = "business/owner/";
	
	String modulename = "客户管理-用户中心-用户管理";

	//将"省份,城市,地区"拼接在一起
	String provinceCityArea;
	
	
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
    	return baseUrl+"owner-tree";
    }

	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid,String parentId)
	{
		setUserInFo(request);

		tb_area area=areaService.infoArea(areaid);
		tb_city city=cityService.infoCityid(parentId);
		String proid=cityService.findProvinceIdByCityId(parentId);
		tb_province province=provinceService.infoProvinceid(proid);
		provinceCityArea="["+province.getProvince()+","+city.getCity()+","+area.getArea()+"]";


		tb_log_opr_customer log = new tb_log_opr_customer();
        log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);
		log.setOprcontent("访问区域列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		request.setAttribute("areaid",areaid);
		return baseUrl+"owner-list";
	}

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String areaid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_owner owner = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("id", id);
			owner = ownerService.infoOwner(map);
			//getEditUserInfo(request, owner.getDoornumid());
		}
		setUserInFo(request);

		Map<String,String> mapAre = new HashMap<String,String>();
		mapAre.put("customerId",customerId);
		mapAre.put("areaid",areaid);

		List<nb_residential_init> rlist = residentialService.getListResidential(mapAre);
		request.setAttribute("rlist", rlist);
		request.setAttribute("owner", owner);
		return baseUrl+"owner-input";
	}
	
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		OwnerModel owner = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("id", id);
			owner = ownerService.ownerInfo(map);

			tb_log_opr_customer log = new tb_log_opr_customer();
            log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename+provinceCityArea);
			log.setOprcontent("查看"+"["+owner.getUsername()+"]用户详情");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		}
		request.setAttribute("owner", owner);
		return baseUrl+"owner-info";
	}

	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String areaid,String startdate,String enddate,String useraccount,String userunitprice,String operationmode)
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
			map.put("customercode", customerId);//客户ID
			map.put("areacode", areaid);//区县ID
			
			if(startdate != null && !"".equals(startdate)){
				startdate += " 00:00:00";
			}
			
			if(enddate != null && !"".equals(enddate)){
				enddate += " 23:59:59";
			}
			
			map.put("startdate", startdate);//客户ID
			map.put("enddate", enddate);//客户ID
			map.put("useraccount", useraccount);//客户ID
			map.put("userunitprice", userunitprice);//客户ID
			map.put("operationmode", operationmode);//客户ID
			List<nb_owner> owner = ownerService.getOwner(map);
			long count = ownerService.getCount(map);


			PageListData<nb_owner> pageData = new PageListData<nb_owner>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(owner);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	@RequestMapping(value="isCkeckUserAccount" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckUserAccount(nb_owner owner){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			int num = 0;
			if("".equals(owner.getId())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("customercode", customerId);//客户ID
				map.put("useraccount", owner.getUseraccount());//户号
				num = ownerService.isCkeckUserAccount(map);
			}
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	//检测过户的用户的户号的唯一性
	@RequestMapping(value="isCkeckTransferUserAccount" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckTransferUserAccount(nb_owner owner){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);//客户ID
			map.put("useraccount", owner.getUseraccount());//户号
			return  ownerService.isCkeckUserAccount(map);

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@RequestMapping(value="isCkeckIdNum" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCkeckIdNum(nb_owner owner){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		try {
			int num = 0;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);//客户ID
			map.put("idnum", owner.getIdnum());//身份证号
			num = ownerService.isCkeckUserAccount(map);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request, nb_owner owner, String type)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		owner.setCustomercode(customerId);
		Gson gson = new Gson();
		try {

			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
            log.setIp(logOprCustomerService.getIp(request));//ip地址
			if(owner.getId() == null || owner.getId().equals(""))
			{
				log.setOprcontent("新增"+"[户名:" + owner.getUsername()+"][户号:"+owner.getUseraccount()+"]用户信息");
			}
			else
			{
				log.setOprcontent("修改"+"[户名:"+owner.getUsername()+"][户号:"+owner.getUseraccount()+"]用户信息");
			}
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

			
			ownerService.opentionOwner(owner);
			if("add".equals(type)){
				//添加业主记录
				AddOwnerRecord(owner);
			}

			
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}
	
	/**
	 * 实例化业主记录实体类
	 * @param owner 业主实体类
	 * @return 业主记录实体类
	 */
	private void AddOwnerRecord(nb_owner owner){
		nb_owner_record ownerRecord = new nb_owner_record();
		
		//查询同一门牌号下的设备,若存在则得到设备唯一号,并更新到业主记录内
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", owner.getCustomercode());
		map.put("doornumid", owner.getDoornumid());
		EquipmentModel equipment = equipmentService.infoEquipmentM(map);
		if(equipment != null){
			//查询是否有设备若有则得到设备唯一号
			ownerRecord.setWatermetercode(equipment.getWatermetercode());
			
			map = new HashMap<String,Object>();
			map.put("customercode", owner.getCustomercode());
			map.put("doornumid", owner.getDoornumid());
			map.put("watermeterid", equipment.getId());
			//查询同一门牌号下的设备记录,若有则同步其该设备的业主相关信息
			nb_watermeter_record record = equipmentRecordService.queryEquipmentRecord(map);
			if(record != null){
				record.setUseraccount(owner.getUseraccount());
				record.setUsername(owner.getUsername());
				equipmentRecordService.editEquipmentRecord(record);
			}
		}else{
			//若无则在新建设备的时候更新该业主记录并更新其设备唯一号
			ownerRecord.setWatermetercode("");
		}
		
		ownerRecord.setOwnerid(owner.getId());
		ownerRecord.setId(UUIDUtils.getUUID());
		ownerRecord.setCustomercode(owner.getCustomercode());
		ownerRecord.setUseraccount(owner.getUseraccount());
		ownerRecord.setUsername(owner.getUsername());
		
		nb_pay_dict pay = payService.infoPay(owner.getOperationmode());
		ownerRecord.setOperationmode(pay.getPaymode());
		
		nb_price_dic price = priceSercice.infoPrice(owner.getUserunitprice());
		ownerRecord.setUserunitprice(price.getType() + "-(" + price.getPrice() + "元/吨))");
		
		ownerRecord.setIdnum(owner.getIdnum());
		ownerRecord.setPhone(owner.getPhone());
		
		nb_residential_door door = doorService.infoDoor(owner.getDoornumid());
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
		
		ownerRecord.setAddress(province.getProvince() + city.getCity() + area.getArea() + residential.getName() + build.getBuilding() + "栋" + door.getDoornum() + "室");
		ownerRecord.setDoornumid(owner.getDoornumid());
		ownerRecord.setCtime(DateTimeUtils.getnowdate());
		ownerRecordService.addOwnerRecord(ownerRecord);
	}
	
	@RequestMapping(value="savetransfer" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String savetransfer(HttpServletRequest request, nb_owner owner)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", customerId);
			map.put("id", owner.getId());
			OwnerModel ownerDB = ownerService.ownerInfo(map);
			owner.setCustomercode(customerId);
			owner.setDoornumid(ownerDB.getDoornumid());
			addtransferrecord(ownerDB, owner);
			//ownerService.opentionOwner(owner);
            //过户更新
            ownerService.transferOwner(owner);

			tb_log_opr_customer log = new tb_log_opr_customer();
            log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename+provinceCityArea);
			log.setOprcontent("为[户名:"+owner.getUsername()+"][户号:"+owner.getUseraccount()+"]新用户过户");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

			return gson.toJson(new Message("1","过户成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","过户失败!"));
		}
	}	
	
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String id[])
	{
		Gson gson = new Gson();
		try {
			String username = ownerService.infoOwner(id);
			ownerService.deleteOwner(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
            log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename+provinceCityArea);
			log.setOprcontent("删除"+"["+username+"]用户信息");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
	
	/**
	 * 添加过户记录
	 * @param ownerDB 原户主信息
	 * @param owner 新户主信息
	 */
	private void addtransferrecord(OwnerModel ownerDB, nb_owner owner){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customercode", owner.getCustomercode());
			map.put("doornumid", owner.getDoornumid());
			EquipmentModel equipment = equipmentService.infoEquipmentM(map);
			nb_owner_transferrecord transferrecord = new nb_owner_transferrecord();
			transferrecord.setCustomercode(owner.getCustomercode());
			transferrecord.setOlduseraccount(ownerDB.getUseraccount());
			transferrecord.setOldusername(ownerDB.getUsername());
			transferrecord.setNewuseraccount(owner.getUseraccount());
			transferrecord.setNewuaername(owner.getUsername());
			transferrecord.setWatermetercode(equipment.getWatermetercode());
			transferrecord.setTransferread(equipment.getBasenum());
			transferrecord.setAddress(ownerDB.getAddress());
			transferrecordService.addTransferrecord(transferrecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置收费类型，用户单击列表
	 * @param request
	 */
	private void setUserInFo(HttpServletRequest request){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		
		List<nb_price_dic> moneyList = priceSercice.getListPrices(customerId);
		List<nb_pay_dict> payList = payService.getPayAll();
		request.setAttribute("payList", payList);
		request.setAttribute("moneyList", moneyList);
	}
	
	/**
	 * 查询用户相关信息
	 * @param request 
	 * @param doorId
	 *//*
	private void getEditUserInfo(HttpServletRequest request,String doorId){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");

		nb_residential_door door = doorService.infoDoor(doorId);
		String buildingId = door.getBuildingid();
		nb_residential_building build = buildingailsService.infoBuilding(buildingId);
		String residentialId = build.getResidentialid();
		nb_residential_init residential = residentialService.infoResidential(residentialId);
		String provinceId = residential.getProvincecode();
		String cityId = residential.getCitycode();
		String areaId = residential.getAreacode();
		tb_province p = provinceService.infoProvince(provinceId);
		tb_city c = cityService.infoCity(cityId);
		List<tb_city> cityList = cityService.getListCitys(p.getProvinceid());//市级对象
		List<tb_area> areaList = areaService.getListByfather(c.getCityid());//市级对象

		Map mapAre = new HashMap();
		mapAre.put("customerId",customerId);
		mapAre.put("areaid",areaId);
		List<nb_residential_init> rlist = residentialService.getListResidential(mapAre);

		Map mapRes = new HashMap();
		mapAre.put("customerId",customerId);
		mapAre.put("residentialId",residentialId);

		List<nb_residential_building> bList = buildingailsService.getListBuilding(mapRes);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		map.put("buildingid", buildingId);
		List<nb_residential_door> dList = doorService.getListDoor(map);
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
	}*/
}
