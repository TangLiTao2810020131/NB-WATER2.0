package com.ets.system.resource.entity;

/**
 * @author: 姚轶文
 * @date:2018年8月27日 上午10:03:10
 * @version :
 * 
 */
public class tb_resource {

	private String id;
	private String descr;
	private String ismenu;
	private String pid;
	private String resourcename;
	private String resourceurl;
	private String ctime;
	




	public tb_resource(String descr, String ismenu, String pid, String resourcename, String resourceurl, String ctime) {
		super();
		this.descr = descr;
		this.ismenu = ismenu;
		this.pid = pid;
		this.resourcename = resourcename;
		this.resourceurl = resourceurl;
		this.ctime = ctime;
	}

	public tb_resource(String id, String descr, String ismenu, String pid, String resourcename, String resourceurl,
			String ctime) {
		super();
		this.id = id;
		this.descr = descr;
		this.ismenu = ismenu;
		this.pid = pid;
		this.resourcename = resourcename;
		this.resourceurl = resourceurl;
		this.ctime = ctime;
	}

	public tb_resource() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getIsmenu() {
		return ismenu;
	}

	public void setIsmenu(String ismenu) {
		this.ismenu = ismenu;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}


	
}
