package com.ets.business.remote.entity;

/**
 * @author 姚轶文
 * @create 2018- 11-21 9:08
 */
public class RemoteListEntity {

    private String wId; //水表ID
    private String oId; // 业主ID
    private String dId; //户号ID
    private String userName; //业主名称
    private String userCode; //水表户号
    private String address; //详细地址
    //private String floodgate; //水阀状态
    private String isFloodgate; //有无水阀 1开启 0关闭  -1无阀
    private String ismagnetism;//是否有磁
    private String waterValue; //水表最新读书
    private String ltime; //最新抄表时间
    private String InitName; //小区名称
    private String building; //栋
    private String doornum;//户
    private String deviceId; // 设备在NB平台的ID
    
    private String watermetercode;

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getIsFloodgate() {
        return isFloodgate;
    }

    public void setIsFloodgate(String isFloodgate) {
        this.isFloodgate = isFloodgate;
    }

    public String getIsmagnetism() {
		return ismagnetism;
	}

	public void setIsmagnetism(String ismagnetism) {
		this.ismagnetism = ismagnetism;
	}

	public String getWaterValue() {
        return waterValue;
    }

    public void setWaterValue(String waterValue) {
        this.waterValue = waterValue;
    }

    public String getLtime() {
        return ltime;
    }

    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

    public String getInitName() {
        return InitName;
    }

    public void setInitName(String initName) {
        InitName = initName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDoornum() {
        return doornum;
    }

    public void setDoornum(String doornum) {
        this.doornum = doornum;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

	public String getWatermetercode() {
		return watermetercode;
	}

	public void setWatermetercode(String watermetercode) {
		this.watermetercode = watermetercode;
	}
}
