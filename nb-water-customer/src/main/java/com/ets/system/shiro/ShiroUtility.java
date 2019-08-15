package com.ets.system.shiro;

import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
/**
 * @author: 姚轶文
 * @date:2018年8月27日 上午10:32:42
 * @version :
 * 
 */

@Service
public class ShiroUtility {




	public LinkedHashMap<String, String> getMap()
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//System.out.println("更新全部资源访问权限！");
		map.put("/resources/**", "anon"); //WEB资源文件，不进行验证
		map.put("/login/login.action", "anon"); //登陆，不进行验证
		//map.put("/login/login.action", "anon"); //登陆，不进行验证
		map.put("/custormerLogin/login.action", "anon"); //登陆，不进行验证
		map.put("/custormerLogin/loginCheck.action", "anon");
		map.put("/custormerLogin/loginSuccess.action", "anon");

		map.put("/login/loginCheck.action", "anon"); //登陆，不进行验证
		map.put("/login/loginSuccess.action", "anon"); //登陆跳转，不进行验证
		map.put("/websocket.action", "anon"); //websocket，不进行验证
		map.put("/jsp/business/websocket/**", "anon"); //登陆，不进行验证
		map.put("/test/**", "anon"); //测试，不进行验证

		//命令接口不进行验证
		map.put("/addnewdevice/JsonToAddNewDevice.action", "anon");//添加新设备
		map.put("/equipmentinfochange/JsonToEquipmentInfoChange.action", "anon");//设备信息变化
		map.put("/devicedatachange/JsonToDeviceDataChange.action", "anon");//设备数据变化
		map.put("/deletedevice/JsonToDeleteDevice.action", "anon");//删除设备
		map.put("/messageconfirm/JsonToMessageConfirm.action", "anon");//消息确认
		map.put("/responsecommand/JsonToResponseCommand.action", "anon");//响应命令
		map.put("/deviceevents/JsonToDeviceEvents.action", "anon");//设备事件
		map.put("/deviceinfochange/JsonToDeviceInfoChange.action", "anon");//设备信息变更
		map.put("/ruleevents/JsonToRuleEvents.action", "anon");//规则事件
		map.put("/devicebindingactivation/JsonToDeviceBindingActivation.action", "anon");//设备绑定激活
		map.put("/batchequipmentchange/JsonToBatchEquipmentChange.action", "anon");//设备数据批量变化

		//回调函数接口不进行验证
		map.put("/callback/ValveControlCallBackUrl.action", "anon");//阀控回调接口
		map.put("/callback/WaterMeterBasicCallBackUrl.action", "anon");//基础数据回调接口
		map.put("/callback/DeliveryCallBackUrl.action", "anon");//上报周期回调接口
		map.put("/callback/CheckTimeCallBackUrl.action", "anon");//校验时间回调接口
		
		map.put("/callback/WaterMeterTLVBasicCallBackUrl.action", "anon");//TLV基础数据回调接口
		

		map.put("/**", "authc");
		return map;
	}
}
