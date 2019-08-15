package com.ets.business.logopr.service;

import com.ets.business.logopr.dao.LogOprCustomerDao;
import com.ets.business.logopr.entity.tb_log_opr_customer;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.cususer.entity.nb_cus_user;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: 姚轶文
 * @date:2018年9月5日 下午4:27:31
 * @version :
 * 
 */
@Service
@Transactional
public class LogOprCustomerService {

	@Resource
	LogOprCustomerDao logCustomerDao;

	public List<tb_log_opr_customer> getLogs(Map map)
	{
		return logCustomerDao.getLogs(map);
	}

	public long getCount(Map map)
	{
		return logCustomerDao.getCount(map);
	}

	public void addLog(tb_log_opr_customer log)
	{
		//tb_user user = (tb_user)SecurityUtils.getSubject().getSession().getAttribute("userSession");

		Object obj = SecurityUtils.getSubject().getSession().getAttribute("userSession");
		if(obj instanceof nb_cus_user)
		{
			nb_cus_user user = (nb_cus_user)obj;
			log.setUsername(user.getRealname());
		}
		else
		{
			Map<String,String> map = (Map)obj;
			log.setUsername(map.get("REALNAME"));
		}


		log.setId(UUIDUtils.getUUID());
		log.setOprtime(DateTimeUtils.getnowdate());
		logCustomerDao.addLog(log);
	}


	public void inser(tb_log_opr_customer log){
		log.setId(UUIDUtils.getUUID());
		log.setOprtime(DateTimeUtils.getnowdate());
		logCustomerDao.addLog(log);
	}

	public tb_log_opr_customer infoLog(String id)
	{
		return logCustomerDao.infoLog(id);
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
