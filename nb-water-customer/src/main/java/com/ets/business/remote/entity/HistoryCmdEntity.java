package com.ets.business.remote.entity;

/**
 * @author 姚轶文
 * @create 2018- 11-26 21:55
 */
public class HistoryCmdEntity {

    private String commandId; //命令ID
    private String appId; //第三方应用身份标识
    private String deviceId; //设备ID
    private String serviceId; //命令对应的服务ID
    private String method;  //命令服务下具体命令名称
    private String paras; //命令参数的jsonString
    private String callbackUrl; //命令状态变化的回调地址，失败、成功、超时、发送、已送达
    private String expireTime; //下发命令的超时时间，单位秒，默认48小时
    private String status; //命令执行状态
    private String result; //下发命令执行的详细结果
    private String creationTime; //命令创建的时间
    private String executeTime; //命令执行的时间
    private String platformIssuedTime; //平台发送命令的时间
    private String deliveredTime;   //平台将命令送达设备的时间
    private String issuedTimes;     //平台发送命令的次数
    private Integer maxRetransmit; //命令下发最大重传次数

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getPlatformIssuedTime() {
        return platformIssuedTime;
    }

    public void setPlatformIssuedTime(String platformIssuedTime) {
        this.platformIssuedTime = platformIssuedTime;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public String getIssuedTimes() {
        return issuedTimes;
    }

    public void setIssuedTimes(String issuedTimes) {
        this.issuedTimes = issuedTimes;
    }

    public Integer getMaxRetransmit() {
        return maxRetransmit;
    }

    public void setMaxRetransmit(Integer maxRetransmit) {
        this.maxRetransmit = maxRetransmit;
    }
}
