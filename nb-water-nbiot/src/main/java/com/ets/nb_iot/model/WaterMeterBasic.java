package com.ets.nb_iot.model;


/**
 * 
 * @ClassName:     WaterMeterBasic.java 
 * @Description:   水表基础信息（对象编码：bn：/80/0）
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午5:14:29
 */
public class WaterMeterBasic {
    private String watermetertype;// 0 水表类型
    private String measurementmodel;// 1 计量模式
    private String measurementfaultStatus;// 6 计量错误状态
    private String waterRead;// 16 表读数
    private String readMeterTime;// 22 SN
    private String bn;//对象编码
	public String getWatermetertype() {
		return watermetertype;
	}
	public void setWatermetertype(String watermetertype) {
		this.watermetertype = watermetertype;
	}
	public String getMeasurementmodel() {
		return measurementmodel;
	}
	public void setMeasurementmodel(String measurementmodel) {
		this.measurementmodel = measurementmodel;
	}
	public String getMeasurementfaultStatus() {
		return measurementfaultStatus;
	}
	public void setMeasurementfaultStatus(String measurementfaultStatus) {
		this.measurementfaultStatus = measurementfaultStatus;
	}

	public String getWaterRead() {
		return waterRead;
	}
	public void setWaterRead(String waterRead) {
		this.waterRead = waterRead;
	}
	public String getReadMeterTime() {
		return readMeterTime;
	}
	public void setReadMeterTime(String readMeterTime) {
		this.readMeterTime = readMeterTime;
	}
	public String getBn() {
		return bn;
	}
	public void setBn(String bn) {
		this.bn = bn;
	}
	@Override
	public String toString() {
		return "WaterMeterBasic [watermetertype=" + watermetertype + ", measurementmodel=" + measurementmodel
				+ ", measurementfaultStatus=" + measurementfaultStatus + ", waterRead=" + waterRead + ", readMeterTime="
				+ readMeterTime + ", bn=" + bn + "]";
	}

	
}
