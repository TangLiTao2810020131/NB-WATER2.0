package com.ets.nb_iot.model;

/**
 * UTC时间
 * @author 吴浩
 * @create 2018-12-26 11:10
 */
public class UTCTime {

    private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "UTCTime [time=" + time + "]";
	}
}
