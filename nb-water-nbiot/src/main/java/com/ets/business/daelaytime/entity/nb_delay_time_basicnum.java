package com.ets.business.daelaytime.entity;

/**
 * 
 * @ClassName:     nb_delay_time_basicnum.java 
 * @Description:   设置读数延迟时间
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:34:43
 */
public class nb_delay_time_basicnum {

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
