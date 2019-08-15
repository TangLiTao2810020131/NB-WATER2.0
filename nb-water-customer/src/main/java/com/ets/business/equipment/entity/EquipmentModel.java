package com.ets.business.equipment.entity;

/**
 * 水表设备模板类
 * @author wh
 *
 */
public class EquipmentModel {
	
	private String id;
	private String watermetercode;
	private String name;
    private String basenum;
    private String optionuser;
    private String status;
    private String installdate;
    private String address;
    private String describe;
    private String ctime;
	private String deviceid;
	private String doornumid;
	private String type;
	private String isonline;
	private String psvoltage;
	private String rssi;
	private String snr;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWatermetercode() {
		return watermetercode;
	}
	public void setWatermetercode(String watermetercode) {
		this.watermetercode = watermetercode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBasenum() {
		return basenum;
	}
	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}

	public String getOptionuser() {
		return optionuser;
	}
	public void setOptionuser(String optionuser) {
		this.optionuser = optionuser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInstalldate() {
		return installdate;
	}
	public void setInstalldate(String installdate) {
		this.installdate = installdate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getDoornumid() {
		return doornumid;
	}
	public void setDoornumid(String doornumid) {
		this.doornumid = doornumid;
	}
	public String getIsonline() {
		return isonline;
	}
	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	public String getPsvoltage() {
		return psvoltage;
	}
	public void setPsvoltage(String psvoltage) {
		this.psvoltage = psvoltage;
	}
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	public String getSnr() {
		return snr;
	}
	public void setSnr(String snr) {
		this.snr = snr;
	}

}
