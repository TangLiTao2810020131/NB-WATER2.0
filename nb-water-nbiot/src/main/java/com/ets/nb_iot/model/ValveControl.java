package com.ets.nb_iot.model;


/**
 * 
 * @ClassName:     ValveControl.java 
 * @Description:   阀门（对象编码：bn：/81/0） 
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午5:14:43
 */
public class ValveControl {

	
    private String ValveControl;// 0 阀门控制字
    private String ValveCurrentStatus;// 1 阀门状态
    private String ValvefaultStatus;// 2 阀门故障状态
    private String type;
    private String bn;// /81/0
	

    public String getValveControl() {
		return ValveControl;
	}


	public void setValveControl(String valveControl) {
		ValveControl = valveControl;
	}


	public String getValveCurrentStatus() {
		return ValveCurrentStatus;
	}


	public void setValveCurrentStatus(String valveCurrentStatus) {
		ValveCurrentStatus = valveCurrentStatus;
	}


	public String getValvefaultStatus() {
		return ValvefaultStatus;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setValvefaultStatus(String valvefaultStatus) {
		ValvefaultStatus = valvefaultStatus;
	}


	public String getBn() {
		return bn;
	}


	public void setBn(String bn) {
		this.bn = bn;
	}


	@Override
	public String toString() {
		return "ValveControl [ValveControl=" + ValveControl + ", ValveCurrentStatus=" + ValveCurrentStatus
				+ ", ValvefaultStatus=" + ValvefaultStatus + ", type=" + type + ", bn=" + bn + "]";
	}

	
}
