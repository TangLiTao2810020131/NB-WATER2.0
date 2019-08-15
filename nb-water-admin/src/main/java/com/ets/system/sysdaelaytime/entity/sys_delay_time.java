package com.ets.system.sysdaelaytime.entity;

/**
 * 设置读数延迟时间
 * @author wuhao
 *
 */
public class sys_delay_time {
	private String id;
	private String  basicnumtime;//表读数延迟执行时间
	private String  deliverytime;//上报周期延迟执行时间
	private String  valvecontroltime;//阀控延迟执行时间
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBasicnumtime() {
		return basicnumtime;
	}
	public void setBasicnumtime(String basicnumtime) {
		this.basicnumtime = basicnumtime;
	}
	public String getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(String deliverytime) {
		this.deliverytime = deliverytime;
	}
	public String getValvecontroltime() {
		return valvecontroltime;
	}
	public void setValvecontroltime(String valvecontroltime) {
		this.valvecontroltime = valvecontroltime;
	}
	
}
