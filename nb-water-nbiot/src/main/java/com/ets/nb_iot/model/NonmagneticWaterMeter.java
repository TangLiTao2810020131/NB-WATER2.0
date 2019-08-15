package com.ets.nb_iot.model;

/**
 * 无磁表协议
 * @author 吴浩
 * @create 2018-12-26 10:40
 */
public class NonmagneticWaterMeter {
	
	private String preambleCode;
	private String headerByte;
	private String commandType;
	private String errorType;
	private String parLen;
	private String parameter;
	private String checkSum;
	private String terminator;
	public String getPreambleCode() {
		return preambleCode;
	}
	public void setPreambleCode(String preambleCode) {
		this.preambleCode = preambleCode;
	}
	public String getHeaderByte() {
		return headerByte;
	}
	public void setHeaderByte(String headerByte) {
		this.headerByte = headerByte;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getParLen() {
		return parLen;
	}
	public void setParLen(String parLen) {
		this.parLen = parLen;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public String getTerminator() {
		return terminator;
	}
	public void setTerminator(String terminator) {
		this.terminator = terminator;
	}
	@Override
	public String toString() {
		return "NonmagneticWaterMeter [preambleCode=" + preambleCode + ", headerByte=" + headerByte + ", commandType="
				+ commandType + ", errorType=" + errorType + ", parLen=" + parLen + ", parameter=" + parameter
				+ ", checkSum=" + checkSum + ", terminator=" + terminator + "]";
	}
}
