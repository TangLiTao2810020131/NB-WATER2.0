package com.ets.common;

import java.util.List;

public class EleTree {
	private String id;
	private String label;
	private boolean isLeaf;
	private List<EleTree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public List<EleTree> getChildren() {
		return children;
	}
	public void setChildren(List<EleTree> children) {
		this.children = children;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}



}
