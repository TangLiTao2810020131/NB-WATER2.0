package com.ets.business.meterread.entity;

/**
 * 
 * @ClassName:     nb_meterread.java 
 * @Description:   最新抄表实体类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午8:22:46
 */
public class nb_meterread {
	  private String id           ;
	  private String watermeterid ;
	  private String value        ;
	  private String type         ;
	  private String optionuser   ;
	  private String optiontime   ;
	  private String ctime        ;
	  private String customercode	;
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
