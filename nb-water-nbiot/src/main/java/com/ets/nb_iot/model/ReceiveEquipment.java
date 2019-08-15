package com.ets.nb_iot.model;

/**
 * 
 * @ClassName:     ReceiveEquipment.java 
 * @Description:   接收设备信息实体类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午4:59:46
 */
public class ReceiveEquipment {
	private String notifyType;
	private String deviceId;
	private String gatewayId;
	private String requestId;
	private String service;
	private String eventTime;
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getGatewayId() {
		return gatewayId;
	}
	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
}
