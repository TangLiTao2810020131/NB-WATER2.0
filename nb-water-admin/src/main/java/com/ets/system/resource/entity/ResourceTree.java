package com.ets.system.resource.entity;

import java.util.List;

/**
 * @author: 姚轶文
 * @date:2018年8月27日 下午3:32:16
 * @version :
 * 
 */
public class ResourceTree {

	private String name;
	private String id;
	private String pid;
	private String url;
	private List<ResourceTree> children;
	
	
	public ResourceTree() {
		super();
	}
	public ResourceTree(String name) {
		super();
		this.name = name;
	}
	public ResourceTree(String name, List<ResourceTree> children) {
		super();
		this.name = name;
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ResourceTree> getChildren() {
		return children;
	}
	public void setChildren(List<ResourceTree> children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

	
	
}
