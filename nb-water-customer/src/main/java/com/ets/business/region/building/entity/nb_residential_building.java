package com.ets.business.region.building.entity;

import java.sql.Timestamp;

/**
 * 小区楼栋实体类
 * @author wh
 *
 */
public class nb_residential_building {
	
	  private String id;
	  private String customercode;
	  private String residentialid;
	  private String building;
	  private String describe;
	  private Timestamp ctime;
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
	public String getResidentialid() {
		return residentialid;
	}
	public void setResidentialid(String residentialid) {
		this.residentialid = residentialid;
	}
	
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	
}
