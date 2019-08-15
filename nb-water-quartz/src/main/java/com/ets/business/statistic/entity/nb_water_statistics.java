package com.ets.business.statistic.entity;

/**
 * 
 * @ClassName:     nb_water_statistics.java 
 * @Description:   水表统计类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午6:33:02
 */
public class nb_water_statistics {

	private String id;
	private String ownerid;
	private String equipmentid;
	private String useraccount;
	private String username;
	private String degrees;
	private String residentialid;
	private String ctime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public String getEquipmentid() {
		return equipmentid;
	}
	public void setEquipmentid(String equipmentid) {
		this.equipmentid = equipmentid;
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
	public String getDegrees() {
		return degrees;
	}
	public void setDegrees(String degrees) {
		this.degrees = degrees;
	}
	public String getResidentialid() {
		return residentialid;
	}
	public void setResidentialid(String residentialid) {
		this.residentialid = residentialid;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
}
