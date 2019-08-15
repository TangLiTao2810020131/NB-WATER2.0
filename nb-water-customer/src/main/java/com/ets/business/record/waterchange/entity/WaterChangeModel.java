package com.ets.business.record.waterchange.entity;


/**
 * 水表更换模板类
 * @author wh
 *
 */
public class WaterChangeModel {
	private String ownerid;
	private String equipmentid;
	private String oldbasenum;
	private String watermetercode;
	private String watermeterid;
	private String basenum;
	private String changtime;
	private String optionuser;
	private String changreason;
	private String describe;
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
	public String getOldbasenum() {
		return oldbasenum;
	}
	public void setOldbasenum(String oldbasenum) {
		this.oldbasenum = oldbasenum;
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
	public String getChangtime() {
		return changtime;
	}
	public void setChangtime(String changtime) {
		this.changtime = changtime;
	}
	public String getOptionuser() {
		return optionuser;
	}
	public void setOptionuser(String optionuser) {
		this.optionuser = optionuser;
	}
	public String getChangreason() {
		return changreason;
	}
	public void setChangreason(String changreason) {
		this.changreason = changreason;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
