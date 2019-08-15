package com.ets.nb_iot.cmdinfo.command.accept;


import com.ets.common.Common;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping("batchequipmentchange")
public class BatchEquipmentChange {
	

	String baseUrl = "business/nb_lot/cmdinfo/command/accept/";
	


	@RequestMapping("JsonToBatchEquipmentChange")
	public String JsonToBatchEquipmentChange(HttpServletRequest request) throws Exception{
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		System.out.println("设备数据批量变化");
		System.out.println(date);
		return baseUrl + "accept-batchequipmentchange";
	}
	

}
