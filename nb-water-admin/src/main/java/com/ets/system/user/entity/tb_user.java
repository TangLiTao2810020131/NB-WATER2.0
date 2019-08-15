package com.ets.system.user.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 姚轶文
 * @date:2018年8月16日 上午11:46:18
 * @version :
 * 
 */
public class tb_user implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1569081305284258082L;
	
	private String id;
	private String username;
	private String password;
	private String isclose;
	private Timestamp ctime;
	private String email;
	private String tel;
	private String loginnum;


	public tb_user() {
		super();
	}

	public tb_user(String username, String password, String isclose, Timestamp ctime, String email, String tel) {
		super();
		this.username = username;
		this.password = password;
		this.isclose = isclose;
		this.ctime = ctime;
		this.email = email;
		this.tel = tel;
	}

	public tb_user(String id, String username, String password, String isclose, Timestamp ctime, String email,
			String tel) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.isclose = isclose;
		this.ctime = ctime;
		this.email = email;
		this.tel = tel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsclose() {
		return isclose;
	}

	public void setIsclose(String isclose) {
		this.isclose = isclose;
	}

	public Timestamp getCtime() {
		return ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(String loginnum) {
		this.loginnum = loginnum;
	}

}
