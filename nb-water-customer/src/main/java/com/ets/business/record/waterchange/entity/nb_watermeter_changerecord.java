package com.ets.business.record.waterchange.entity;

/**
 * 水表更换实体类
 * @author wh
 *
 */
public class nb_watermeter_changerecord {
	private String id;
	private String customercode      ;
	private String useraccount       ;
	private String username          ;
	private String idcard			 ;
	private String address           ;
	private String oldwatermetercode ;
	private String oldbasenum        ;
	private String newwatermetercode ;
	private String newbasenum        ;
	private String phone             ;
	private String optionuser        ;
	private String optionusername    ;
	private String changtime         ;
	private String changreason       ;
	private String describe          ;
	private String ctime             ;
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
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOldwatermetercode() {
		return oldwatermetercode;
	}
	public void setOldwatermetercode(String oldwatermetercode) {
		this.oldwatermetercode = oldwatermetercode;
	}
	public String getOldbasenum() {
		return oldbasenum;
	}
	public void setOldbasenum(String oldbasenum) {
		this.oldbasenum = oldbasenum;
	}
	public String getNewwatermetercode() {
		return newwatermetercode;
	}
	public void setNewwatermetercode(String newwatermetercode) {
		this.newwatermetercode = newwatermetercode;
	}
	public String getNewbasenum() {
		return newbasenum;
	}
	public void setNewbasenum(String newbasenum) {
		this.newbasenum = newbasenum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOptionuser() {
		return optionuser;
	}
	public void setOptionuser(String optionuser) {
		this.optionuser = optionuser;
	}
	public String getOptionusername() {
		return optionusername;
	}
	public void setOptionusername(String optionusername) {
		this.optionusername = optionusername;
	}
	public String getChangtime() {
		return changtime;
	}
	public void setChangtime(String changtime) {
		this.changtime = changtime;
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
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
}
