package com.ets.nb_iot.model;

/**
 * 信号质量
 * @author 吴浩
 * @create 2018-12-26 10:36
 */
public class SignalQuality {
	private String signalIntensity;//信号强度
	private String signalNoiseRatio;//信噪比
	private String coverageLevel;//覆盖等级
	private String cellNumber;//小区号
	private String signalLevel;//信号等级
	public String getSignalIntensity() {
		return signalIntensity;
	}
	public void setSignalIntensity(String signalIntensity) {
		this.signalIntensity = signalIntensity;
	}
	public String getSignalNoiseRatio() {
		return signalNoiseRatio;
	}
	public void setSignalNoiseRatio(String signalNoiseRatio) {
		this.signalNoiseRatio = signalNoiseRatio;
	}
	public String getCoverageLevel() {
		return coverageLevel;
	}
	public void setCoverageLevel(String coverageLevel) {
		this.coverageLevel = coverageLevel;
	}
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	public String getSignalLevel() {
		return signalLevel;
	}
	public void setSignalLevel(String signalLevel) {
		this.signalLevel = signalLevel;
	}
	@Override
	public String toString() {
		return "SignalQuality [signalIntensity=" + signalIntensity + ", signalNoiseRatio=" + signalNoiseRatio
				+ ", coverageLevel=" + coverageLevel + ", cellNumber=" + cellNumber + ", signalLevel=" + signalLevel
				+ "]";
	}
	
	
}
