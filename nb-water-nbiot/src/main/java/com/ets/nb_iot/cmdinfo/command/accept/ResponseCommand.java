package com.ets.nb_iot.cmdinfo.command.accept;

import com.ets.common.Common;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping("responsecommand")
public class ResponseCommand {
	

	String baseUrl = "business/nb_lot/cmdinfo/command/accept/";
	


	@RequestMapping("JsonToResponseCommand")
	public String JsonToResponseCommand(HttpServletRequest request) throws Exception{
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		System.out.println("响应命令");
		System.out.println(date);
		return baseUrl + "accept-responsecommand";
	}
	

}
