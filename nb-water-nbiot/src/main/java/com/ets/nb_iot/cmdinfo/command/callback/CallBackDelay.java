package com.ets.nb_iot.cmdinfo.command.callback;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class CallBackDelay implements Delayed   {
	
	private String commandId;
	private String deviceId;
	private String type;
	private long workTime; //执行时间 单位毫秒，用当前时间的毫秒数+延时时间
	
	
    private CallBackDelay(){}

    /**
     * 
     * @param commandId 命令ID
     * @param deviceId  设备ID
     * @param type 命令类型
     * @param workTime 延迟执行时间
     */
    public CallBackDelay(String commandId,String deviceId,String type,long workTime)
    {
    	this.commandId = commandId;
    	this.deviceId = deviceId;
    	this.type = type;
        this.workTime = workTime;
    }

    @Override
    public long getDelay(TimeUnit unit)
    {
    	return unit.convert(workTime - System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o)
    {
        if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
            return -1;
        else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
            return 1;
        else
            return 0;
    }

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	
	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getWorkTime() {
		return workTime;
	}

	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}
	
	

}
