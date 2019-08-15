package com.ets.nb_iot.cmdinfo.command.concurrent;

import com.ets.common.ObjectCode;
import com.ets.nb_iot.cmdinfo.command.send.CommandService;
import com.ets.nb_iot.model.ReportDataHAC;
import com.ets.nb_iot.model.ReportDataTLV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * @author 姚轶文
 * @create 2018- 12-19 9:08
 */
@Service
@Scope(value = "singleton")
public class CmdAdapter implements  Runnable {

	CmdDelay cmdDelay;

	@Autowired
	protected CommandService commandService;


	public CmdDelay getCmdDelay() {
		return cmdDelay;
	}

	public void setCmdDelay(CmdDelay cmdDelay) {
		this.cmdDelay = cmdDelay;
	}

	@Override
	public void run() {
		if(cmdDelay!=null)
		{
			String deviceId = cmdDelay.getDeviceId();
			ReportDataHAC reportData = cmdDelay.getReportData();
			ReportDataTLV reportDataTLV = cmdDelay.getReportDataTLV();

			if(reportData != null){
				if(reportData.getCmdType() == ObjectCode.NB_CHECK_TIME){
					//logger.info("=========================================start时间校验==========================================");
					commandService.CheckTime(deviceId, reportData);
					//logger.info("==========================================end时间校验===========================================");
				}
				if(reportData.getCmdType() == ObjectCode.NB_REPORT_CYCLE){
					//logger.info("=========================================start执行上报周期命令=======================================");
					commandService.AssemblyDeliveryCommand(deviceId, reportData);
					//logger.info("=========================================end执行上报周期命令=========================================");
				}
				if(reportData.getCmdType() == ObjectCode.NB_VALVE_CONTROL){
					//logger.info("=========================================start执行设置读数命令==========================================");
					commandService.AssemblyValveControlCommand(deviceId, reportData);
					//logger.info("==========================================end执行设置读数命令===========================================");
				}
				if(reportData.getCmdType() == ObjectCode.NB_METER_READING){
					//logger.info("=========================================start执行阀控命令==========================================");
					commandService.AssemblyWaterMeterBasicCommand(deviceId, reportData);
					//logger.info("==========================================end执行阀控命令===========================================");
				}
			}

			if(reportDataTLV != null){
				
				if(reportDataTLV.getCmdType() == ObjectCode.NB_CHECK_TIME){
					//commandService.CheckTimeTLV(deviceId, reportDataTLV);
				}
				if(reportDataTLV.getCmdType() == ObjectCode.NB_RAW){
					commandService.AssemblyTLVBasicCommand(deviceId, reportDataTLV);
				}

			}
		}
	}
}
