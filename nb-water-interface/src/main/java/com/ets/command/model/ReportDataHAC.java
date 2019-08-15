package com.ets.command.model;

import java.io.Serializable;
import java.util.Map;

public class ReportDataHAC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2053261376551743920L;
    private String bver;
    private String msgType;
    private String code;
    private String msgId;
    private String payloadFormat;
    private Map<String,Object>[] dev;
    private Integer cmdType;
	private String eventTime;
	public String getBver() {
		return bver;
	}
	public void setBver(String bver) {
		this.bver = bver;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPayloadFormat() {
		return payloadFormat;
	}
	public void setPayloadFormat(String payloadFormat) {
		this.payloadFormat = payloadFormat;
	}

	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	public Map<String, Object>[] getDev() {
		return dev;
	}
	public void setDev(Map<String, Object>[] dev) {
		this.dev = dev;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public Integer getCmdType() {
		return cmdType;
	}
	public void setCmdType(Integer cmdType) {
		this.cmdType = cmdType;
	}
}
