package com.ets.nb_iot.cmdinfo.command.accept;

import com.ets.common.Common;
import com.ets.nb_iot.cmdinfo.DataReporting;
import com.ets.nb_iot.cmdinfo.command.concurrent.SpringContextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class DeviceDataChange {

	private static Logger logger = LoggerFactory.getLogger(DeviceDataChange.class);

	@Autowired
	protected ThreadPoolTaskExecutor taskExecutor;

	String baseUrl = "business/nb_lot/cmdinfo/command/accept/";

	/**
	 * 
	* @Title: JsonToDeviceDataChange 
	* @Description: 上报数据解析并存储
	* @param: @param request
	* @return: String    
	* @Date: 2019年7月25日 下午8:20:30  
	* @throws
	 */
	public String JsonToDeviceDataChange(HttpServletRequest request){
		try {
			
			logger.info("start接收电信平台上报数据接口");

			String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);//获取电信平台推送数据
			//String date = "{\"notifyType\":\"deviceDataChanged\",\"deviceId\":\"e85a750a-0285-4f87-9ebc-8d83db3ae039\",\"gatewayId\":\"b20942f1-5f9a-44f5-bfcc-d79295132cd5\",\"requestId\":null,\"service\":{\"serviceId\":\"Reading\",\"serviceType\":\"Reading\",\"data\":{\"reportData\":{\"bver\":1.01,\"msgType\":0,\"code\":2,\"payloadFormat\":60,\"messageId\":45409,\"dev\":[{\"11\":1,\"13\":1526000144,\"14\":\"UTC+8\",\"17\":1,\"18\":\"PV1.2\",\"19\":\"V1.00\",\"bn\":\"/3/0\",\"0\":\"Hac\",\"1\":\"NBh-Y38B\",\"2\":\"1901110007\",\"6\":2,\"7\":359,\"20\":0},{\"0\":\"LXC-20\",\"1\":0,\"16\":0,\"6\":0,\"bn\":\"/80/0\",\"21\":1526000144},{\"1\":0,\"2\":0,\"3\":1,\"bn\":\"/81/0\"},{\"0\":0,\"1\":0,\"bn\":\"/82/0\"},{\"0\":1800,\"1\":130,\"2\":2,\"3\":1200,\"bn\":\"/84/0\"},{\"1\":\"862458041570877\",\"13\":-674,\"14\":38,\"bn\":\"/99/0\"}]}},\"eventTime\":\"20190228T014219Z\"}}";

			logger.info("设备数据变化-date:" + date);

			if("push success.".equals(date)){
				return "error";
			}
			
			DataReporting task = SpringContextUtils.getContext().getBean(DataReporting.class);
			task.setDate(date);
			task.setRequest(request);
			taskExecutor.execute(task);
			
			logger.info("end接收电信平台上报数据接口");

			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
