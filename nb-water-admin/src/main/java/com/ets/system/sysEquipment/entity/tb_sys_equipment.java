package com.ets.system.sysEquipment.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统水表设备实体类
 * @author Administrator
 *
 */
public class tb_sys_equipment {
	private String uuid;
	private String deviceid;
	private String batchid;
	private String id;
	private String imei;
	private String basenum;
	private String control;
	private String cstatus;
	private String dstatus;
	private String ctime;
	

	
	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public String getDeviceid() {
		return deviceid;
	}



	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}



	public String getBatchid() {
		return batchid;
	}



	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getImei() {
		return imei;
	}



	public void setImei(String imei) {
		this.imei = imei;
	}



	public String getBasenum() {
		return basenum;
	}



	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}



	public String getControl() {
		return control;
	}



	public void setControl(String control) {
		this.control = control;
	}



	public String getCstatus() {
		return cstatus;
	}



	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}



	public String getDstatus() {
		return dstatus;
	}



	public void setDstatus(String dstatus) {
		this.dstatus = dstatus;
	}



	public String getCtime() {
		return ctime;
	}



	public void setCtime(String ctime) {
		this.ctime = ctime;
	}



	public static List<tb_sys_equipment> getList(){
		List<tb_sys_equipment> list = new ArrayList<tb_sys_equipment>();
		
		tb_sys_equipment  equipment = new tb_sys_equipment();
		equipment.setId("201905160001");
		equipment.setImei("865118047788397");
		
		tb_sys_equipment  equipment1 = new tb_sys_equipment();
		equipment1.setId("201905160002");
		equipment1.setImei("865118047788264");
		
		tb_sys_equipment  equipment2 = new tb_sys_equipment();
		equipment2.setId("201905160003");
		equipment2.setImei("865118047788090");
		
		tb_sys_equipment  equipment3 = new tb_sys_equipment();
		equipment3.setId("201905160004");
		equipment3.setImei("865118047787233");
		
		tb_sys_equipment  equipment4 = new tb_sys_equipment();
		equipment4.setId("201905160005");
		equipment4.setImei("865118047788116");
		
		list.add(equipment);
		list.add(equipment1);
		list.add(equipment2);
		list.add(equipment3);
		list.add(equipment4);
		
		return list;
	}
}
