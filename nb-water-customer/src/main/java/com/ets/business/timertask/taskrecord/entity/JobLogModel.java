package com.ets.business.timertask.taskrecord.entity;

public class JobLogModel {
	  private String jobname      ;
	  private String exectime     ;
	  private String execendtime  ;
	  private String issuccess    ;
	  private String message      ;
	  private String ctime;
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getExectime() {
		return exectime;
	}
	public void setExectime(String exectime) {
		this.exectime = exectime;
	}
	public String getExecendtime() {
		return execendtime;
	}
	public void setExecendtime(String execendtime) {
		this.execendtime = execendtime;
	}
	public String getIssuccess() {
		return issuccess;
	}
	public void setIssuccess(String issuccess) {
		this.issuccess = issuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
}
