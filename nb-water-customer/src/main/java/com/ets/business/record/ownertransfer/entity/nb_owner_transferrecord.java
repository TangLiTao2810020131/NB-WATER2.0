package com.ets.business.record.ownertransfer.entity;

/**
 * 用户过户实体类
 * @author wh
 *
 */
public class nb_owner_transferrecord {
	private String id;
	private String customercode;
	private String watermetercode;
	private String olduseraccount;
	private String oldusername;
	private String oldidcard;
	private String newuseraccount;
	private String newuaername;
	private String newidcard;
	private String transferread;
	private String address;
	private String describe;
	private String ctime;
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

	public String getWatermetercode() {
		return watermetercode;
	}
	public void setWatermetercode(String watermetercode) {
		this.watermetercode = watermetercode;
	}
	public String getOlduseraccount() {
		return olduseraccount;
	}
	public void setOlduseraccount(String olduseraccount) {
		this.olduseraccount = olduseraccount;
	}
	public String getOldusername() {
		return oldusername;
	}
	public void setOldusername(String oldusername) {
		this.oldusername = oldusername;
	}
	public String getOldidcard() {
		return oldidcard;
	}
	public void setOldidcard(String oldidcard) {
		this.oldidcard = oldidcard;
	}
	public String getNewuseraccount() {
		return newuseraccount;
	}
	public void setNewuseraccount(String newuseraccount) {
		this.newuseraccount = newuseraccount;
	}
	public String getNewuaername() {
		return newuaername;
	}
	public void setNewuaername(String newuaername) {
		this.newuaername = newuaername;
	}
	public String getNewidcard() {
		return newidcard;
	}
	public void setNewidcard(String newidcard) {
		this.newidcard = newidcard;
	}
	public String getTransferread() {
		return transferread;
	}
	public void setTransferread(String transferread) {
		this.transferread = transferread;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
}
