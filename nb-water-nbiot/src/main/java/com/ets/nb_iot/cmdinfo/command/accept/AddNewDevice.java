package com.ets.nb_iot.cmdinfo.command.accept;

import com.ets.common.Common;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 添加新设备
 * @author wuhao
 *
 */
@Component
@RequestMapping("addnewdevice")
public class AddNewDevice {
	

	String baseUrl = "business/nb_lot/cmdinfo/command/accept/";


	@RequestMapping("JsonToAddNewDevice")
	public String JsonToAddNewDevice(HttpServletRequest request) throws Exception{
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		System.out.println("添加新设备");
		System.out.println(date);
		return baseUrl + "accept-addnewdevice";
	}
	

}
