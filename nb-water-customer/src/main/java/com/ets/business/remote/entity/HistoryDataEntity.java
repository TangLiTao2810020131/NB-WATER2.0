package com.ets.business.remote.entity;

/**
 * @author 姚轶文
 * @create 2018- 11-21 16:10
 */
public class HistoryDataEntity {

    private String serviceId; //服务ID
    private String deviceId; //设备ID
    private String getwayId; //网关
    private String data; //数据
    private String upTime; //上报时间

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGetwayId() {
        return getwayId;
    }

    public void setGetwayId(String getwayId) {
        this.getwayId = getwayId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }
}
