package com.ets.nb_iot.model;

import java.io.Serializable;

/**
 * @author 吴浩
 * @create 2018-12-25 20:33
 */
public class ReportDataTLV implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2281264529074933459L;
	private String bver;
    private String msgType;
    private String code;
    private String payloadFormat;
    private String msgId;
    private String raw;
    private Integer cmdType;

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

	public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

	public Integer getCmdType() {
		return cmdType;
	}

	public void setCmdType(Integer cmdType) {
		this.cmdType = cmdType;
	}
}
