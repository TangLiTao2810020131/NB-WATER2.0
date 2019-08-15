package com.ets.business.loginlog.service;

import com.ets.business.custormer.service.CustomerUserService;
import com.ets.business.loginlog.dao.LoginLogDao;
import com.ets.business.loginlog.entity.nb_customer_login_log;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LoginLogService {
    @Resource
    LoginLogDao logDao;
    @Resource
    CustomerUserService customerUserService;

    public List<nb_customer_login_log> getLogs(Map map)
    {
        return logDao.getLogs(map);
    }

    public long getCount(Map map)
    {
        return logDao.getCount(map);
    }

    public nb_customer_login_log infoLog(String id)
    {
        return logDao.infoLog(id);
    }
    //增加登录日志
    public void addLog(HttpServletRequest request, nb_customer_login_log log)throws Exception
    {
        String ipaddress = getIp(request);
        log.setIpaddress(ipaddress);
        logDao.addLog(log);
    }

    public  String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }




}
