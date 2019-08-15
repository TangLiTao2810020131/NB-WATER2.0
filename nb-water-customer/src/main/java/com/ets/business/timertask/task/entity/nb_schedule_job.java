package com.ets.business.timertask.task.entity;

import java.io.Serializable;

/**
 * 计划任务
 * @author WH
 *
 */
public class nb_schedule_job implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jobid;//任务ID
	private String customercode;//所属客户ID
	private String jobname;//任务名称
	private String jobgroup;//任务分组
	private String jobstatus;//任务状态 是否启动任务
	private String cronexpression;//cron表达式
	private String beanclass;//任务执行时调用哪个类的方法 包名+类名
	private String isconcurrent;//任务是否有状态
	private String springid;//spring bean 类
	private String methodname;//任务调用的方法名
	private String executordate;
	private String description;
	private String createtime;
	private String updatetime;
	public String getJobid() {
		return jobid;
	}
	public void setJobid(String jobid) {
		this.jobid = jobid;
	}
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getJobgroup() {
		return jobgroup;
	}
	public void setJobgroup(String jobgroup) {
		this.jobgroup = jobgroup;
	}
	public String getJobstatus() {
		return jobstatus;
	}
	public void setJobstatus(String jobstatus) {
		this.jobstatus = jobstatus;
	}
	public String getCronexpression() {
		return cronexpression;
	}
	public void setCronexpression(String cronexpression) {
		this.cronexpression = cronexpression;
	}
	public String getBeanclass() {
		return beanclass;
	}
	public void setBeanclass(String beanclass) {
		this.beanclass = beanclass;
	}
	public String getIsconcurrent() {
		return isconcurrent;
	}
	public void setIsconcurrent(String isconcurrent) {
		this.isconcurrent = isconcurrent;
	}
	public String getSpringid() {
		return springid;
	}
	public void setSpringid(String springid) {
		this.springid = springid;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getExecutordate() {
		return executordate;
	}
	public void setExecutordate(String executordate) {
		this.executordate = executordate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}