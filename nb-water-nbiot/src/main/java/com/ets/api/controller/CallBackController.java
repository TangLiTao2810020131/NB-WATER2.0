package com.ets.api.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.common.Common;
import com.ets.nb_iot.cmdinfo.command.callback.CallBack;
/**
 * Title:        CallBackController
 * author:       吴浩
 * version:      nb-water 2.0
 * Description:  命令下发回调函数回调函数
 * date:         2019/8/9 16:40
 */
@RestController
@RequestMapping("/callback")
public class CallBackController {


	@Autowired
	CallBack CallBack;

	/**
	 * 阀控命令回调函数
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("ValveControlCallBackUrl")
	public String ValveControlCallBackUrl(HttpServletRequest request){
		try{
			CallBack.ValveControlCallBackUrl(request);
		}catch(Exception e){
		}
		return "success";
	}

	/**
	 * 基础表数据命令回调函数
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("WaterMeterBasicCallBackUrl")
	public String WaterMeterBasicCallBackUrl(HttpServletRequest request) {
		try{
			CallBack.WaterMeterBasicCallBackUrl(request);
		}catch(Exception e){
		}
		return "success";
	}

	/**
	 * 上报周期命令回调函数
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("DeliveryCallBackUrl")
	public String DeliveryCallBackUrl(HttpServletRequest request) {
		try{
			CallBack.DeliveryCallBackUrl(request);
		}catch(Exception e){
		}
		return "success";
	}

	/**
	 * 校验时间命令回调函数
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("CheckTimeCallBackUrl")
	public String CheckTimeCallBackUrl(HttpServletRequest request){
		try{
			CallBack.CheckTimeCallBackUrl(request);
		}catch(Exception e){
		}
		return "success";
	}

	/**
	 * 设置表读数命令回调函数
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("WaterMeterTLVBasicCallBackUrl")
	public String WaterMeterTLVBasicCallBackUrl(HttpServletRequest request){
		try{
			CallBack.WaterMeterTLVBasicCallBackUrl(request);
		}catch(Exception e){
		}
		return "success";
	}


}
