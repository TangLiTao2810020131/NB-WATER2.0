package com.ets.business.region.building.web;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.business.region.building.entity.nb_residential_building;
import com.ets.business.region.building.service.BuildingService;
import com.ets.business.region.door.entity.nb_residential_door;
import com.ets.business.region.door.service.DoorService;
import com.ets.business.region.residential.entity.ResidentialModel;
import com.ets.business.region.residential.service.ResidentialService;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.common.dtree.DtreeEntity;
import com.ets.system.cus.entity.nb_cus;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小区楼栋操作类
 * @author wh
 *
 */
@Controller
@RequestMapping("building")
public class BuildingController {

	@Resource
	OwnerService ownerService;
	@Resource
	EquipmentService equipmentService;
	@Resource
	ResidentialService residentialService;
	@Resource
	BuildingService buildingService;
	@Resource
    DoorService doorService;
	@Resource
	LogOprCustomerService logOprCustomerService;
	
	
	String baseUrl = "business/region/building/";
	String modulename = "客户管理-小区维护-小区楼栋";
	
	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String residentialid)
	{
		nb_residential_building building = null;
		if(id!=null && !id.equals(""))
		{
			building = buildingService.infoBuilding(id);
		}else{
			building = new nb_residential_building();
			building.setResidentialid(residentialid);
		}
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", residentialid);//oracle
		map.put("customercode", customerId);//oracle
		ResidentialModel residential = residentialService.infoResidentialModel(map);
		request.setAttribute("residential", residential);
		request.setAttribute("build", building);

		return baseUrl+"building-input";
	}
	
	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("访问小区楼栋维护操作页面");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"building-list";
	}
	
	@RequestMapping("buildlist")
	public String storey(HttpServletRequest request,String id,String areaid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);//oracle
		map.put("customercode", customerId);//oracle
		ResidentialModel residential = residentialService.infoResidentialModel(map);
		request.setAttribute("residential", residential);
		request.setAttribute("residentialid", id);
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("查看["+residential.getName()+"]楼栋维护列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"building-list";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id,String residentialid)
	{
		nb_residential_building building = null;
		if(id!=null && !id.equals(""))
		{
			building = buildingService.infoBuilding(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);
			log.setOprcontent("查看"+"["+building.getBuilding()+"]小区信息");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		}
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", residentialid);//oracle
		map.put("customercode", customerId);//oracle
		ResidentialModel residential = residentialService.infoResidentialModel(map);
		request.setAttribute("residential", residential);
		request.setAttribute("build", building);
		return baseUrl+"building-info";
	}
	
    @RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public DtreeEntity tree(HttpServletRequest request, String buildingid, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
    {

    	Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
    	
        DtreeEntity dtreeEntity = null;

        if(level==null)
        {
        	 dtreeEntity = doorService.getTreeDoor(customerId,buildingid);
        }
        

        return dtreeEntity;
    }
	
	
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(HttpServletRequest request,int page,int limit,String residentialid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("page", (page-1)*limit);//mysql
//		map.put("limit", limit);//mysql
		map.put("residentialid", residentialid);//小区ID
		map.put("customercode", customerId);//oracle
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle

		
		List<nb_residential_building> building = buildingService.getBuilding(map);
		long count = buildingService.getCount(map);
		
		PageListData<nb_residential_building> pageData = new PageListData<nb_residential_building>();
		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(building);
		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);

		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setModulename(modulename);
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setOprcontent("访问小区楼栋维护操作页面");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return listJson;
	}
	
	
	@RequestMapping(value="isCheckbuilding" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckbuilding(nb_residential_building building)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		int num = 0;
		try {
			if("".equals(building.getId())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("customercode", customerId);//oracle
				map.put("building", building.getBuilding().trim());//oracle
				map.put("residentialid", building.getResidentialid());//oracle
				num = buildingService.isCheckbuilding(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			num = -1;
		}
		return num;
	}
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_residential_building building)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			if(building.getId() == null || building.getId().equals(""))
			{
				log.setOprcontent("新增"+"["+building.getBuilding()+"]栋小区");
			}
			else
			{
				log.setOprcontent("修改"+"["+building.getBuilding()+"]栋小区");
			}
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
			building.setCustomercode(customerId);
			buildingService.opentionBuilding(building);
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String id[])
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			String building = buildingService.infoBuilding(id);
			
			List<String> doorIdList = new ArrayList<String>();//门牌号ID集合
			List<String> equipmentIdList = new ArrayList<String>();//水表ID集合
			List<String> ownerIdList = new ArrayList<String>();//业主ID集合
			
			for (int i = 0; i < id.length; i++) {
				Map<String, Object> doorMap = new HashMap<String, Object>();
				doorMap.put("buildingid", id[i]);
				doorMap.put("customercode",customerId);
				List<nb_residential_door> doorList = doorService.getListDoor(doorMap);
				if(doorList != null){
					for (int k = 0; k < doorList.size(); k++) {
						nb_residential_door door = doorList.get(k);
						doorIdList.add(door.getId());
						String doornumid = door.getId();
						Map<String,String> eMap = new HashMap<String, String>();
						eMap.put("doornumid", doornumid);
						eMap.put("customerId",customerId);
						List<nb_watermeter_equipment> equipmentList = equipmentService.queryEquipmentDoorId(eMap);
						List<nb_owner> ownerList = ownerService.queryOwneDoorId(eMap);
						if(equipmentList != null){
							for (int l = 0; l < equipmentList.size(); l++) {
								nb_watermeter_equipment e = equipmentList.get(l);
								equipmentIdList.add(e.getId());
							}								
						}
						if(ownerList != null){
							for (int l = 0; l < ownerList.size(); l++) {
								nb_owner o = ownerList.get(l);
								ownerIdList.add(o.getId());
							}
						}
					}
				}				
			}
			
			if(doorIdList.size() != 0){//删除小区下所有门牌号
				String[] doorId = new String[doorIdList.size()];
				doorIdList.toArray(doorId);
				doorService.deleteDoor(doorId);
				//System.out.println(doorId);
			}
			
			if(equipmentIdList.size() != 0){//删除小区下所有水表设备
				String[] equipmentId = new String[equipmentIdList.size()];
				equipmentIdList.toArray(equipmentId);
				equipmentService.deleteEquipment(equipmentId);
				//System.out.println(equipmentIdList);
			}
			
			if(ownerIdList.size() != 0){//删除小区下所有业主
				String[] ownerId = new String[ownerIdList.size()];
				ownerIdList.toArray(ownerId);
				ownerService.deleteOwner(ownerId);
				//System.out.println(ownerId);
			}
			
			buildingService.deleteBuilding(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setOprcontent("删除"+"["+building+"]栋");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
	
	@RequestMapping(value="getBuilding" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getBuilding(String residentialid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,String> map = new HashMap<String,String>();
		map.put("customercode",customerId);
		map.put("residentialid",residentialid);

		List<nb_residential_building> list = buildingService.getListBuilding(map);//小区栋号查询
		Gson gson = new Gson();
		String listJson = gson.toJson(list);
		return listJson;
	}
}
