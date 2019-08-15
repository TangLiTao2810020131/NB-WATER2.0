package com.ets.business.balance.web;

import com.ets.business.balance.entity.BalanceModel;
import com.ets.business.balance.entity.nb_balance_dict;
import com.ets.business.balance.service.BalanceService;
import com.ets.business.custormer.entity.nb_customer_user;
import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.business.logopr.service.LogOprCustomerService;
import com.ets.business.meter.meterread.entity.MeterreadModel;
import com.ets.business.meter.meterread.service.MeterreadService;
import com.ets.business.meter.meterreadlog.entity.nb_meterread_log;
import com.ets.business.meter.meterreadlog.service.MeterreadlogService;
import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.common.DateTimeUtils;
import com.ets.common.JxlsUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wh
 *
 */
@Controller
@RequestMapping("balance")
public class BalanceController {

	@Resource
	MeterreadService meterreadService;
	@Resource
    CustomerUserService customerUserService;
	@Resource
    BalanceService balanceService;
	@Resource
	MeterreadlogService meterreadlogService;
	@Resource
	EquipmentService equipmentService;
	@Resource
    OwnerService ownerService;
	@Resource
	LogOprCustomerService logOprCustomerService;
	@Resource
    AreaService areaService;
	@Resource
    CityService cityService;
	@Resource
    ProvinceService provinceService;

	String baseUrl = "business/balance/";

	String modulename = "客户管理-抄表管理-抄表结算";

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
		return baseUrl+"balance-input";
	}

	@RequestMapping("info")
	public String info(HttpServletRequest request,String id)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		BalanceModel balance = new BalanceModel();
		if(id!=null && !id.equals(""))
		{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customerCode", customerId);
			map.put("id", id);
			balance = balanceService.infoBalance(map);
			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
			log.setIp(logOprCustomerService.getIp(request));
			log.setOprcontent("查看[户号:"+balance.getUseraccount()+"][户名:"+balance.getUsername()+"][结算月份:"+balance.getBalancemonth()+"]结算");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);
		}
		request.setAttribute("balance", balance);
		return baseUrl+"balance-info";
	}

	@RequestMapping("tree")
	public String tree(HttpServletRequest request)
	{
		return baseUrl+"balance-tree";
	}


	@RequestMapping("list")
	public String list(HttpServletRequest request,String areaid,String parentId)
	{
		tb_area area=areaService.infoArea(areaid);
		tb_city city=cityService.infoCityid(parentId);
		String proid=cityService.findProvinceIdByCityId(parentId);
		tb_province province=provinceService.infoProvinceid(proid);
		provinceCityArea="["+province.getProvince()+","+city.getCity()+","+area.getArea()+"]";

		tb_log_opr_customer log = new tb_log_opr_customer();//日志对象
		log.setModulename(modulename+provinceCityArea);//模块名
		log.setIp(logOprCustomerService.getIp(request));
		log.setOprcontent("访问区域列表页面");//模块内容
		nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
		log.setCustomercode(cus.getId());
		logOprCustomerService.addLog(log);//添加日志
		request.setAttribute("areaid",areaid);
		return baseUrl+"balance-list";
	}

	/**
	 * 分页查询接入类型数据字典集合
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String balancemonth,String useraccount,String areaid)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");
		Gson gson = new Gson();
		try {
			//System.out.println("page="+page+",limit="+limit);
			Map<String,Object> map = new HashMap<String,Object>();
			//			map.put("page", (page-1)*limit);//mysql
			//			map.put("limit", limit);//mysql
			map.put("customercode", customerId);
			map.put("areacode", areaid);
			map.put("page", (page)*limit);//oracle
			map.put("limit", (page-1)*limit);//oracle


			map.put("balancemonth", balancemonth); 
			map.put("useraccount", useraccount); 

			List<nb_balance_dict> balance = balanceService.getBalance(map);
			long count = balanceService.getCount(map);


			PageListData<nb_balance_dict> pageData = new PageListData<nb_balance_dict>();

			pageData.setCode("0");
			pageData.setCount(count);
			pageData.setMessage("");
			pageData.setData(balance);


			String listJson = gson.toJson(pageData);
			return listJson;
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}


	@RequestMapping(value="checkBalanceDate" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int checkBalanceDate(HttpServletRequest request,nb_balance_dict balance)
	{
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");

		try {

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customerCode", customerId);
			map.put("waterMeterId", balance.getWatermeterid());
			map.put("balanceMonth", balance.getBalancemonth());

			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
			log.setIp(logOprCustomerService.getIp(request));
			log.setOprcontent("检测"+modulename+"["+balance.getBalancemonth()+"月，是否读数结算]");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

			int num = balanceService.checkBalanceDate(map);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,nb_balance_dict balance,String watermetercode,String xqname,String balancemonth,String building,String doornum)
	{
		Gson gson = new Gson();
		try {

			this.setBalanceObject(balance);

			tb_log_opr_customer log = new tb_log_opr_customer();
			log.setModulename(modulename+provinceCityArea);
			log.setIp(logOprCustomerService.getIp(request));
			log.setOprcontent("为[小区:"+xqname+";楼栋号:"+building+";门牌号:"+doornum+"][IMEI:"+watermetercode+"]表手动结算,结算月份["+balancemonth+"]");
			nb_cus cus=(nb_cus)SecurityUtils.getSubject().getSession().getAttribute("customerSession");
			log.setCustomercode(cus.getId());
			logOprCustomerService.addLog(log);

			balanceService.opentionBalance(balance);
			return gson.toJson(new Message("1","操作成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","操作失败!"));
		}
	}

	private void setBalanceObject(nb_balance_dict balance){
		Map<?, ?> loginMap = (Map<?, ?>)SecurityUtils.getSubject().getSession().getAttribute("userSession");
		String customerId = (String)loginMap.get("CID");

		try {
			/*			Map<String,Object> map = new HashMap<String,Object>();
			map.put("customerCode", customerId);
			map.put("waterMeterId", balance.getWatermeterid());
			map.put("balanceMonth", balance.getBalancemonth());
			nb_meterread_log balanceMeterreadLog = meterreadlogService.selectBalanceStartTimeMeterreadLog(map);*/


			Map<String,Object> map = new HashMap<String,Object>();

			String cuurTime = balance.getBalancemonth();//选择的月份
			String lastTime = DateTimeUtils.lastMonth(cuurTime);//上个月月份

			map = new HashMap<String,Object>();
			map.put("id", balance.getWatermeterid());
			nb_watermeter_equipment equipment = equipmentService.infoEquipment(map);

			map = new HashMap<String,Object>();
			map.put("customerCode", customerId);
			map.put("doornumid", equipment.getDoornumid());
			nb_owner owner = ownerService.infoOwner(map);

			if(owner != null){
				//查询上月的用水量
				map = new HashMap<String,Object>();
				map.put("customerCode", customerId);
				map.put("waterMeterId", balance.getWatermeterid());
				map.put("lastTime", lastTime);
				nb_balance_dict lastBalance = balanceService.queryLastBalance(map);
				nb_meterread_log startBalance  = new nb_meterread_log();
				if(lastBalance == null){
					startBalance.setValue(equipment.getBasenum());
					startBalance.setOptiontime(equipment.getInstalldate());
				}else{
					double endNum = Double.valueOf(lastBalance.getEndnum());
					double startNum = Double.valueOf(lastBalance.getStartnum());
					startBalance.setValue(String.valueOf(endNum - startNum));
					startBalance.setOptiontime(lastBalance.getEndnumdate());
				}


				map = new HashMap<String,Object>();
				map.put("customerCode", customerId);
				map.put("waterMeterId", equipment.getId());
				map.put("balanceMonth", DateTimeUtils.getnowdate());
				nb_meterread_log endBalance = meterreadlogService.queryBalanceEndTimeMeterreadLog(map);//查询需要结算开始的月份最新的读数
				//若无记录，则取初始安装表读数和安装时间
				if(endBalance == null){
					endBalance = new nb_meterread_log();
					endBalance.setValue(equipment.getBasenum());
					endBalance.setOptiontime(equipment.getInstalldate());
				}
				balance.setWatermeterid(equipment.getId());
				balance.setBalancemonth(cuurTime);
				balance.setUseraccount(owner.getUseraccount());
				balance.setUsername(owner.getUsername());
				balance.setStartnum(startBalance.getValue());
				balance.setStartnumdate(startBalance.getOptiontime());
				balance.setEndnum(endBalance.getValue());
				balance.setEndnumdate(endBalance.getOptiontime());
				balance.setCustomercode(customerId);
				balance.setDescribe("手动结算");
				//balanceService.opentionBalance(balance);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * 导出报表
	 * @return
	 */
	@RequestMapping(value="export" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void export(HttpServletRequest request,HttpServletResponse response,String balancemonth,String useraccount,String areaid) throws Exception {

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


		String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/meter_reading_settlement_output.xls";
		OutputStream os = new FileOutputStream(path);
		Map<String , Object> model=new HashMap<String , Object>();
		model.put("balance", list);
		model.put("nowdate", new Date());
		JxlsUtils.exportExcel("meter_reading_settlement_template.xls", os, model);
		os.close();
		String fileName = province.getProvince() + city.getCity() + area.getArea() + DateTimeUtils.getTimestamp1() + "抄表结算" + ".xls";
		JxlsUtils.doDownLoad(path, fileName, response,request);
		
	}
}
