package com.ets.business.daelaytime.entity;

/**
 * 
 * @ClassName:     nb_delay_time_delivery.java 
 * @Description:   上报周期延迟时间
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:34:51
 */
public class nb_delay_time_delivery {

	private String id;
	private String value;
	private String customerId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	
}
