package com.ets.command.body;

import com.ets.command.model.ReportDataHAC;
import com.ets.common.ObjectCode;

import java.util.HashMap;
import java.util.Map;


/**
 * 命令体组装类
 * @author wuhao
 *
 */
public class CommandBody {


	/**
	 * 时间校验命令体组装
	 * @return
	 */
	public Object getCheckTimeCmd()
	{
		long millis = System.currentTimeMillis();
		
		int time = Integer.valueOf(String.valueOf(millis/1000));
		
		ReportDataHAC reportData = new ReportDataHAC();
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("2","1234567890");
		map.put("13",time);
		map.put("14","UTC+8");
		map.put("bn","/3/0");
		Map[] maps = new HashMap[1];
		maps[0] = map;
		reportData.setDev(maps);
		reportData.setCode("205");
		reportData.setBver("1.01");
		reportData.setMsgType("0");
		reportData.setMsgId("20827");
		reportData.setPayloadFormat("60");
		reportData.setCmdType(ObjectCode.NB_CHECK_TIME);

		return reportData;
	}

	/**
	 * 上报周期命令体组装
	 * @param time
	 * @return
	 */
	public Object getDeliveryCmd(String time)
	{
		ReportDataHAC reportData = new ReportDataHAC();

		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("0",time); // 上报周期
		map.put("1","130"); //上报时隙
		map.put("2","2"); //重发次数
		map.put("3","1200"); //重发周期
		map.put("bn", ObjectCode.DELIVERY_CODE); //对象编码
		Map[] maps = new HashMap[1];
		maps[0] = map;
		reportData.setDev(maps);
		reportData.setCode("205");
		reportData.setBver("1.01");
		reportData.setMsgType("0");
		reportData.setMsgId("20827");
		reportData.setPayloadFormat("60");
		reportData.setCmdType(ObjectCode.NB_REPORT_CYCLE);  //Delivery上报周期命令代码1

		return reportData;
	}

	/**
	 * 设置表读数命令体
	 * @param waterRead 表读数
	 * @return
	 */
	public Object getWaterMeterBasicCmd(String waterRead)
	{
		ReportDataHAC reportData = new ReportDataHAC();
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("0","LXC-20"); // 0 水表类型
		map.put("1",0); // 1 计量模式
		map.put("6",0); // 6 计量错误状态
		map.put("16",waterRead); //16 表读数
		//map.put("22",System.currentTimeMillis()); //阀门故障状态
		map.put("bn", ObjectCode.WATER_METER_CODE); //对象编码
		Map[] maps = new HashMap[1];
		maps[0] = map;
		reportData.setDev(maps);
		reportData.setCode("205");
		reportData.setBver("1.01");
		reportData.setMsgType("0");
		reportData.setMsgId("20827");
		reportData.setPayloadFormat("60");
		reportData.setCmdType(ObjectCode.NB_METER_READING);  //Delivery上报周期命令代码1
		return reportData;
	}

	/**
	 *阀控命令体组装
	 * @param valveData 0开阀  1关阀
	 * @return
	 */
	public Object getValveControlCmd(Integer valveData)
	{
		ReportDataHAC reportData = new ReportDataHAC();
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("0",valveData); // 0开阀  1关阀
		map.put("1",valveData); //阀门状态 0 开阀状态  1关阀状态  2阀门释放  3阀门正在动作
		map.put("2",0); //阀门故障状态
		map.put("bn","/81/0"); //对象编码
		Map[] maps = new HashMap[1];
		maps[0] = map;
		reportData.setDev(maps);
		reportData.setCode("205");
		reportData.setBver("1.01");
		reportData.setMsgType("0");
		reportData.setMsgId("20827");
		reportData.setPayloadFormat("60");
		reportData.setCmdType(ObjectCode.NB_VALVE_CONTROL);  //VALVECONTROL阀控命令代码1
		return reportData;
	}
}
