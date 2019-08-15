package com.ets.business.region.door.web;

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
 * 小区维护门牌号
 * @author wh
 *
 */
@Controller
@RequestMapping("door")
public class DoorController {

	@Resource
    ResidentialService residentialService;
	@Resource
	BuildingService buildingService;
	@Resource
    DoorService doorService;
	@Resource
	EquipmentService equipmentService;
	@Resource
    OwnerService ownerService;
	@Resource
	LogOprCustomerService logOprCustomerService;

	String baseUrl = "business/region/door/";
	String modulename = "客户管理-小区维护-小区门牌号维护";

	@RequestMapping("list")
	public String list(HttpServletRequest request)
	{
		return baseUrl+"door-list";
	}
	
	@RequestMapping("doorlist")
	public String zhuzhai(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		nb_residential_building building = buildingService.infoBuilding(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", building.getResidentialid());//oracle
		map.put("customercode", customerId);//oracle
		ResidentialModel residential = residentialService.infoResidentialModel(map);
		request.setAttribute("residential", residential);
		request.setAttribute("build", building);
		request.setAttribute("buildid", id);

		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("查看["+residential.getName()+"]小区["+building.getBuilding()+"]栋管理页面");
		logOprCustomerService.addLog(log);
		return baseUrl+"door-list";
	}

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String buildid)
	{
		nb_residential_door door = null;

		if(id!=null && !id.equals(""))
		{
			door = doorService.infoDoor(id);
		}else{
			door = new nb_residential_door();
			door.setBuildingid(buildid);
		}
		this.setInitRegion(request, door);
		request.setAttribute("door", door);
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename);
		log.setOprcontent("访问小区门牌号维护操作页面");
		logOprCustomerService.addLog(log);
		return baseUrl+"door-input";
	}
	
	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		nb_residential_door door = null;
		if(id!=null && !id.equals(""))
		{
			door = doorService.infoDoor(id);
			nb_residential_building building = buildingService.infoBuilding(door.getBuildingid());
			Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
			String customerId = (String)loginMap.get("CID");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", building.getResidentialid());//oracle
			map.put("customercode", customerId);//oracle
			ResidentialModel residential = residentialService.infoResidentialModel(map);
			request.setAttribute("residential", residential);
			request.setAttribute("build", building);
			request.setAttribute("door", door);

			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);
			log.setOprcontent("查看["+residential.getName()+"]小区["+building.getBuilding()+"]栋["+door.getDoornum()+"]门牌号信息");
			logOprCustomerService.addLog(log);
		}
		return baseUrl+"door-info";
	}

	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String buildid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("buildid", buildid);//oracle
			map.put("customercode", customerId);//oracle
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle

			List<nb_residential_door> door = doorService.getDoor(map);
			long count = doorService.getCount(map);


			PageListData<nb_residential_door> pageData = new PageListData<nb_residential_door>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(door);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	
	@RequestMapping(value="isCheckDoor" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckDoor(nb_residential_door door)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		int num = 0;
		try {
			if("".equals(door.getId())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("customercode", customerId);//oracle
				map.put("doornum",door.getDoornum().trim());//oracle
				map.put("buildingid",  door.getBuildingid());//oracle
				num = doorService.isCheckDoor(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			num = -1;
		}
		return num;
	}
	
	
	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_residential_door door)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setModulename(modulename);
			if(door.getId() == null || door.getId().equals(""))
			{
				log.setOprcontent("新增["+door.getDoornum()+"]门牌号");
			}
			else
			{
				log.setOprcontent("修改["+door.getDoornum()+"]门牌号");
			}
			logOprCustomerService.addLog(log);
			door.setCustomercode(customerId);
			doorService.opentionDoor(door);
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
			String Doornum = doorService.infoDoor(id);
			
			List<String> equipmentIdList = new ArrayList<String>();//水表ID集合
			List<String> ownerIdList = new ArrayList<String>();//业主ID集合
			
			for (int i = 0; i < id.length; i++) {
				Map<String,String> eMap = new HashMap<String, String>();
				eMap.put("doornumid", id[i]);
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
			
			doorService.deleteDoor(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename);
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setOprcontent("删除["+Doornum+"]门牌号");
			logOprCustomerService.addLog(log);
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
	
	
	@RequestMapping(value="getOwnerDoor" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getOwnerDoor(String buildingid)
	{
		//查询该楼栋下已经入住小区的门牌号
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		map.put("buildingid", buildingid);
		List<String> doorIDlist = doorService.getListOwnerDoor(map);
		//查询该楼栋下所有的门牌号
		List<nb_residential_door> doorList = doorService.getListDoor(map);//门派号对象
		if(doorIDlist != null){
			for (int i = 0; i < doorIDlist.size(); i++) {
				String id = doorIDlist.get(i);
				for (int j = 0; j < doorList.size(); j++) {
					if(id.equals(doorList.get(j).getId())){
						doorList.remove(doorList.get(j));//若是有已住小区门派则删除并不显示于下拉列表
						break;
					}
				}
			}
		}

		Gson gson = new Gson();
		String listJson = gson.toJson(doorList);
		return listJson;
	}
	
	@RequestMapping(value="getEquipmentDoor" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getEquipmentDoor(String buildingid)
	{
		//查询该楼栋下已经入住小区设置水表的门牌号
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", customerId);
		map.put("buildingid", buildingid);
		List<String> doorIDlist = doorService.getListEquipmentDoor(map);
		//查询该楼栋下所有的门牌号
		List<nb_residential_door> doorList = doorService.getListDoor(map);//门派号对象
		if(doorIDlist != null){
			for (int i = 0; i < doorIDlist.size(); i++) {
				String id = doorIDlist.get(i);
				for (int j = 0; j < doorList.size(); j++) {
					if(id.equals(doorList.get(j).getId())){
						doorList.remove(doorList.get(j));//若是有已住小区门派则删除并不显示于下拉列表
						break;
					}
				}
			}
		}

		Gson gson = new Gson();
		String listJson = gson.toJson(doorList);
		return listJson;
	}

	
	/**
	 * 区域信息
	 * @param request
	 * @param door
	 */
	private void setInitRegion(HttpServletRequest request,nb_residential_door door){
		nb_residential_building building = null;
		ResidentialModel residential = null;
		building = buildingService.infoBuilding(door.getBuildingid());//查询小区栋对象
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", building.getResidentialid());//oracle
		map.put("customercode", customerId);//oracle
		residential = residentialService.infoResidentialModel(map);//校训小区对象
		request.setAttribute("residential", residential);
		request.setAttribute("build", building);
	}
}
