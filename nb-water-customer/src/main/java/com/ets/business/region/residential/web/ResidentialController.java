package com.ets.business.region.residential.web;

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
import com.ets.business.region.residential.entity.nb_residential_init;
import com.ets.business.region.residential.service.ResidentialService;
import com.ets.common.EleTree;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.common.dtree.DtreeEntity;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import com.ets.system.log.opr.service.LogOprService;
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
 * 小区维护操作类
 * @author WH
 *
 */
@Controller
@RequestMapping("residential")
public class ResidentialController {
	
	@Resource
	OwnerService ownerService;
	@Resource
	EquipmentService equipmentService;
	@Resource
	ResidentialService residentialService;
	@Resource
    ProvinceService provinceService;
	@Resource
    CityService cityService;
	@Resource
    AreaService areaService;
	@Resource
    LogOprService logService;
	@Resource
	BuildingService buildingService;
	@Resource
    DoorService doorService;
	@Resource
	LogOprCustomerService logOprCustomerService;



	String baseUrl = "business/region/residential/";
	String modulename = "客户管理-小区维护";

	//将"省份,城市,地区"拼接在一起
	String provinceCityArea;

	
    @RequestMapping("tree")
    public String input(HttpServletRequest request)
    {
    	return baseUrl+"residential-tree";
    }

	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String areaid)
	{
		nb_residential_init residential = null;
		if(areaid!=null && !areaid.equals(""))
		{
			residential = new nb_residential_init();
			residential.setAreacode(areaid);
		}

		if(id!=null && !id.equals(""))
		{
			residential = residentialService.infoResidential(id);
		}
		this.setInitRegion(request,areaid);
		request.setAttribute("residential", residential);


		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);
		log.setOprcontent("查看"+residential.getName());
		logOprCustomerService.addLog(log);

		return baseUrl+"residential-input";
	}

	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid,String parentId)
	{

		tb_area area=areaService.infoArea(areaid);
		tb_city city=cityService.infoCityid(parentId);
		String proid=cityService.findProvinceIdByCityId(parentId);
		tb_province province=provinceService.infoProvinceid(proid);
		provinceCityArea="["+province.getProvince()+","+city.getCity()+","+area.getArea()+"]";


		tb_log_opr_customer log=new tb_log_opr_customer();//操作日志对象
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);//模块名称
		log.setOprcontent("查看区域列表");//操作内容
		logOprCustomerService.addLog(log);//加入日志内容
		request.setAttribute("areaid",areaid);
		return baseUrl+"residential-list";
	}

	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{	
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		ResidentialModel residential = null;
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);//oracle
			map.put("customercode", customerId);//oracle
			residential = residentialService.infoResidentialModel(map);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setOprcontent("查看"+"["+residential.getName()+"]小区信息");
			logOprCustomerService.addLog(log);
		}
		request.setAttribute("residential", residential);
		return baseUrl+"residential-info";
	}
	
    @RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
    @ResponseBody
    public DtreeEntity tree(HttpServletRequest request, String residentialid, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
    {

    	Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
    	
        DtreeEntity dtreeEntity = null;

        if(level==null)
        {
            dtreeEntity = buildingService.getTreeBuilding(customerId,residentialid);
        }
        else if(level.equals("1")){
            dtreeEntity = doorService.getTreeDoor(customerId,parentId);
        }

        return dtreeEntity;
    }
	

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String areaid)
	{
		Gson gson = new Gson();
		try {
			PageListData<nb_residential_init> pageData = null;
			if("0".equals(areaid)){
				pageData = new PageListData<nb_residential_init>();
				//初始化小区设置点击区县areaid为空返回空数据
				String listJson = gson.toJson(pageData);
				return listJson;
			}else{
				Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
				String customerId = (String)loginMap.get("CID");

				//System.out.println("page="+page+",limit="+limit);
				Map<String,Object> map = new HashMap<String,Object>();
				//				map.put("page", (page-1)*limit);//mysql
				//				map.put("limit", limit);//mysql
				map.put("areaid", areaid);//oracle
				map.put("customercode", customerId);//oracle
				map.put("page", (page)*limit);//oracle
				map.put("limit", (page-1)*limit);//oracle

				List<nb_residential_init> residential = residentialService.getResidential(map);
				long count = residentialService.getCount(map);

				pageData = new PageListData<nb_residential_init>();
				pageData.setCode("0");
				pageData.setCount(count);
				pageData.setMessage("");
				pageData.setData(residential);
				String listJson = gson.toJson(pageData);
				return listJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}

	}

	@RequestMapping(value="isCheckName" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckName(nb_residential_init residential)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		int num = 0;
		try {
			if("".equals(residential.getId())){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("customercode", customerId);//oracle
				map.put("name", residential.getName().trim());//oracle
				map.put("areacode", residential.getAreacode());//oracle
				num = residentialService.isCheckName(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			num = -1;
		}
		return num;
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_residential_init residential)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {

			String areaid = residential.getAreacode();
			tb_area area = areaService.infoArea(areaid);//县级对象
			tb_city city = cityService.infoCityid(area.getFather());//市级对象
			tb_province province = provinceService.infoProvinceid(city.getFather());//省级对象
			residential.setCitycode(city.getId());
			residential.setProvincecode(province.getId());
			residential.setCustomercode(customerId);
			residentialService.opentionResidential(residential);


			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			if(residential.getId() == null || residential.getId().equals(""))
			{
				log.setOprcontent("新增["+residential.getName()+"]小区");
			}
			else
			{
				log.setOprcontent("修改"+"["+residential.getName()+"]小区信息");
			}
			logOprCustomerService.addLog(log);

			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	
	/**
	 * 初始化省份，市区
	 * @return
	 */
	@RequestMapping(value="initTreelist" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String initTreelist()
	{
		List<EleTree> eTreep = new ArrayList<EleTree>();//省份树形菜单集合
		List<tb_province> pList = provinceService.getTreeProvince();//查询省份集合
		for (com.ets.dictionary.district.entity.tb_province tb_province : pList) {
			EleTree eTree = new EleTree();
			String idp = tb_province.getProvinceid();
			String labelp = tb_province.getProvince();
			List<tb_city> city = cityService.getListCitys(idp);//根据省份编码查询市区集合
			List<EleTree> eTreeList = new ArrayList<EleTree>();//市区树形菜单集合
			for (com.ets.dictionary.district.entity.tb_city tb_city : city) {
				EleTree eTreec = new EleTree();
				String idc = tb_city.getCityid();
				String labelc = tb_city.getCity();
				eTreec = new EleTree();
				eTreec.setId(idc);
				eTreec.setLabel(labelc);
				eTreec.setIsLeaf(false);
				eTreeList.add(eTreec);
			}
			eTree.setId(idp);
			eTree.setLabel(labelp);
			eTree.setChildren(eTreeList);
			eTree.setIsLeaf(false);
			eTreep.add(eTree);
		}
		Gson gson = new Gson();
		String listJson = gson.toJson(eTreep);
		return listJson;
	}

	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String id[])
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			String name = residentialService.infoResidential(id);
			
			List<String> buildIdList = new ArrayList<String>();//栋号ID集合
			List<String> doorIdList = new ArrayList<String>();//门牌号ID集合
			List<String> equipmentIdList = new ArrayList<String>();//水表ID集合
			List<String> ownerIdList = new ArrayList<String>();//业主ID集合
			
			for (int i = 0; i < id.length; i++) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("residentialid", id[i]);
				map.put("customercode",customerId);
				List<nb_residential_building> buildList = buildingService.getListBuilding(map);
				if(buildList != null){
					for (int j = 0; j < buildList.size(); j++) {
						nb_residential_building build = buildList.get(j);
						buildIdList.add(build.getId());
						Map<String, Object> doorMap = new HashMap<String, Object>();
						doorMap.put("buildingid", build.getId());
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
				} 
			}
			
			if(buildIdList.size() != 0){//删除小区下所有楼栋号
				String[] buildId = new String[buildIdList.size()];
				buildIdList.toArray(buildId);
				buildingService.deleteBuilding(buildId);
				//System.out.println(buildId);
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
			
			residentialService.deleteResidential(id);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
			log.setIp(logOprCustomerService.getIp(request));//ip地址
			log.setOprcontent("删除,"+"["+name+"]小区");
			logOprCustomerService.addLog(log);
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}

	@RequestMapping(value="getResidential" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getResidential(String areaid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Map<String,String> map = new HashMap<String,String>();
		map.put("customerId",customerId);
		map.put("areaid",areaid);

		List<nb_residential_init> residential = residentialService.getListResidential(map);//市级对象
		Gson gson = new Gson();
		String listJson = gson.toJson(residential);
		return listJson;
	}

	/**
	 * 初始化区域信息
	 * @param request
	 * @param areaid
	 */
	private void setInitRegion(HttpServletRequest request,String areaid){
		tb_area area = areaService.infoArea(areaid);//县级对象
		tb_city city = cityService.infoCityid(area.getFather());//市级对象
		tb_province province = provinceService.infoProvinceid(city.getFather());//省级对象
		request.setAttribute("area", area);
		request.setAttribute("city", city);
		request.setAttribute("province", province);
	}
}
