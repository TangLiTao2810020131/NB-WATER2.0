package com.ets.business.balance.entity;

public class BalanceModel {
	private String useraccount;
	private String username;
    private String startnum;
    private String startnumdate;
    private String endnum;
    private String endnumdate;
    private String balancemonth;
    private String address;
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
	public String getStartnum() {
		return startnum;
	}
	public void setStartnum(String startnum) {
		this.startnum = startnum;
	}
	public String getStartnumdate() {
		return startnumdate;
	}
	public void setStartnumdate(String startnumdate) {
		this.startnumdate = startnumdate;
	}
	public String getEndnum() {
		return endnum;
	}
	public void setEndnum(String endnum) {
		this.endnum = endnum;
	}
	public String getEndnumdate() {
		return endnumdate;
	}
	public void setEndnumdate(String endnumdate) {
		this.endnumdate = endnumdate;
	}
	public String getBalancemonth() {
		return balancemonth;
	}
	public void setBalancemonth(String balancemonth) {
		this.balancemonth = balancemonth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
