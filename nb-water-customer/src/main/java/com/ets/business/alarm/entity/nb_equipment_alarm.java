package com.ets.business.alarm.entity;

/**
 *	设备告警
 * @author wuhao
 *
 */
public class nb_equipment_alarm {
	  private String id;
	  private String customercode;
	  private String useraccount;
	  private String username;
	  private String address;
	  private String watermetercode;
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
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWatermetercode() {
		return watermetercode;
	}
	public void setWatermetercode(String watermetercode) {
		this.watermetercode = watermetercode;
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
