package com.ets.business.loginlog.dao;

import com.ets.business.loginlog.entity.nb_customer_login_log;

import java.util.List;
import java.util.Map;

public interface LoginLogDao {
     //得到登录日志的信息
    public List<nb_customer_login_log> getLogs(Map map);
    //得到数据的总数目
    public long getCount(Map map);
     //增加日志
    public void addLog(nb_customer_login_log log);
    //根据id去查看对应得信息
    public nb_customer_login_log infoLog(String id);
}
