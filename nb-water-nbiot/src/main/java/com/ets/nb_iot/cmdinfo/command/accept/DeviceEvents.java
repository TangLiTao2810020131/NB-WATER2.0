package com.ets.nb_iot.cmdinfo.command.accept;

import com.ets.common.Common;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping("deviceevents")
public class DeviceEvents {
	

	String baseUrl = "business/nb_lot/cmdinfo/command/accept/";
	


	@RequestMapping("JsonToDeviceEvents")
	public String JsonToDeviceEvents(HttpServletRequest request) throws Exception{
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		System.out.println("设备事件");
		System.out.println(date);
		return baseUrl + "accept-deviceevents";
	}
	

}
