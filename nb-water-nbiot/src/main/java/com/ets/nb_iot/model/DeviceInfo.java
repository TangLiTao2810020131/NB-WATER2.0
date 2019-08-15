package com.ets.nb_iot.model;


/**
 * 
 * @ClassName:     DeviceInfo.java 
 * @Description:   设备信息 对象编号（bn:/3/0）
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午5:14:56
 */
public class DeviceInfo {
    private String manufacturer;// 0 厂商
    private String model;// 1 模块名称
    private String serialNember;// 2 串行号  表地址  
    private String availablePowerSources;// 6 电源类型
    private String powerSourceVoltage;// 7 电源电压
    private String errorCode;// 11 错误码
    private String currentTime;// 13 当前时间 Unix时间
    private String utcOffset; // 14 时区[ISO 8601]
    private String deviceType;// 17 设备类型
    private String hardwareVersion;// 18 硬件版本
    private String softwareVersion;// 19 软件版本
    private String batteryStatus;// 20 电池状态
    private String bn;//对象编码
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerialNember() {
		return serialNember;
	}
	public void setSerialNember(String serialNember) {
		this.serialNember = serialNember;
	}
	public String getAvailablePowerSources() {
		return availablePowerSources;
	}
	public void setAvailablePowerSources(String availablePowerSources) {
		this.availablePowerSources = availablePowerSources;
	}
	public String getPowerSourceVoltage() {
		return powerSourceVoltage;
	}
	public void setPowerSourceVoltage(String powerSourceVoltage) {
		this.powerSourceVoltage = powerSourceVoltage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getUtcOffset() {
		return utcOffset;
	}
	public void setUtcOffset(String utcOffset) {
		this.utcOffset = utcOffset;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	public String getBatteryStatus() {
		return batteryStatus;
	}
	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
	}
	public String getBn() {
		return bn;
	}
	public void setBn(String bn) {
		this.bn = bn;
	}
	@Override
	public String toString() {
		return "DeviceInfo [manufacturer=" + manufacturer + ", model=" + model + ", serialNember=" + serialNember
				+ ", availablePowerSources=" + availablePowerSources + ", powerSourceVoltage=" + powerSourceVoltage
				+ ", errorCode=" + errorCode + ", currentTime=" + currentTime + ", utcOffset=" + utcOffset
				+ ", deviceType=" + deviceType + ", hardwareVersion=" + hardwareVersion + ", softwareVersion="
				+ softwareVersion + ", batteryStatus=" + batteryStatus + ", bn=" + bn + "]";
	}
	
	
}
