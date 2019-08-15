package com.ets.nb_iot.cmdinfo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.ets.business.alarm.service.EquipmentAlarmService;
import com.ets.business.daelaytime.entity.nb_delay_time_basicnum;
import com.ets.business.daelaytime.entity.nb_delay_time_delivery;
import com.ets.business.daelaytime.entity.nb_delay_time_valvecontrol;
import com.ets.business.daelaytime.service.DelayTimeService;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.meterread.entity.nb_meterread;
import com.ets.business.meterread.service.MeterreadService;
import com.ets.business.meterreadlog.service.MeterreadlogService;
import com.ets.business.readlog.service.ReadLogService;
import com.ets.common.JsonUtils;
import com.ets.common.ObjectCode;
import com.ets.common.TemplateUtils;
import com.ets.nb_iot.body.ObjectClass;
import com.ets.nb_iot.body.ObjectClassTLV;
import com.ets.nb_iot.cmdinfo.iotinit.NbIotConfig;
import com.ets.nb_iot.model.DeviceInfo;
import com.ets.nb_iot.model.NonmagneticWaterMeter;
import com.ets.nb_iot.model.ReceiveEquipment;
import com.ets.nb_iot.model.ReportDataHAC;
import com.ets.nb_iot.model.ReportDataTLV;
import com.ets.nb_iot.model.Signal;
import com.ets.nb_iot.model.ValveControl;
import com.ets.nb_iot.model.WaterMeterBasic;
import com.ets.system.cache.ByteSourceUtils;
import com.ets.system.cache.RedisClientTemplate;
import com.ets.system.log.login.service.LogLoginService;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.ets.system.sysEquipment.service.SysEquipmentService;
import com.ets.system.sysEquipmentAlarm.service.SysEquipmentAlarmService;
import com.ets.system.sysReadLog.service.SysReadLogService;
import com.ets.system.sysdaelaytime.entity.sys_delay_time;
import com.ets.system.sysdaelaytime.service.SysDelayTimeService;

/**
 * 
 * @ClassName:     DataReporting.java 
 * @Description:   数据上报操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午4:58:08
 */
@Component
public class DataReporting implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(DataReporting.class);

	private String date;

	private HttpServletRequest request;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Resource
	EquipmentService equipmentService;

	@Resource
    SysEquipmentService sysEquipmentService;

	@Resource
    SysDelayTimeService sysDelayTimeService;

	@Resource
	DelayTimeService delayTimeService;

	@Resource
    NbIotConfig nbIotConfig;

	@Resource
    RedisClientTemplate redisClientTemplate;

	@Resource
	MeterreadlogService meterreadlogService;

	@Resource
	MeterreadService meterreadService;

	@Resource
    SysReadLogService sysReadLogService;
	
	@Resource
	ReadLogService readLogService;

	@Resource
    LogLoginService logLoginService;

	@Resource
    SysEquipmentAlarmService sysAlarmService;

	@Resource
    EquipmentAlarmService alarmService;
	

	@Override
	public void run() {

		try {

			JsonUtils jsonUtils = new JsonUtils();

			TemplateUtils utli = new TemplateUtils();

			ReceiveEquipment equipmentReceive = jsonUtils.JsonReceiveEquipment(date);//解析电信推送来的数据转行为设备对象

			if(equipmentReceive != null){//若设备对象不为空

				utli.CheckTime(equipmentReceive.getDeviceId());//只要接收到上报数据就下发校时命令

				nb_watermeter_equipment equipment = equipmentService.queryWMEinfoByDeviceId(equipmentReceive.getDeviceId());//根据设备ID查询客户端水表设备

				tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(equipmentReceive.getDeviceId());//根据设备ID查询系统端水表设备

				String baseNum = "";//表读数
				
				String psVoltage = "";//电池电压
				
				String rssi = "";//设备信号度
				
				String snr = "";//设备信噪比

				WaterMeterBasic basic = null;

				ValveControl value = null;

				DeviceInfo info = null;
				
				Signal signal = null;

				String payloadFormat = jsonUtils.JsonToPayloadFormat(equipmentReceive.getService());//水表类型

				logger.info("start获取命令。并准备执行命令");
				//查询redies内上报周期的命令,存在则取出命令体
				ReportDataHAC reportDataDelivery = getReportDataTORedis(nbIotConfig.getDelivery(),equipmentReceive.getDeviceId() + nbIotConfig.getDelivery());

				//查询redies内表读数的命令,存在则取出命令体
				ReportDataHAC reportDataWaterMeterBasic = getReportDataTORedis(nbIotConfig.getWater_meter_basic(),equipmentReceive.getDeviceId() + nbIotConfig.getWater_meter_basic());

				//查询redies内阀控的命令,存在则取出命令体
				ReportDataHAC reportValveControl = getReportDataTORedis(nbIotConfig.getValve_control(),equipmentReceive.getDeviceId() + nbIotConfig.getValve_control());

				logger.info("end获取命令。并准备执行命令");

				if("60".equals(payloadFormat)){

					logger.info("start解析获ReportDataHAC对象内的dev饼转化为：WaterMeterBasic、ValveControl、DeviceInfo等对象");

					ReportDataHAC hacRdata = jsonUtils.JsonToReportDataHAC(equipmentReceive.getService());

					Map<String,Object>[] devmap = hacRdata.getDev();
					
					ObjectClass o = new ObjectClass();

					for(int i = 0;i < devmap.length;i++){

						Map<String,Object> map = (devmap[i]);

						String bn = (map.get("bn")).toString();

						if(ObjectCode.WATER_METER_CODE.equals(bn)){

							basic = o.MapToWMBasic(map);
							logger.info("水表基础信息对象：" + basic.toString());
						}
						if(ObjectCode.DEV_CODE.equals(bn)){

							info = o.MapToDeviceInfo(map);
							logger.info("设备信息 对象对象：" + info.toString());
						}
						if(ObjectCode.VALVE_CONTROL_CODE.equals(bn)){

							value = o.MapToValue(map);
							logger.info("阀门信息对象：" + value.toString());
						}
						if(ObjectCode.NB_CODE.equals(bn)){

							signal = o.MapToSignal(map);
							logger.info("设备信号对象：" + signal.toString());
						}
					}

					logger.info("end解析获ReportDataHAC对象内的dev饼转化为：WaterMeterBasic、ValveControl、DeviceInfo等对象");
				}

				if(equipment != null){

					logger.info("start客户端执行操作");

					long basicnumtime = 0;//表读数延迟执行时间
					long deliverytime = 0;//上报周期延迟执行时间
					long valvecontroltime = 0;//阀控延迟执行时间

					if("60".equals(payloadFormat)){//JSON格式HAC协议

						baseNum = basic.getWaterRead();//上报设备的表读数
						
						psVoltage = info.getPowerSourceVoltage();
						
						rssi = signal.getRssi();
						
						snr = signal.getSnr();
						
						equipment.setPsvoltage(psVoltage);
						equipment.setRssi(rssi);
						equipment.setSnr(snr);
						equipment.setIsonline("1");
						
						logger.info("start每上报一次就更新设备在线、信号、信噪比等数据");
						equipmentService.updateOnlinePRS(equipment);//每上报一次就更新设备在线、信号、信噪比等数据
						logger.info("end上每上报一次就更新设备在线、信号、信噪比等数据");
						
						logger.info("start上报数据日志记录");
						readLogService.AddReadLog(date,equipmentReceive,equipment,logLoginService.getIp(request));
						logger.info("end上报数据日志记录");

						//设备告警添加
						alarmService.addEquipmentAlarm(equipment,basic,info,value);

						if(reportDataDelivery != null){//若命令存在则获取延迟执行时间
							nb_delay_time_delivery delivery = delayTimeService.infoDelivery(equipment.getCustomercode());
							deliverytime = Long.valueOf(delivery.getValue()) * 1000;
						}

						if(reportDataWaterMeterBasic != null){//若命令存在则获取延迟执行时间
							nb_delay_time_basicnum basicnum = delayTimeService.infoBasicNum(equipment.getCustomercode());
							basicnumtime = Long.valueOf(basicnum.getValue()) * 1000;
						}

						if(reportValveControl != null){//若命令存在则获取延迟执行时间
							nb_delay_time_valvecontrol valvecontrol = delayTimeService.infoValveControl(equipment.getCustomercode());
							valvecontroltime = Long.valueOf(valvecontrol.getValue()) * 1000;
						}

						long time1 = basicnumtime;//表读数延迟执行时间
						long tmie2 = basicnumtime + deliverytime;//上报周期延迟执行时间
						long tmie3 = basicnumtime + deliverytime + valvecontroltime;//阀控延迟执行时间

						if(time1 != 0){
							utli.AssemblyWaterMeterBasicCommand(equipmentReceive.getDeviceId(),reportDataWaterMeterBasic,time1);//将设置表读数命令添加到队列中
						}
						if(tmie2 != 0){
							utli.AssemblyDeliveryCommand(equipmentReceive.getDeviceId(),reportDataDelivery,tmie2);//将上报周期命令添加到队列中
						}
						if(tmie3 != 0){
							utli.AssemblyValveControlCommand(equipmentReceive.getDeviceId(),reportValveControl,tmie3);//将阀控命令添加到队列中
						}

					}
					if("42".equals(payloadFormat)){//透传协议水表解析

						ReportDataTLV tlvRdata = jsonUtils.JsonToReportDataTLV(equipmentReceive.getService());//解析透传协议并转化为ReportDataTLV对象

						if(tlvRdata.getRaw().length() != 70){//若数据内容长度不为70则为上报数据内容，若为70则为响应

							String datas[] = tlvRdata.getRaw().split("fa");
							NonmagneticWaterMeter waterMeter = ObjectClassTLV.getNonmagneticWaterMeter(datas[1]);

							ReportDataTLV reportWaterMeter = getReportDataTLVTORedis(equipmentReceive.getDeviceId() + "SETRAW");//去除Redis表读数命令

							if(reportWaterMeter != null){//若命令存在则获取延迟执行时间

								nb_delay_time_basicnum basicnum = delayTimeService.infoBasicNum(equipment.getCustomercode());
								basicnumtime = Long.valueOf(basicnum.getValue()) * 1000;//命令延迟 执行时间

								utli.AssemblyTLVBasicCommand(equipmentReceive.getDeviceId(),reportWaterMeter,basicnumtime);//将设置表读数命令添加到队列中
							}
							baseNum = waterMeter.getParameter();
						}

					}

					if(baseNum != null && baseNum != ""){//若表读数不为空则判断是否有计量数据错误告警

						Map<String,Object> readMap = new HashMap<String, Object>();
						readMap.put("watermeterid", equipment.getId());
						readMap.put("customercode", equipment.getCustomercode());

						nb_meterread readDB = meterreadService.queryMeterRead(readMap);

						long baseNumDB = Long.valueOf(readDB.getValue());
						long baseNumL = Long.valueOf(baseNum);

						if(baseNumL < baseNumDB){//判断当前读数是否小于历史最新读数，若小于则添加计量错误告警
							logger.info("计量数据错误");
							alarmService.addBaseNumAlarm(equipment);
						}

						meterreadService.AddMeterRead(equipmentReceive.getDeviceId(), baseNum,equipment);//最新表读数日志

						meterreadlogService.AddMeterReadLog(equipmentReceive.getDeviceId(), baseNum,equipment);//历史表读数日志
					}
					logger.info("end客户端执行操作");

				}
				if(sysEquipment != null){

					logger.info("start系统执行操作");

					sysEquipmentService.updateDstatus(sysEquipment.getDeviceid());//该设备已经上报数据

					if("60".equals(payloadFormat)){

						baseNum = basic.getWaterRead();

						sys_delay_time sysDelayTime = sysDelayTimeService.getSysDaelayTime();

						long time1 = Long.valueOf(sysDelayTime.getBasicnumtime()) * 1000;
						long time2 = Long.valueOf(sysDelayTime.getDeliverytime()) * 1000;
						long time3 = Long.valueOf(sysDelayTime.getValvecontroltime()) * 1000;

						if(reportDataWaterMeterBasic != null){

							logger.info("设置表读数");
							utli.AssemblyWaterMeterBasicCommand(equipmentReceive.getDeviceId(),reportDataWaterMeterBasic,time1);							
						}else{
							time1 = 0;
						}

						if(reportDataDelivery != null){

							time2 = time1 + time2;
							logger.info("设置表上报周期");
							utli.AssemblyDeliveryCommand(equipmentReceive.getDeviceId(),reportDataDelivery,time2);							
						}else{
							time2 = 0;
						}

						if(reportValveControl != null){

							time3 = time1 + time2 + time3 ;
							logger.info("设置阀控");
							utli.AssemblyValveControlCommand(equipmentReceive.getDeviceId(),reportValveControl,time3);
						}else{
							time3 = 0;
						}

					}
					if("42".equals(payloadFormat)){

						ReportDataTLV tlvRdata = jsonUtils.JsonToReportDataTLV(equipmentReceive.getService());

						if(tlvRdata.getRaw().length() != 70){
							String datas[] = tlvRdata.getRaw().split("fa");

							NonmagneticWaterMeter waterMeter = ObjectClassTLV.getNonmagneticWaterMeter(datas[1]);

							ReportDataTLV reportWaterMeter = getReportDataTLVTORedis(equipmentReceive.getDeviceId() + "SETRAW");
							if(reportWaterMeter != null){//若命令存在则获取延迟执行时间

								logger.info("设置表读数" + reportWaterMeter);
								utli.AssemblyTLVBasicCommand(equipmentReceive.getDeviceId(),reportWaterMeter,5000);
							}

							baseNum = waterMeter.getParameter();
						}
					}

					sysReadLogService.addSysReadLog(date,equipmentReceive,sysEquipment,logLoginService.getIp(request),baseNum);

					sysAlarmService.addEquipmentAlarm(sysEquipment,basic,info,value);

					logger.info("end系统执行操作");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	* @Title: getReportDataTORedis 
	* @Description: 根据redis内的KEY取出命令
	* @param: @param type 命令类型
	* @param: @param jedisKey Redis KEY键
	* @return: ReportDataHAC    
	* @Date: 2019年7月25日 下午5:09:50  
	 */
	public ReportDataHAC getReportDataTORedis(String type,String jedisKey){

		String keyPrefix = "redis_command_" + type + ":" + jedisKey;

		ReportDataHAC reportData = null;

		byte[] bytes = redisClientTemplate.get(keyPrefix.getBytes());

		ByteSourceUtils byteSourceUtils = new ByteSourceUtils();

		reportData = (ReportDataHAC)byteSourceUtils.deserialize(bytes);

		logger.info("取出redis内的命令:"+keyPrefix);

		return reportData;
	}

	/**
	 * 
	* @Title: getReportDataTLVTORedis 
	* @Description: 根据redis内的KEY取出命令
	* @param: @param jedisKey Redis KEY键
	* @return: ReportDataTLV    
	* @Date: 2019年7月25日 下午5:30:58  
	 */
	public ReportDataTLV getReportDataTLVTORedis(String jedisKey){

		String keyPrefix = "redis_command_SETRAW:" + jedisKey;

		ReportDataTLV reportData = null;

		byte[] bytes = redisClientTemplate.get(keyPrefix.getBytes());

		ByteSourceUtils byteSourceUtils = new ByteSourceUtils();

		reportData = (ReportDataTLV)byteSourceUtils.deserialize(bytes);
		logger.info("取出redis内的命令:"+jedisKey);

		return reportData;
	}

}