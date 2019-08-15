package com.ets.nb_iot.model;

/**
 * 
 * @ClassName:     Signal.java 
 * @Description:   信号交付实体类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午5:15:23
 */
public class Signal {
	private String rssi;//信号强度
	private String snr;//信噪比
	private String bn;// /99/0
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	public String getSnr() {
		return snr;
	}
	public void setSnr(String snr) {
		this.snr = snr;
	}
	public String getBn() {
		return bn;
	}
	public void setBn(String bn) {
		this.bn = bn;
	}
	@Override
	public String toString() {
		return "Signal [rssi=" + rssi + ", snr=" + snr + ", bn=" + bn + "]";
	}
	
	
}
