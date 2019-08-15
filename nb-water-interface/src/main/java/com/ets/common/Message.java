package com.ets.common;

public class Message {
	private String status;
	private String msg;
	private Object body;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
	public Message(String status,String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public Message(String status,String msg,Object body){
		this.status = status;
		this.msg = msg;
		this.body = body;
	}
}
