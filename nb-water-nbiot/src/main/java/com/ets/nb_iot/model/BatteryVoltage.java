package com.ets.nb_iot.model;

/**
 * 电池电压
 * @author 吴浩
 * @create 2018-12-26 11:08
 */
public class BatteryVoltage {
    private String voltageValue;

	public String getVoltageValue() {
		return voltageValue;
	}

	public void setVoltageValue(String voltageValue) {
		this.voltageValue = voltageValue;
	}

	@Override
	public String toString() {
		return "BatteryVoltage [voltageValue=" + voltageValue + "]";
	}
}
