package com.ets.nb_iot.cmdinfo.iotinit;

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

	@Value("${nbiot.appName}")
	public String appName;
	@Value("${nbiot.appId}")
	public String appId;
	@Value("${nbiot.appKey}")
	public String appKey;
	@Value("${nbiot.ip}")
	public String ip;
	@Value("${nbiot.port}")
	public String port;

	@Value("${nbiot.device_type}")
	private String device_type;
	@Value("${nbiot.model}")
	private String model;
	@Value("${nbiot.manufacturer_id}")
	private String manufacturer_id;
	@Value("${nbiot.manufacturer_name}")
	private String manufacturer_name;
	@Value("${nbiot.protocol_type}")
	private String protocol_type;

	@Value("${nbiot.callback_ip}")
	private String callback_ip;
	@Value("${nbiot.callback_port}")
	private String callback_port;

	@Value("${nbiot.check_time}")
	private String check_time;
	@Value("${nbiot.delivery}")
	private String delivery;
	@Value("${nbiot.water_meter_basic}")
	private String water_meter_basic;
	@Value("${nbiot.valve_control}")
	private String valve_control;
	@Value("${nbiot.service_name}")
	private String service_name;
	@Value("${nbiot.attribute_name}")
	private String attribute_name;
	
	@Value("${nbiot.callback_check_time_url}")
	private String callback_check_time_url;
	@Value("${nbiot.callback_delivery_url}")
	private String callback_delivery_url;
	@Value("${nbiot.callback_water_meter_basic_url}")
	private String callback_water_meter_basic_url;
	@Value("${nbiot.callback_valve_control_url}")
	private String callback_valve_control_url;
	
	@Value("${nbiot.callback_tlv_basic_url}")
	private String callback_tlv_basic_url;
	
	@Value("${sys.upload_url}")
    private String upload_url;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer_id() {
		return manufacturer_id;
	}

	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}

	public String getManufacturer_name() {
		return manufacturer_name;
	}

	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}

	public String getProtocol_type() {
		return protocol_type;
	}

	public void setProtocol_type(String protocol_type) {
		this.protocol_type = protocol_type;
	}

	public String getCallback_ip() {
		return callback_ip;
	}

	public void setCallback_ip(String callback_ip) {
		this.callback_ip = callback_ip;
	}

	public String getCallback_port() {
		return callback_port;
	}

	public void setCallback_port(String callback_port) {
		this.callback_port = callback_port;
	}

	/**
	 * 时间校验属性
	 * @return 属性值
	 */
	public String getCheck_time() {
		return check_time;
	}

	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}

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

	/**
	 * 命令服务名属性
	 * @return 服务值
	 */
	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	/**
	 * 命令参数属性
	 * @return 属性值
	 */
	public String getAttribute_name() {
		return attribute_name;
	}

	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}

	/**
	 * 时间校验回调方法
	 * @return url
	 */
	public String getCallback_check_time_url() {
		return callback_check_time_url;
	}

	public void setCallback_check_time_url(String callback_check_time_url) {
		this.callback_check_time_url = callback_check_time_url;
	}

	/**
	 * 上周周期回调方法
	 * @return url
	 */
	public String getCallback_delivery_url() {
		return callback_delivery_url;
	}

	public void setCallback_delivery_url(String callback_delivery_url) {
		this.callback_delivery_url = callback_delivery_url;
	}

	/**
	 * 表读数回调方法
	 * @return url
	 */
	public String getCallback_water_meter_basic_url() {
		return callback_water_meter_basic_url;
	}

	public void setCallback_water_meter_basic_url(String callback_water_meter_basic_url) {
		this.callback_water_meter_basic_url = callback_water_meter_basic_url;
	}

	/**
	 * 阀控回调方法
	 * @return url
	 */
	public String getCallback_valve_control_url() {
		return callback_valve_control_url;
	}

	public void setCallback_valve_control_url(String callback_valve_control_url) {
		this.callback_valve_control_url = callback_valve_control_url;
	}

	public String getCallback_tlv_basic_url() {
		return callback_tlv_basic_url;
	}

	public void setCallback_tlv_basic_url(String callback_tlv_basic_url) {
		this.callback_tlv_basic_url = callback_tlv_basic_url;
	}

	public String getUpload_url() {
		return upload_url;
	}

	public void setUpload_url(String upload_url) {
		this.upload_url = upload_url;
	}
}
