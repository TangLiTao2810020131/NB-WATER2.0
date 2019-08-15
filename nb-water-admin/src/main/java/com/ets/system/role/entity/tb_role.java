package com.ets.system.role.entity;

/**
 * @author: 姚轶文
 * @date:2018年8月30日 下午3:06:43
 * @version :
 * 
 */
public class tb_role {

	private String id;
	private String rolename;
	private String descr;
	private String ctime;

	public tb_role() {
		super();
	}

	public tb_role(String id, String rolename, String descr, String ctime) {
		super();
		this.id = id;
		this.rolename = rolename;
		this.descr = descr;
		this.ctime = ctime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

}
