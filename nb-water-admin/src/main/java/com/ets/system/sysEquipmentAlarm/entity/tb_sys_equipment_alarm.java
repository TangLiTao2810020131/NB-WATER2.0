package com.ets.system.sysEquipmentAlarm.entity;

/**
 *	设备告警
 * @author wuhao
 *
 */
public class tb_sys_equipment_alarm {
	  private String id;
	  private String deviceid;
	  private String imei;
	  private String alarmtype;
	  private String alarmstatus;
	  private String alarmtime;
	  private String ctime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getAlarmtype() {
		return alarmtype;
	}
	public void setAlarmtype(String alarmtype) {
		this.alarmtype = alarmtype;
	}
	public String getAlarmstatus() {
		return alarmstatus;
	}
	public void setAlarmstatus(String alarmstatus) {
		this.alarmstatus = alarmstatus;
	}
	public String getAlarmtime() {
		return alarmtime;
	}
	public void setAlarmtime(String alarmtime) {
		this.alarmtime = alarmtime;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
}
