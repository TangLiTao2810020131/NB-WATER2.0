package com.ets.business.equipment.entity;

/**
 * 水表设备实体类
 * @author Administrator
 *
 */
public class nb_watermeter_equipment {

	private String id;
	private String customercode;
	private String watermetercode;
	private String watermeterid;
	private String basenum;
	private String optionuser;
	private String operationmode;
	private String status;
	private String productiondate;
	private String productionnum;
	private String installdate;
	private String describe;
	private String ctime;
	private String doornumid;
	private String deviceid;
	private String isonline;
	private String psvoltage;
	private String rssi;
	private String snr;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getWatermetercode() {
		return watermetercode;
	}
	public void setWatermetercode(String watermetercode) {
		this.watermetercode = watermetercode;
	}
	public String getWatermeterid() {
		return watermeterid;
	}
	public void setWatermeterid(String watermeterid) {
		this.watermeterid = watermeterid;
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
	public String getOperationmode() {
		return operationmode;
	}
	public void setOperationmode(String operationmode) {
		this.operationmode = operationmode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductiondate() {
		return productiondate;
	}
	public void setProductiondate(String productiondate) {
		this.productiondate = productiondate;
	}
	public String getProductionnum() {
		return productionnum;
	}
	public void setProductionnum(String productionnum) {
		this.productionnum = productionnum;
	}
	public String getInstalldate() {
		return installdate;
	}
	public void setInstalldate(String installdate) {
		this.installdate = installdate;
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
	public String getDoornumid() {
		return doornumid;
	}
	public void setDoornumid(String doornumid) {
		this.doornumid = doornumid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
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
	@Override
	public String toString() {
		return "nb_watermeter_equipment{" +
				"id='" + id + '\'' +
				", customercode='" + customercode + '\'' +
				", watermetercode='" + watermetercode + '\'' +
				", watermeterid='" + watermeterid + '\'' +
				", basenum='" + basenum + '\'' +
				", optionuser='" + optionuser + '\'' +
				", operationmode='" + operationmode + '\'' +
				", status='" + status + '\'' +
				", productiondate='" + productiondate + '\'' +
				", productionnum='" + productionnum + '\'' +
				", installdate='" + installdate + '\'' +
				", describe='" + describe + '\'' +
				", ctime='" + ctime + '\'' +
				", doornumid='" + doornumid + '\'' +
				", deviceid='" + deviceid + '\'' +
				'}';
	}
}
