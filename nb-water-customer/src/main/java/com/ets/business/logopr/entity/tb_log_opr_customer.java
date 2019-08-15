package com.ets.business.logopr.entity;
/**
 * @author: 姚轶文
 * @date:2018年9月5日 下午4:13:39
 * @version :
 * 
 */
public class tb_log_opr_customer {

	private String id;
	private String modulename;
	private String username;
	private String oprcontent;
	private String oprtime;
	private String ip;
	private String customercode;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOprcontent() {
		return oprcontent;
	}
	public void setOprcontent(String oprcontent) {
		this.oprcontent = oprcontent;
	}
	public String getOprtime() {
		return oprtime;
	}
	public void setOprtime(String oprtime) {
		this.oprtime = oprtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCustomercode() {
		return customercode;
	}

	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
}
