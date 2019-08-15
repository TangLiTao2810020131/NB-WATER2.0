package com.ets.nb_iot.cmdinfo.command.concurrent;


import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.ets.nb_iot.model.ReportDataHAC;
import com.ets.nb_iot.model.ReportDataTLV;

/**
 * @author 姚轶文
 * @create 2018- 12-11 10:10
 */
public class CmdDelay implements Delayed   {


	private long workTime; //执行时间 单位毫秒，用当前时间的毫秒数+延时时间
    private ReportDataHAC reportData;//需要返送的命令体
    private ReportDataTLV reportDataTLV;
    private String deviceId;//需要执行的设备

    public CmdDelay(){}

    /**
     * 封装命令体
     * @param deviceId 设备ID
     * @param reportData 命令体
     * @param workTime 执行时间
     */
    public CmdDelay(String deviceId,ReportDataHAC reportData , long workTime)
    {
        this.workTime = workTime;
        this.reportData = reportData;
        this.deviceId = deviceId;
    }

    /**
     * 封装命令体
     * @param deviceId 设备ID
     * @param reportData 命令体
     * @param workTime 执行时间
     */
    public CmdDelay(String deviceId,ReportDataTLV reportData , long workTime)
    {
        this.workTime = workTime;
        this.reportDataTLV = reportData;
        this.deviceId = deviceId;
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


    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public ReportDataHAC getReportData() {
        return reportData;
    }

    public void setReportData(ReportDataHAC reportData) {
        this.reportData = reportData;
    }

	public ReportDataTLV getReportDataTLV() {
		return reportDataTLV;
	}

	public void setReportDataTLV(ReportDataTLV reportDataTLV) {
		this.reportDataTLV = reportDataTLV;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
