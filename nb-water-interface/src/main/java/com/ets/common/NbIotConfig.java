package com.ets.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @create 2018- 11-13 10:50
 */
@Service
@PropertySource("classpath:nbiot.properties")
public class NbIotConfig {

	@Value("${nbiot.delivery}")
	private String delivery;
	@Value("${nbiot.water_meter_basic}")
	private String water_meter_basic;
	@Value("${nbiot.valve_control}")
	private String valve_control;

	/**
	 * 上报周期属性
	 * @return 属性值
	 */
	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	/**
	 * 基础数据属性
	 * @return 属性值
	 */
	public String getWater_meter_basic() {
		return water_meter_basic;
	}

	public void setWater_meter_basic(String water_meter_basic) {
		this.water_meter_basic = water_meter_basic;
	}

	/**
	 * 阀控属性
	 * @return 属性值
	 */
	public String getValve_control() {
		return valve_control;
	}

	public void setValve_control(String valve_control) {
		this.valve_control = valve_control;
	}

}
