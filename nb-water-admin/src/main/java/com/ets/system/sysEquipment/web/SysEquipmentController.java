package com.ets.system.sysEquipment.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.ets.common.ConstantConfig;
import com.ets.common.DateTimeUtils;
import com.ets.common.JxlsUtils;
import com.ets.common.Message;
import com.ets.common.PageListData;
import com.ets.system.batch.entity.tb_sys_batch;
import com.ets.system.batch.service.BatchService;
import com.ets.system.cus_remote.entity.DeviceInfoEntity;
import com.ets.system.log.opr.entity.tb_log_opr;
import com.ets.system.log.opr.service.LogOprService;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.ets.system.sysEquipment.service.SysEquipmentService;
import com.google.gson.Gson;
import net.sf.json.JSONObject;


/**
 * 系统水表设备控制类
 * @author wuhao
 *
 */
@Controller
@RequestMapping("sysEquipment")
public class SysEquipmentController {
	
	private static Logger logger = LoggerFactory.getLogger(SysEquipmentController.class);

	String baseUrl = "system/sysEquipment/";
	
	@Resource
	RestTemplate restTemplate;

	@Resource
	ConstantConfig constantConfig;
	
	@Resource
	BatchService batchService;

	@Resource
	SysEquipmentService sysEquipmentService;

	@Resource
	LogOprService logService;
	
	
	List<tb_sys_equipment> errorList = new ArrayList<tb_sys_equipment>();

	@RequestMapping("list")
	public String list(HttpServletRequest request,String batchid)
	{
		tb_log_opr log=new tb_log_opr();
		log.setModulename("水表管理-水表检测");
		log.setOprcontent("查看水表检测列表页面");
		logService.addLog(log);
		
		request.setAttribute("batchid", batchid);
		
		return baseUrl + "sysEquipment-list";
	}

	@RequestMapping(value="listData" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listData(int page,int limit,String batchid,String startdate,String enddate,String imei)
	{
		//System.out.println("page="+page+",limit="+limit);
		Map<String,Object> map = new HashMap<String,Object>();
		//		map.put("page", (page-1)*limit);//mysql
		//		map.put("limit", limit);//mysql
		map.put("page", (page)*limit);//oracle
		map.put("limit", (page-1)*limit);//oracle
		map.put("startdate", startdate);//开始发送时间
		map.put("enddate", enddate);//结束发送时间
		map.put("batchid", batchid);//结束发送时间
		map.put("imei", imei);//imei

		List<tb_sys_equipment> equipment = sysEquipmentService.getSysEquipment(map);
		long count = sysEquipmentService.getCount(map);


		PageListData<tb_sys_equipment> pageData = new PageListData<tb_sys_equipment>();

		pageData.setCode("0");
		pageData.setCount(count);
		pageData.setMessage("");
		pageData.setData(equipment);

		Gson gson = new Gson();
		String listJson = gson.toJson(pageData);
		return listJson;
	}

	@RequestMapping("device-info")
	public String deviceInfo(HttpServletRequest request , String deviceid)
	{
		try {
			
			logger.info("===============================start查看设备详情数据============================================");
			DeviceInfoEntity entity = restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeviceInfo.action", deviceid, DeviceInfoEntity.class);
			request.setAttribute("info",entity);
			logger.info("设备详情：" + entity);
			logger.info("===============================start查看设备详情数据============================================");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  baseUrl + "sysEquipment-device-info";
	}


	@RequestMapping("toReportCycle")
	public String input(HttpServletRequest request,String ids)
	{
		request.setAttribute("ids", ids);
		return baseUrl + "sysEquipment-reportCycle";
	}


	/**
	 * 上报时间
	 * @return
	 */
	@RequestMapping(value="reportCycle" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String reportCycle(HttpServletRequest request ,String id[],String time)
	{
		String times = "900";
		if(!"0".equals(time)){
			double a = Double.valueOf(time) * 3600;
			int b = (int) a;
			times = String.valueOf(b);
		}

		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("times", times);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/ReportCycleTime.action", map, Boolean.class);
		return gson.toJson(new Message("1","设置上报周期命令缓存成功!等待执行。。。"));

	}



	@RequestMapping("toBasicNum")
	public String toBasicNum(HttpServletRequest request,String ids)
	{

		request.setAttribute("ids", ids);
		return baseUrl + "sysEquipment-basicnum";
	}

	/**
	 * 表读数
	 * @return
	 */
	@RequestMapping(value="readBasicNum" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String readBasicNum(HttpServletRequest request ,String id[],String basenum)
	{

		Gson gson = new Gson();

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("basenum", basenum);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/ReadBasicNum.action", map, Boolean.class);
		return gson.toJson(new Message("1","设置表读数命令缓存成功!等待执行。。。"));

	}




	/**
	 * 开水阀
	 * @return
	 */
	@RequestMapping(value="open" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String open(HttpServletRequest request ,String id[],String[] initNames,String[] doornums)
	{
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
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeviceClose.action", map, Boolean.class);
		return gson.toJson(new Message("1","关阀命令缓存成功成功!等待执行。。。"));

	}

	/**
	 * 跳转到水表设备新增或修改页面
	 * @param request 请求对象
	 * @param id 水表设备ID
	 * @return equipment-input.jsp
	 */
	@RequestMapping("input")
	public String input(HttpServletRequest request,String id,String batchid)
	{
		tb_log_opr log=new tb_log_opr();
		log.setModulename("水表管理-水表检测");
		log.setOprcontent("注册新水表页面");
		logService.addLog(log);
		
		request.setAttribute("batchid", batchid);

		return baseUrl + "sysEquipment-input";
	}

	@RequestMapping(value="isCheckIMEI" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int isCheckIMEI(tb_sys_equipment equipment){
		try {
			int num = 0;
			if("".equals(equipment.getId())){

				num = sysEquipmentService.isCheckIMEI(equipment.getImei());
			}
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@RequestMapping(value="save" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest request,tb_sys_equipment equipment)
	{
		if(!equipment.equals("1")){

			equipment.setControl("0");
		}

		Gson gson = new Gson();
		try {


			String deviceId = restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/OpentionSysEquipmentNBLOT.action", equipment, String.class);

			if(!"".equals(deviceId) && deviceId != null){

				equipment.setDeviceid(deviceId);

				sysEquipmentService.opentionSysEquipment(equipment);//添加或修改水表设备对象

			}else{

				return gson.toJson(new Message("3","注册设备失败!"));
			}

			return gson.toJson(new Message("1","注册设备成功!"));

		} catch (Exception e) {

			e.printStackTrace();

			return gson.toJson(new Message("2","操作异常!"));
		}
	}

	
	/**
	 * 删除水表设备对象
	 * @param id 水表设备对象ID集合
	 * @return JSON字符串
	 */
	@RequestMapping(value="delete" )
	@ResponseBody
	public String delete(HttpServletRequest request,String uuids[],String deviceids[])
	{
		Gson gson = new Gson();
		try {
			
			sysEquipmentService.deleteSysEquipment(uuids);
			
			List<String> list = new ArrayList<String>();
			
			for (String deviceid : deviceids) {
				list.add(deviceid);
			}
			
			restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/DeleteEquipmentNBLOT.action", list, String.class);//调用NB-IOT项目接口注册设备
			
			return gson.toJson(new Message("1","删除成功!"));
		} catch (Exception e) {
			e.printStackTrace();
			return gson.toJson(new Message("2","删除失败!"));
		}
	}
	
	/**
	 * 跳转到导入页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toSysEquipmentImport")
	public String toSysEquipmentImport(HttpServletRequest request){
		
		List<tb_sys_batch> list = batchService.getAll();
		request.setAttribute("list", list);
		return baseUrl + "sysEquipment-Import";
	}


	@RequestMapping(value="execlImport" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String execlImport(@RequestParam("file") MultipartFile file,String batchid, HttpServletRequest request){

		List<tb_sys_equipment> list = new ArrayList<tb_sys_equipment>();
		
		errorList = new ArrayList<tb_sys_equipment>();

		HSSFWorkbook workbook = null;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

			String fileName = file.getOriginalFilename() ;

			String ext =fileName.substring(fileName.lastIndexOf(".")+1);

			if(ext.equals("xls")||ext.equals("xlsx")){

				fileName=fileName.substring(0, fileName.length() - ext.length() - 1) + "_" + sdf.format(new Date()) + "." + ext;

				String savePath = constantConfig.getSysUploadUrl();

				File targetFile = new File(savePath, fileName);

				if (!targetFile.exists()) {

					targetFile.mkdirs();
				}

				file.transferTo(targetFile);//写文件

				File f = new File(savePath + "/" + fileName);

				InputStream inputStream = new FileInputStream(f);

				workbook = new HSSFWorkbook(inputStream);
				inputStream.close();

				//循环sheet
				for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
					if (hssfSheet == null) {
						continue;
					}
					
					int num = hssfSheet.getLastRowNum();
					// 循环行
					for (int rowNum = 2; rowNum <= num; rowNum ++) {

						tb_sys_equipment equipment = new tb_sys_equipment();
						equipment.setCstatus("0");
						equipment.setDstatus("0");
						equipment.setBatchid(batchid);

						HSSFRow hssfRow = hssfSheet.getRow(rowNum);

						if (hssfRow == null) {
							continue;
						}
						
						// 将单元格中的内容存入集合
						HSSFCell cell = hssfRow.getCell(0);

						if (cell == null || "".equals(cell) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
							continue;
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						equipment.setId(cell.getStringCellValue());

						cell = hssfRow.getCell(1);

						if (cell == null || "".equals(cell) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
							continue;
						}
						
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						//这里唯一需要注意的是类型的转换
						equipment.setImei(cell.getStringCellValue());
						
						
						cell = hssfRow.getCell(2);

						if (cell == null || "".equals(cell) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
							continue;
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						//这里唯一需要注意的是类型的转换
						equipment.setBasenum(cell.getStringCellValue());

						cell = hssfRow.getCell(3);

						if (cell == null ||  "".equals(cell) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
							continue;
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						//这里唯一需要注意的是类型的转换
						equipment.setControl(cell.getStringCellValue());
						
						list.add(equipment);
					}
				}
				int successNum = 0;


				for (tb_sys_equipment equipment : list) {
					
					String imei = equipment.getImei();
					
					int num = sysEquipmentService.isCheckIMEI(imei);
					
					if(num != 0){
						
						errorList.add(equipment);
					}else{
						
						String deviceId = restTemplate.postForObject(constantConfig.getNbiotPreHost() + "/api/OpentionEquipmentNBLOT.action", equipment, String.class);

						if(!"".equals(deviceId) && deviceId != null){

							equipment.setDeviceid(deviceId);

							sysEquipmentService.opentionSysEquipment(equipment);//添加或修改水表设备对象

							successNum ++;
						}else{
							errorList.add(equipment);
						}
					}
				}
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("list", errorList);
				jsonObj.put("successNum", successNum);
				jsonObj.put("errorNum", errorList.size());
				jsonObj.put("total", list.size());
				return jsonObj.toString();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 导出报表
	 * @return
	 */
	@RequestMapping(value="exportError" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void exportError(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/error_output.xls";
		OutputStream os = new FileOutputStream(path);
		Map<String , Object> model=new HashMap<String , Object>();
		model.put("equipment", errorList);
		JxlsUtils.exportExcel("error_template.xls", os, model);
		os.close();
		String fileName = "导入失败水表" + DateTimeUtils.getTimestamp1() + ".xls";
		JxlsUtils.doDownLoad(path, fileName, response,request);
	}
	
	/**
	 * 导出报表
	 * @return
	 */
	@RequestMapping(value="exportTemplate" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public void exportTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String path = JxlsUtils.class.getClassLoader().getResource("jxls").getPath()+"/sys_equipment_template.xls";

		String fileName = "水表导入格式模板" + ".xls";
		
		JxlsUtils.doDownLoad(path, fileName, response,request);
	}
	
	@RequestMapping(value="getWaterNum" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public int getWaterNum(String batchid){
		try {
			int num = 0;
			if(!"".equals(batchid)){
				num = sysEquipmentService.getWaterNum(batchid);
			}
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
