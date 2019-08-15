package com.ets.nb_iot.model;

public class Delivery {
	
    private String deliveryFrequency;// 0 上报周期
    private String reportTime;// 1 上报时隙
    private String retries;// 2 重发次数
    private String retryPeriod; // 3 重发周期
	public String getDeliveryFrequency() {
		return deliveryFrequency;
	}
	public void setDeliveryFrequency(String deliveryFrequency) {
		this.deliveryFrequency = deliveryFrequency;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public String getRetries() {
		return retries;
	}
	public void setRetries(String retries) {
		this.retries = retries;
	}
	public String getRetryPeriod() {
		return retryPeriod;
	}
	public void setRetryPeriod(String retryPeriod) {
		this.retryPeriod = retryPeriod;
	}
}
