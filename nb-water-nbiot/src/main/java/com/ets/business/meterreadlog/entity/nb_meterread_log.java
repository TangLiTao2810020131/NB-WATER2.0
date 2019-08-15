package com.ets.business.meterreadlog.entity;

/**
 * 
 * @ClassName:     nb_meterread_log.java 
 * @Description:   最新抄表日志记录类 
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午7:48:31
 */
public class nb_meterread_log {
	  private String id           ;
	  private String watermeterid ;
	  private String value        ;
	  private String type         ;
	  private String optionuser   ;
	  private String optiontime	  ;
	  private String issuccess    ;
	  private String messgae      ;
	  private String ctime        ;
	  private String customercode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWatermeterid() {
		return watermeterid;
	}
	public void setWatermeterid(String watermeterid) {
		this.watermeterid = watermeterid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOptionuser() {
		return optionuser;
	}
	public void setOptionuser(String optionuser) {
		this.optionuser = optionuser;
	}

	public String getOptiontime() {
		return optiontime;
	}

	public void setOptiontime(String optiontime) {
		this.optiontime = optiontime;
	}

	public String getIssuccess() {
		return issuccess;
	}
	public void setIssuccess(String issuccess) {
		this.issuccess = issuccess;
	}
	public String getMessgae() {
		return messgae;
	}
	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getCustomercode() {
		return customercode;
	}

	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
}
