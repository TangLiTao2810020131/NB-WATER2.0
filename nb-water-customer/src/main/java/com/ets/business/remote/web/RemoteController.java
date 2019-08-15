package com.ets.business.remote.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.business.commandsendlog.service.CommandSendLogService;
import com.ets.business.init.daelaytime.service.DelayTimeService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.readlog.entity.nb_read_log;
import com.ets.business.readlog.service.ReadLogService;
import com.ets.business.remote.entity.DeviceInfoEntity;
import com.ets.business.remote.entity.RemoteListEntity;
import com.ets.business.remote.service.RemoteService;
import com.ets.common.ConstantConfig;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.common.dtree.DtreeEntity;
import com.ets.dictionary.district.entity.tb_area;
import com.ets.dictionary.district.entity.tb_city;
import com.ets.dictionary.district.entity.tb_province;
import com.ets.dictionary.district.service.AreaService;
import com.ets.dictionary.district.service.CityService;
import com.ets.dictionary.district.service.ProvinceService;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus_region.entity.tb_cus_region_area;
import com.ets.system.cus_region.entity.tb_cus_region_city;
import com.ets.system.cus_region.entity.tb_cus_region_province;
import com.ets.system.cus_region.service.CusRegionSerivce;
import com.ets.system.shiro.cache.RedisClientTemplate;
import com.google.gson.Gson;

/**
 * @author 姚轶文
 * @create 2018- 11-20 11:15
 */
@Controller
@RequestMapping("remote")
public class RemoteController {
	
	@Resource
	RestTemplate restTemplate;
	
	@Resource
	ConstantConfig constantConfig;

	@Resource
	CommandSendLogService commandSendLogService;

	@Resource
    ReadLogService readLogService;

	@Resource
    AreaService areaService;

	@Resource
    CityService cityService;

	@Resource
    ProvinceService provinceService;

	@Autowired
    RemoteService remoteService;

	@Resource
    LogOprCustomerService logOprCustomerService;

	@Resource
	DelayTimeService delayTimeService;
	
	@Autowired
    RedisClientTemplate redisClientTemplate;

    @Resource
    CusRegionSerivce cusRegionSerivce;

	String baseUrl = "business/remote/";

	String modulename = "客户管理-设备管理-远程控制";

	//将"省份,城市,地区"拼接在一起
	String provinceCityArea;

	@RequestMapping("tree")
	public String input(HttpServletRequest request)
	{
		return "business/remote/remote-tree";
	}

	@RequestMapping(value="treeData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public DtreeEntity tree(HttpServletRequest request, String nodeId , String parentId , String isLeaf , String context , String level, String spared)
	{
		Map loginMap = (Map) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cusid = (String)loginMap.get("CID"); //从session里获取客户ID
		List<tb_cus_region_province> regionP = cusRegionSerivce.queryCusRegionProvince(cusid);
		List<tb_cus_region_city> regionC = cusRegionSerivce.queryCusRegionCity(cusid,regionP.get(0).getProvinceid());
		parentId = regionC.get(0).getCityid();
		List<tb_cus_region_area> regionA = cusRegionSerivce.queryCusRegionArea(cusid,regionC.get(0).getCityid());
		DtreeEntity dtreeEntity = null;
		dtreeEntity = remoteService.getAreas(parentId,regionA);

		return dtreeEntity;
	}

	@RequestMapping("list")
	public String list(String areaId,HttpServletRequest request,String parentId)
	{

		tb_area area=areaService.infoArea(areaId);
		tb_city city=cityService.infoCityid(parentId);
		String proid=cityService.findProvinceIdByCityId(parentId);
		tb_province province=provinceService.infoProvinceid(proid);
		provinceCityArea="["+province.getProvince()+","+city.getCity()+","+area.getArea()+"]";

		//将"查看区域列表"信息记录到操作日志
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);
		log.setOprcontent("查看区域列表列表");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		request.setAttribute("areaId",areaId);
		return "business/remote/remote-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageListData<RemoteListEntity> listData(int page, int limit , String areaId, String useraccount, String username)
	{
		Map loginMap = (Map) SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String cid = (String)loginMap.get("CID"); //从session里获取客户ID

		//获取此客户 此地区的用户列表
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("cid", cid);
		map.put("areaId", areaId);
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		
		map.put("useraccount", useraccount);//户号
		map.put("username", username);//业主名
		List<RemoteListEntity> remoteList = remoteService.list(map);

		long count = remoteService.getCount(map);

		PageListData<RemoteListEntity> pageData = new PageListData<RemoteListEntity>();

		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(remoteList);

		//Gson gson = new Gson();
		//String listJson = gson.toJson(pageData);
		return pageData;
	}

	@RequestMapping("history-data-list")
	public String historyDataList(String deviceId,HttpServletRequest request)
	{
		request.setAttribute("deviceId",deviceId);
		return "business/remote/remote-history-data";
	}

	@RequestMapping("history-cmd-list")
	public String historyCmdList(String deviceId,HttpServletRequest request)
	{
		request.setAttribute("deviceId",deviceId);
		return "business/remote/remote-history-cmd";
	}

	@RequestMapping(value="history-data-data" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deviceHistoryData(HttpServletRequest request , String deviceId ,int page,int limit)
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

			map.put("deviceid", deviceId);//设备ID
			List<nb_read_log> comsend = readLogService.getReadLog(map);
			long count = readLogService.getCount(map);


			PageListData<nb_read_log> pageData = new PageListData<nb_read_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(comsend);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}

	@RequestMapping(value="history-cmd-data" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deviceHistoryCmd(HttpServletRequest request , String deviceId ,int page,int limit)
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

			map.put("deviceid", deviceId);//设备ID
			List<nb_command_send_log> comsend = commandSendLogService.getCommandSendLog(map);
			long count = commandSendLogService.getCount(map);


			PageListData<nb_command_send_log> pageData = new PageListData<nb_command_send_log>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(comsend);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	@RequestMapping("device-info")
	public String deviceInfo(HttpServletRequest request , String deviceId)
	{
		try {
			DeviceInfoEntity entity = restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeviceInfo.action", deviceId, DeviceInfoEntity.class);;
			
			request.setAttribute("info",entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "business/remote/remote-device-info";
	}

	/**
	 * 开水阀
	 * @return
	 */
	@RequestMapping(value="open" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String open(HttpServletRequest request ,String id[],String[] initNames,String[] doornums)
	{

		//将所有的小区名拼接
		StringBuilder sb1=new StringBuilder();
		for(String initName:initNames)
		{
			sb1.append(initName+",");
		}
		//将所有户名拼接
		StringBuilder sb2=new StringBuilder();
		for(String doornum:doornums)
		{
			sb2.append(doornum+",");
		}

		//将小区开阀操作记录到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setModulename(modulename+provinceCityArea);
		log.setOprcontent("为小区["+sb1.substring(0,sb1.length()-2)+"]户号["+sb2.substring(0,sb2.length()-2)+"]开阀");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeviceOpen.action", map, Boolean.class);
		return gson.toJson(new Message("1","开阀命令缓存成功!等待执行。。。"));
	}

	/**
	 * 关闭水阀
	 * @return
	 */
	@RequestMapping(value="close" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String close(HttpServletRequest request ,String id[],String[] initNames,String[] doornums)
	{
		//将所有的小区名拼接
		StringBuilder sb1=new StringBuilder();
		for(String initName:initNames)
		{
			sb1.append(initName+",");
		}
		//将所有户名拼接
		StringBuilder sb2=new StringBuilder();
		for(String doornum:doornums)
		{
			sb2.append(doornum+",");
		}

		//将小区关阀操作记录到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setModulename(modulename+provinceCityArea);
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setOprcontent("为小区["+sb1.substring(0,sb1.length()-2)+"]户号["+sb2.substring(0,sb2.length()-2)+"]关阀");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeviceClose.action", map, Boolean.class);
		return gson.toJson(new Message("1","关阀命令缓存成功成功!等待执行。。。"));

	}

	@RequestMapping("toReportCycle")
	public String input(HttpServletRequest request,String ids,String initNames,String doornums)
	{
		request.setAttribute("ids", ids);
		request.setAttribute("initNames", initNames);
		request.setAttribute("doornums", doornums);
		tb_log_opr_customer log = new tb_log_opr_customer();
		log.setModulename(modulename+provinceCityArea);
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setOprcontent("访问设置上报时间页面");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);
		return baseUrl+"remote-reportCycle";
	}

	/**
	 * 上报时间
	 * @return
	 */
	@RequestMapping(value="reportCycle" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String reportCycle(HttpServletRequest request ,String id[],String time,String initNames,String doornums)
	{
		String times = "900";
		if(!"0".equals(time)){
			double a = Double.valueOf(time) * 3600;
			int b = (int) a;
			times = String.valueOf(b);
		}

		//将设置上报周期操作记录到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setModulename(modulename+provinceCityArea);
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setOprcontent("为小区["+initNames.substring(0,initNames.length()-1)+"]户号["
				+doornums.substring(0,doornums.length()-1)+"]设置上报周期[上报周期:"+time+"]小时");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("times", times);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/ReportCycleTime.action", map, Boolean.class);
		return gson.toJson(new Message("1","设置上报周期命令缓存成功!等待执行。。。"));

	}

	@RequestMapping("toBasicNum")
	public String toBasicNum(HttpServletRequest request,String ids,String initNames,String doornums)
	{

		request.setAttribute("ids", ids);
		request.setAttribute("initNames", initNames);
		request.setAttribute("doornums", doornums);
		return baseUrl+"remote-basicnum";
	}

	/**
	 * 表读数
	 * @return
	 */
	@RequestMapping(value="readBasicNum" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String readBasicNum(HttpServletRequest request ,String id[],String basenum,String initNames,String doornums)
	{

		//将设置表读数操作记录到操作日志
		tb_log_opr_customer log=new tb_log_opr_customer();
		log.setModulename(modulename+provinceCityArea);
		log.setIp(logOprCustomerService.getIp(request));//ip地址
		log.setOprcontent("为小区["+initNames.substring(0,initNames.length()-1)+"]户号["
				+doornums.substring(0,doornums.length()-1)+"]设置表读数[表读数:"+basenum+"]");
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);

		Gson gson = new Gson();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("basenum", basenum);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/ReadBasicNum.action", map, Boolean.class);

		return gson.toJson(new Message("1","设置表读数命令缓存成功!等待执行。。。"));
	}
	
	@RequestMapping("toCommandCler")
	public String toCommandCler(HttpServletRequest request,String ids,String initNames,String doornums)
	{

		request.setAttribute("ids", ids);
		request.setAttribute("initNames", initNames);
		request.setAttribute("doornums", doornums);
		return baseUrl + "remote-commandcler";
	}

	
	/**
	 * 表读数
	 * @return
	 */
	@RequestMapping(value="cleanCommand" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String cleanCommand(HttpServletRequest request ,String id[],String type[])
	{

		Gson gson = new Gson();

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("type", type);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/CleanCommand.action", map, Boolean.class);
		return gson.toJson(new Message("1","取消下发命令成功!"));

	}
	
}
