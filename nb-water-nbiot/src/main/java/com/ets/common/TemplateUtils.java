package com.ets.common;

import com.ets.nb_iot.body.CommandBody;
import com.ets.nb_iot.body.CommandBodyTLV;
import com.ets.nb_iot.cmdinfo.command.concurrent.CmdDelay;
import com.ets.nb_iot.cmdinfo.command.concurrent.CmdQueue;
import com.ets.nb_iot.model.ReportDataHAC;
import com.ets.nb_iot.model.ReportDataTLV;

/**
 * 
 * @ClassName:     TemplateUtils.java 
 * @Description:   队列工具类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午5:24:14
 */
public class TemplateUtils {
	
	
	/**
	 * 
	* @Title: CheckTime 
	* @Description: 将校时命令,存储到队列中
	* @param: @param deviceId  设备iD
	* @return: void    
	* @Date: 2019年7月25日 下午5:27:20  
	 */
	public  void CheckTime(String deviceId){

		ReportDataHAC reportData = (ReportDataHAC)new CommandBody().getCheckTimeCmd();
		if(reportData != null){
			CmdDelay delay = new CmdDelay(deviceId,reportData,System.currentTimeMillis());
			CmdQueue.getDelayQueue().put(delay);
		}

	}

	/**
	 * 
	* @Title: AssemblyDeliveryCommand 
	* @Description: 将上报周期名命令体,存储到队列中
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令对象
	* @param: @param time 延迟时间
	* @return: void    
	* @Date: 2019年7月25日 下午5:25:39  
	 */
	public  void AssemblyDeliveryCommand(String deviceId, ReportDataHAC reportData, long time){

		if(reportData != null){
			CmdDelay delay = new CmdDelay(deviceId,reportData,System.currentTimeMillis() + time);
			CmdQueue.getDelayQueue().put(delay);
		}
	}

	/**
	 * 
	* @Title: AssemblyWaterMeterBasicCommand 
	* @Description: 将设置表读数命令体，,存储到命令队列中
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令对象
	* @param: @param time 延迟时间
	* @return: void    
	* @Date: 2019年7月25日 下午5:24:34  
	 */
	public  void AssemblyWaterMeterBasicCommand(String deviceId, ReportDataHAC reportData, long time){

		if(reportData != null){
			CmdDelay delay = new CmdDelay(deviceId,reportData,System.currentTimeMillis() + time);
			CmdQueue.getDelayQueue().put(delay);
		}
	}

	/**
	 * 
	* @Title: AssemblyValveControlCommand 
	* @Description: 将阀控命令体,存储到命令队列中
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令对象
	* @param: @param time 延迟时间
	* @return: void    
	* @Date: 2019年7月25日 下午5:26:16  
	 */
	public  void AssemblyValveControlCommand(String deviceId, ReportDataHAC reportData, long time){

		if(reportData != null){
			CmdDelay delay = new CmdDelay(deviceId,reportData,System.currentTimeMillis() + time);
			CmdQueue.getDelayQueue().put(delay);
		}
	}

	/**
	 * 
	* @Title: CheckTimeTLV 
	* @Description: 将校时命令,存储到队列中
	* @param: @param deviceId  设备iD  
	* @return: void    
	* @Date: 2019年7月25日 下午5:33:27  
	 */
	public  void CheckTimeTLV(String deviceId){

		ReportDataTLV reportData = (ReportDataTLV)new CommandBodyTLV().getCheckTimeCmd();
		if(reportData != null){
			CmdDelay delay = new CmdDelay(deviceId,reportData,System.currentTimeMillis());
			CmdQueue.getDelayQueue().put(delay);
		}

	}
	
	/**
	 * 
	* @Title: AssemblyTLVBasicCommand 
	* @Description:  将设置表读数命令体，,存储到命令队列中
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体
	* @param: @param time  延迟时间
	* @return: void    
	* @Date: 2019年7月25日 下午5:32:28  
	 */
	public void AssemblyTLVBasicCommand(String deviceId, ReportDataTLV reportData, long time) {
		if(reportData != null){
			CmdDelay delay = new CmdDelay(deviceId,reportData,System.currentTimeMillis() + time);
			CmdQueue.getDelayQueue().put(delay);
		}
		
	}
}
