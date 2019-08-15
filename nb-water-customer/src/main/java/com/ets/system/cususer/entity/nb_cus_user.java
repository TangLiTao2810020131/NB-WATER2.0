package com.ets.system.cususer.entity;

/**
 * @author 吴浩
 * @create 2019-01-22 20:29
 */
public class nb_cus_user {

    private String id;
    private String cid; //nb_customer主键
    private String realname;
    private String username; //用户名
    private String password;  //密码
    private String isopen; //0关闭 1开通
    private String ctime; //创建时间
    private String tel; //手机号
    private String type; // 1 创建者  2管理员  3职员
    private String loginnum;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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
    public String getIsopen() {
        return isopen;
    }
    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getCtime() {
        return ctime;
    }
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getLoginnum() {
        return loginnum;
    }

    public void setLoginnum(String loginnum) {
        this.loginnum = loginnum;
    }
}
