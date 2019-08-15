package com.ets.business.init.price.entity;
/**
 * @author: 姚轶文
 * @date:2018年11月7日 下午3:28:55
 * @version :
 * 
 */
public class nb_price_dic {

	private String id;
	private float price;
	private String customerId;
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
