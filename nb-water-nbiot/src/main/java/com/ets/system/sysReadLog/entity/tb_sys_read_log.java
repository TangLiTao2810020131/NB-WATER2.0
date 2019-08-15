package com.ets.system.sysReadLog.entity;

public class tb_sys_read_log {

	private String id;
	private String imei;
	private String content;
	private String ctime;
	private String deviceId;
	private String baseNum;
	private String ipaddress;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBaseNum() {
		return baseNum;
	}
	public void setBaseNum(String baseNum) {
		this.baseNum = baseNum;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
}
