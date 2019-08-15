package com.ets.business.readlog.entity;

/**
 * 
 * @ClassName:     nb_read_log.java 
 * @Description:   上报数据实体类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:51:19
 */
public class nb_read_log {

	private String id;
	private String content;
	private String imei;
	private String ctime;
	private String deviceId;
	private String customercode;
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
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
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
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
}
