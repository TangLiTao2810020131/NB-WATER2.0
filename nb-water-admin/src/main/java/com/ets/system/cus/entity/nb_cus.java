package com.ets.system.cus.entity;

/**
 * @author 吴浩
 * @create 2019-01-22 20:24
 */
public class nb_cus {

    private String id;
    private String customerName; //客户名称，平台唯一
    private String maxNum;
    private String customerStatus; //客户状态  0关闭  1开通
    private String ctime; //创建时间
    private String remarks; //备注
    private String linkman; // 联系人
    private String tel; //电话
    private String email;
    private String address; //详细地址
    private String secretkey;//秘钥
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    
    public String getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
	public String getCustomerStatus() {
        return customerStatus;
    }
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
    public String getCtime() {
        return ctime;
    }
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getLinkman() {
        return linkman;
    }
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

}
