package com.ets.business.timertask.taskrecord.entity;


/**
 * @author WH
 *
 */
public class nb_schedule_job_log {
	  private String id           ;
	  private String customercode ;
	  private String jobid      ;
	  private String exectime     ;
	  private String execendtime  ;
	  private String issuccess    ;
	  private String message      ;
	  private String ctime        ;
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}

	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
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
