package com.ets.system.resource.entity;
/**
 * @author: 姚轶文
 * @date:2018年8月29日 下午2:10:31
 * @version :
 * 
 */
public class ZtreeNode {

	private String id;
	private String pId;
	private String name;
	private boolean open;
	private boolean checked;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
